<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="Character Name" prop="name">
        <el-input v-model="queryParams.name" placeholder="Please enter character name" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="Character Type" prop="characterType">
        <el-select v-model="queryParams.characterType" placeholder="Please select character type" clearable style="width: 240px">
          <el-option v-for="dict in dict.type.character_type" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="Membership Type" prop="membershipType">
        <el-select v-model="queryParams.membershipType" placeholder="Please select membership type" clearable style="width: 240px">
          <el-option v-for="dict in dict.type.membership_type" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="Status" prop="isActive">
        <el-select v-model="queryParams.isActive" placeholder="Character status" clearable style="width: 240px">
          <el-option v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="Create Time">
        <el-date-picker v-model="dateRange" style="width: 240px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="Start Date" end-placeholder="End Date"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">Search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">Reset</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['candy:role:add']">Add</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-magic-stick" size="mini" @click="handleCreate" v-hasPermi="['candy:role:add']">Create Character</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['candy:role:edit']">Edit</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['candy:role:remove']">Delete</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['candy:role:export']">Export</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="roleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="Character ID" align="center" key="id" prop="id" v-if="columns[0].visible" />
      <el-table-column label="Character Name" align="center" key="name" prop="name" v-if="columns[1].visible" :show-overflow-tooltip="true" />
      <el-table-column label="Character Type" align="center" key="characterType" prop="characterType" v-if="columns[2].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.character_type" :value="scope.row.characterType"/>
        </template>
      </el-table-column>
      <el-table-column label="Membership Type" align="center" key="membershipType" prop="membershipType" v-if="columns[3].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.membership_type" :value="scope.row.membershipType"/>
        </template>
      </el-table-column>
      <el-table-column label="Age" align="center" key="age" prop="age" v-if="columns[4].visible" />
      <el-table-column label="Avatar" align="center" key="avatarUrl" prop="avatarUrl" v-if="columns[5].visible" width="80">
        <template slot-scope="scope">
          <el-image v-if="scope.row.avatarUrl" :src="scope.row.avatarUrl" style="width: 40px; height: 40px; border-radius: 50%;" />
          <el-avatar v-else icon="el-icon-user" :size="40"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column label="Total Users" align="center" key="totalUsers" prop="totalUsers" v-if="columns[6].visible" />
      <el-table-column label="Total Conversations" align="center" key="totalConversations" prop="totalConversations" v-if="columns[7].visible" />
      <el-table-column label="Status" align="center" key="isActive" v-if="columns[8].visible">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.isActive" active-value="1" inactive-value="0" @change="handleStatusChange(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="Create Time" align="center" prop="createTime" v-if="columns[9].visible" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Actions" align="center" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['candy:role:edit']">Edit</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['candy:role:remove']">Delete</el-button>
          <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)" v-hasPermi="['candy:role:edit']">
            <el-button size="mini" type="text" icon="el-icon-d-arrow-right">More</el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="handleView" icon="el-icon-view">View Details</el-dropdown-item>
              <el-dropdown-item command="handleCopy" icon="el-icon-copy-document">Copy Character</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- Add or Edit Character Configuration Dialog -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <!-- Basic Information Section -->
        <div class="form-section">
          <h4 class="section-title">Basic Information</h4>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="Character Name" prop="name">
                <el-input v-model="form.name" placeholder="Please enter character name" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="Character Type" prop="characterType">
                <el-select v-model="form.characterType" placeholder="Please select character type" style="width: 100%">
                  <el-option v-for="dict in dict.type.character_type" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="Membership Type" prop="membershipType">
                <el-select v-model="form.membershipType" placeholder="Please select membership type" style="width: 100%">
                  <el-option v-for="dict in dict.type.membership_type" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="Age" prop="age">
                <el-select v-model="form.age" placeholder="Please select age" style="width: 100%">
                  <el-option v-for="dict in dict.type.character_age" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="Style" prop="style">
                <el-select v-model="form.style" placeholder="Please select style" style="width: 100%">
                  <el-option v-for="dict in dict.type.character_style" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="Avatar URL" prop="avatarUrl">
                <el-input v-model="form.avatarUrl" placeholder="Please enter avatar URL" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form-item label="Character Description" prop="description">
                <el-input v-model="form.description" type="textarea" placeholder="Please enter character description" :rows="3"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <!-- Hidden System Fields -->
        <el-row style="display: none;">
          <el-col :span="12">
            <el-form-item label="System Character">
              <el-radio-group v-model="form.isSystem">
                <el-radio :label="'0'">User Created</el-radio>
                <el-radio :label="'1'">System Created</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Public">
              <el-radio-group v-model="form.isPublic">
                <el-radio :label="1">Public</el-radio>
                <el-radio :label="0">Private</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- Physical Appearance Section -->
        <div class="form-section">
          <h4 class="section-title">Physical Appearance</h4>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="Ethnicity" prop="ethnicity">
                <el-select v-model="form.ethnicity" placeholder="Please select ethnicity" style="width: 100%">
                  <el-option v-for="dict in dict.type.character_ethnicity" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="Eye Color" prop="eyeColor">
                <el-select v-model="form.eyeColor" placeholder="Please select eye color" style="width: 100%">
                  <el-option v-for="dict in dict.type.character_eye_color" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="Hair Style" prop="hairStyle">
                <el-select v-model="form.hairStyle" placeholder="Please select hair style" style="width: 100%">
                  <el-option v-for="dict in dict.type.character_hair_style" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="Hair Color" prop="hairColor">
                <el-select v-model="form.hairColor" placeholder="Please select hair color" style="width: 100%">
                  <el-option v-for="dict in dict.type.character_hair_color" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="Body Type" prop="bodyType">
                <el-select v-model="form.bodyType" placeholder="Please select body type" style="width: 100%">
                  <el-option v-for="dict in dict.type.character_body_type" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="Breast Size" prop="breastSize">
                <el-select v-model="form.breastSize" placeholder="Please select breast size" style="width: 100%">
                  <el-option v-for="dict in dict.type.character_breast_size" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="Butt Size" prop="buttSize">
                <el-select v-model="form.buttSize" placeholder="Please select butt size" style="width: 100%">
                  <el-option v-for="dict in dict.type.character_butt_size" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <!-- Personality & Background Section -->
        <div class="form-section">
          <h4 class="section-title">Personality & Background</h4>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="Occupation" prop="occupation">
                <el-select v-model="form.occupation" placeholder="Please select occupation" style="width: 100%">
                  <el-option v-for="dict in dict.type.character_occupation" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="Relationship" prop="relationship">
                <el-select v-model="form.relationship" placeholder="Please select relationship" style="width: 100%">
                  <el-option v-for="dict in dict.type.character_relationship" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form-item label="Hobbies" prop="hobbies">
                <el-input v-model="form.hobbies" placeholder="Please enter hobbies, separate multiple hobbies with commas" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form-item label="Personality Description" prop="personalityDescription">
                <el-input v-model="form.personalityDescription" type="textarea" placeholder="Please enter personality description, e.g.: gentle, caring, understanding, likes to listen to you share bits and pieces of life" :rows="4"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <!-- Status Section -->
        <div class="form-section">
          <h4 class="section-title">Status</h4>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="Character Status">
                <el-radio-group v-model="form.isActive">
                  <el-radio :label="1">Active</el-radio>
                  <el-radio :label="0">Inactive</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">Confirm</el-button>
        <el-button @click="cancel">Cancel</el-button>
      </div>
    </el-dialog>

    <!-- Character Details Dialog -->
    <el-dialog title="Character Details" :visible.sync="detailOpen" width="800px" append-to-body>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="Character Name">{{ detailForm.name }}</el-descriptions-item>
        <el-descriptions-item label="Character Type">
          <dict-tag :options="dict.type.character_type" :value="detailForm.characterType"/>
        </el-descriptions-item>
        <el-descriptions-item label="Membership Type">
          <dict-tag :options="dict.type.membership_type" :value="detailForm.membershipType"/>
        </el-descriptions-item>
        <el-descriptions-item label="System Character">
          <dict-tag :options="dict.type.sys_yes_no" :value="detailForm.isSystem"/>
        </el-descriptions-item>
        <el-descriptions-item label="Age">
          <dict-tag :options="dict.type.character_age" :value="detailForm.age"/>
        </el-descriptions-item>
        <el-descriptions-item label="Style">
          <dict-tag :options="dict.type.character_style" :value="detailForm.style"/>
        </el-descriptions-item>
        <el-descriptions-item label="Ethnicity">
          <dict-tag :options="dict.type.character_ethnicity" :value="detailForm.ethnicity"/>
        </el-descriptions-item>
        <el-descriptions-item label="Eye Color">
          <dict-tag :options="dict.type.character_eye_color" :value="detailForm.eyeColor"/>
        </el-descriptions-item>
        <el-descriptions-item label="Hair Style">
          <dict-tag :options="dict.type.character_hair_style" :value="detailForm.hairStyle"/>
        </el-descriptions-item>
        <el-descriptions-item label="Hair Color">
          <dict-tag :options="dict.type.character_hair_color" :value="detailForm.hairColor"/>
        </el-descriptions-item>
        <el-descriptions-item label="Body Type">
          <dict-tag :options="dict.type.character_body_type" :value="detailForm.bodyType"/>
        </el-descriptions-item>
        <el-descriptions-item label="Breast Size">
          <dict-tag :options="dict.type.character_breast_size" :value="detailForm.breastSize"/>
        </el-descriptions-item>
        <el-descriptions-item label="Butt Size">
          <dict-tag :options="dict.type.character_butt_size" :value="detailForm.buttSize"/>
        </el-descriptions-item>
        <el-descriptions-item label="Personality">
          <dict-tag :options="dict.type.character_personality" :value="detailForm.personality"/>
        </el-descriptions-item>
        <el-descriptions-item label="Occupation">
          <dict-tag :options="dict.type.character_occupation" :value="detailForm.occupation"/>
        </el-descriptions-item>
        <el-descriptions-item label="Relationship">
          <dict-tag :options="dict.type.character_relationship" :value="detailForm.relationship"/>
        </el-descriptions-item>
        <el-descriptions-item label="Total Users">{{ detailForm.totalUsers }}</el-descriptions-item>
        <el-descriptions-item label="Total Conversations">{{ detailForm.totalConversations }}</el-descriptions-item>
        <el-descriptions-item label="Total Messages">{{ detailForm.totalMessages }}</el-descriptions-item>
        <el-descriptions-item label="Public">
          <dict-tag :options="dict.type.sys_yes_no" :value="detailForm.isPublic"/>
        </el-descriptions-item>
        <el-descriptions-item label="Create Time">{{ parseTime(detailForm.createTime) }}</el-descriptions-item>
        <el-descriptions-item label="Update Time">{{ parseTime(detailForm.updateTime) }}</el-descriptions-item>
        <el-descriptions-item label="Character Description" :span="2">{{ detailForm.description }}</el-descriptions-item>
        <el-descriptions-item label="Hobbies" :span="2">{{ detailForm.hobbies }}</el-descriptions-item>
        <el-descriptions-item label="Personality Description" :span="2">{{ detailForm.personalityDescription }}</el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">Close</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getCharacterList, getCharacterById, deleteCharacter, createCharacter, updateCharacter, copyCharacter } from "@/api/candy/role";

export default {
  name: "CandyRole",
  dicts: ['sys_normal_disable', 'character_type', 'membership_type', 'character_style', 'character_ethnicity', 'character_age', 'character_eye_color', 'character_hair_style', 'character_hair_color', 'character_body_type', 'character_breast_size', 'character_butt_size', 'character_personality', 'character_occupation', 'character_relationship', 'sys_yes_no'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // Character table data
      roleList: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示详情弹出层
      detailOpen: false,
      // 日期范围
      dateRange: [],
      // 详情表单
      detailForm: {},
      // 表单参数
      form: {},
      // Query parameters
      queryParams: {
        name: undefined,
        characterType: undefined,
        membershipType: undefined,
        isActive: undefined
      },
      // Column information
      columns: [
        { key: 0, label: `Character ID`, visible: true },
        { key: 1, label: `Character Name`, visible: true },
        { key: 2, label: `Character Type`, visible: true },
        { key: 3, label: `Membership Type`, visible: true },
        { key: 4, label: `Age`, visible: true },
        { key: 5, label: `Avatar`, visible: true },
        { key: 6, label: `Total Users`, visible: true },
        { key: 7, label: `Total Conversations`, visible: true },
        { key: 8, label: `Status`, visible: true },
        { key: 9, label: `Create Time`, visible: true }
      ],
      // Form validation rules
      rules: {
        name: [
          { required: true, message: "Character name cannot be empty", trigger: "blur" },
          { min: 2, max: 50, message: 'Character name length must be between 2 and 50 characters', trigger: "blur" }
        ],
        characterType: [
          { required: true, message: "Character type cannot be empty", trigger: "change" }
        ],
        membershipType: [
          { required: true, message: "Membership type cannot be empty", trigger: "change" }
        ],
        age: [
          { required: true, message: "Age cannot be empty", trigger: "change" }
        ],
        description: [
          { required: true, message: "Character description cannot be empty", trigger: "blur" },
          { min: 10, max: 500, message: 'Character description length must be between 10 and 500 characters', trigger: "blur" }
        ],
        personalityDescription: [
          { required: true, message: "Personality description cannot be empty", trigger: "blur" },
          { min: 10, max: 1000, message: 'Personality description length must be between 10 and 1000 characters', trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** Query character list */
    getList() {
      this.loading = true;
      getCharacterList(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.roleList = response.data;
          this.total = response.data ? response.data.length : 0;
          this.loading = false;
        }
      );
    },
    // Character status change
    handleStatusChange(row) {
      let text = row.isActive === "1" ? "activate" : "deactivate";
      this.$modal.confirm('Are you sure you want to "' + text + '" the character "' + row.name + '" ?').then(function() {
        return updateCharacter(row);
      }).then(() => {
        this.$modal.msgSuccess(text + " successful");
      }).catch(function() {
        row.isActive = row.isActive === "1" ? "0" : "1";
      });
    },
    // Cancel button
    cancel() {
      this.open = false;
      this.reset();
    },
    // Form reset
    reset() {
      this.form = {
        id: undefined,
        name: undefined,
        description: undefined,
        personalityDescription: undefined,
        avatarUrl: undefined,
        characterType: 'girls',
        membershipType: 'normal',
        isSystem: '0',
        age: undefined,
        style: undefined,
        ethnicity: undefined,
        eyeColor: undefined,
        hairStyle: undefined,
        hairColor: undefined,
        bodyType: undefined,
        breastSize: undefined,
        buttSize: undefined,
        personality: undefined,
        occupation: undefined,
        hobbies: undefined,
        relationship: undefined,
        isActive: 1,
        isPublic: 1,
        isSystem: '1'
      };
      this.resetForm("form");
    },
    /** Search button operation */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** Reset button operation */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
              this.title = "Add Candy AI Character";
    },
    /** Create character button operation */
    handleCreate() {
      this.$router.push('/candy/role/create');
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCharacterById(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "Edit Candy AI Character";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCharacter(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            createCharacter(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('Are you sure you want to delete the character with ID "' + ids + '" ?').then(function() {
        return deleteCharacter(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('candy/role/export', {
        ...this.queryParams
      }, `candy_role_${new Date().getTime()}.xlsx`)
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "handleView":
          this.handleView(row);
          break;
        case "handleCopy":
          this.handleCopy(row);
          break;
        default:
          break;
      }
    },
    /** 查看详情操作 */
    handleView(row) {
      const id = row.id;
      getCharacterById(id).then(response => {
        this.detailForm = response.data;
        this.detailOpen = true;
      });
    },
    /** Copy character operation */
    handleCopy(row) {
              this.$modal.confirm('Are you sure you want to copy the character "' + row.name + '" ?').then(function() {
        return copyCharacter(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("复制成功");
      }).catch(() => {});
    }
  }
};
</script>

<style scoped>
.form-section {
  margin-bottom: 30px;
  padding: 20px;
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  background-color: #fafafa;
}

.form-section:last-child {
  margin-bottom: 20px;
}

.section-title {
  margin: 0 0 20px 0;
  padding-bottom: 10px;
  border-bottom: 2px solid #409eff;
  color: #303133;
  font-size: 16px;
  font-weight: 600;
}

.el-form-item {
  margin-bottom: 18px;
}

.el-row {
  margin-bottom: 10px;
}

.el-row:last-child {
  margin-bottom: 0;
}

/* 确保下拉框宽度一致 */
.el-select {
  width: 100%;
}

/* 优化间距 */
.el-form-item__label {
  font-weight: 500;
  color: #606266;
}

/* 美化输入框 */
.el-input__inner {
  border-radius: 4px;
}

/* 美化文本域 */
.el-textarea__inner {
  border-radius: 4px;
}

/* 美化单选按钮组 */
.el-radio-group {
  display: flex;
  gap: 20px;
}

.el-radio {
  margin-right: 0;
}

/* 响应式优化 */
@media (max-width: 768px) {
  .form-section {
    padding: 15px;
  }
  
  .section-title {
    font-size: 14px;
  }
  
  .el-radio-group {
    flex-direction: column;
    gap: 10px;
  }
}
</style> 