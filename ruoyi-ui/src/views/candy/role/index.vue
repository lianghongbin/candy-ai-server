<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="角色名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入角色名称" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="角色类型" prop="characterType">
        <el-select v-model="queryParams.characterType" placeholder="请选择角色类型" clearable style="width: 240px">
          <el-option v-for="dict in dict.type.character_type" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-select v-model="queryParams.gender" placeholder="请选择性别" clearable style="width: 240px">
          <el-option v-for="dict in dict.type.character_gender" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="isActive">
        <el-select v-model="queryParams.isActive" placeholder="角色状态" clearable style="width: 240px">
          <el-option v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="dateRange" style="width: 240px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['candy:role:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['candy:role:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['candy:role:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['candy:role:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="roleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="角色编号" align="center" key="id" prop="id" v-if="columns[0].visible" />
      <el-table-column label="角色名称" align="center" key="name" prop="name" v-if="columns[1].visible" :show-overflow-tooltip="true" />
      <el-table-column label="角色类型" align="center" key="characterType" prop="characterType" v-if="columns[2].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.character_type" :value="scope.row.characterType"/>
        </template>
      </el-table-column>
      <el-table-column label="性别" align="center" key="gender" prop="gender" v-if="columns[3].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.character_gender" :value="scope.row.gender"/>
        </template>
      </el-table-column>
      <el-table-column label="年龄" align="center" key="age" prop="age" v-if="columns[4].visible" />
      <el-table-column label="头像" align="center" key="avatarUrl" prop="avatarUrl" v-if="columns[5].visible" width="80">
        <template slot-scope="scope">
          <el-image v-if="scope.row.avatarUrl" :src="scope.row.avatarUrl" style="width: 40px; height: 40px; border-radius: 50%;" />
          <el-avatar v-else icon="el-icon-user" :size="40"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column label="使用用户数" align="center" key="totalUsers" prop="totalUsers" v-if="columns[6].visible" />
      <el-table-column label="总对话数" align="center" key="totalConversations" prop="totalConversations" v-if="columns[7].visible" />
      <el-table-column label="状态" align="center" key="isActive" v-if="columns[8].visible">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.isActive" active-value="1" inactive-value="0" @change="handleStatusChange(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns[9].visible" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['candy:role:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['candy:role:remove']">删除</el-button>
          <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)" v-hasPermi="['candy:role:edit']">
            <el-button size="mini" type="text" icon="el-icon-d-arrow-right">更多</el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="handleView" icon="el-icon-view">查看详情</el-dropdown-item>
              <el-dropdown-item command="handleCopy" icon="el-icon-copy-document">复制角色</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改角色配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="角色名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入角色名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色类型" prop="characterType">
              <el-select v-model="form.characterType" placeholder="请选择角色类型">
                <el-option v-for="dict in dict.type.character_type" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="form.gender" placeholder="请选择性别">
                <el-option v-for="dict in dict.type.character_gender" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年龄" prop="age">
              <el-input-number v-model="form.age" :min="1" :max="120" placeholder="请输入年龄" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="头像URL" prop="avatarUrl">
              <el-input v-model="form.avatarUrl" placeholder="请输入头像URL" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否公开">
              <el-radio-group v-model="form.isPublic">
                <el-radio :label="1">公开</el-radio>
                <el-radio :label="0">私有</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="角色描述" prop="description">
              <el-input v-model="form.description" type="textarea" placeholder="请输入角色描述" :rows="3"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="性格设定" prop="personality">
              <el-input v-model="form.personality" type="textarea" placeholder="请输入性格设定，例如：温柔、体贴、善解人意，喜欢听你分享生活中的点点滴滴" :rows="4"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="角色状态">
              <el-radio-group v-model="form.isActive">
                <el-radio :label="1">激活</el-radio>
                <el-radio :label="0">停用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 角色详情对话框 -->
    <el-dialog title="角色详情" :visible.sync="detailOpen" width="800px" append-to-body>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="角色名称">{{ detailForm.name }}</el-descriptions-item>
        <el-descriptions-item label="角色类型">
          <dict-tag :options="dict.type.character_type" :value="detailForm.characterType"/>
        </el-descriptions-item>
        <el-descriptions-item label="性别">
          <dict-tag :options="dict.type.character_gender" :value="detailForm.gender"/>
        </el-descriptions-item>
        <el-descriptions-item label="年龄">{{ detailForm.age }}</el-descriptions-item>
        <el-descriptions-item label="使用用户数">{{ detailForm.totalUsers }}</el-descriptions-item>
        <el-descriptions-item label="总对话数">{{ detailForm.totalConversations }}</el-descriptions-item>
        <el-descriptions-item label="总消息数">{{ detailForm.totalMessages }}</el-descriptions-item>
        <el-descriptions-item label="是否公开">
          <dict-tag :options="dict.type.sys_yes_no" :value="detailForm.isPublic"/>
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ parseTime(detailForm.createTime) }}</el-descriptions-item>
        <el-descriptions-item label="更新时间">{{ parseTime(detailForm.updateTime) }}</el-descriptions-item>
        <el-descriptions-item label="角色描述" :span="2">{{ detailForm.description }}</el-descriptions-item>
        <el-descriptions-item label="性格设定" :span="2">{{ detailForm.personality }}</el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCandyRole, getCandyRole, delCandyRole, addCandyRole, updateCandyRole, copyCandyRole } from "@/api/candy/role";

export default {
  name: "CandyRole",
  dicts: ['sys_normal_disable', 'character_type', 'character_gender', 'sys_yes_no'],
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
      // 角色表格数据
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
      // 查询参数
      queryParams: {
        name: undefined,
        characterType: undefined,
        gender: undefined,
        isActive: undefined
      },
      // 列信息
      columns: [
        { key: 0, label: `角色编号`, visible: true },
        { key: 1, label: `角色名称`, visible: true },
        { key: 2, label: `角色类型`, visible: true },
        { key: 3, label: `性别`, visible: true },
        { key: 4, label: `年龄`, visible: true },
        { key: 5, label: `头像`, visible: true },
        { key: 6, label: `使用用户数`, visible: true },
        { key: 7, label: `总对话数`, visible: true },
        { key: 8, label: `状态`, visible: true },
        { key: 9, label: `创建时间`, visible: true }
      ],
      // 表单校验
      rules: {
        name: [
          { required: true, message: "角色名称不能为空", trigger: "blur" },
          { min: 2, max: 50, message: '角色名称长度必须介于 2 和 50 之间', trigger: "blur" }
        ],
        characterType: [
          { required: true, message: "角色类型不能为空", trigger: "change" }
        ],
        gender: [
          { required: true, message: "性别不能为空", trigger: "change" }
        ],
        age: [
          { type: "number", min: 1, max: 120, message: "年龄必须在1-120之间", trigger: "blur" }
        ],
        description: [
          { required: true, message: "角色描述不能为空", trigger: "blur" },
          { min: 10, max: 500, message: '角色描述长度必须介于 10 和 500 之间', trigger: "blur" }
        ],
        personality: [
          { required: true, message: "性格设定不能为空", trigger: "blur" },
          { min: 10, max: 1000, message: '性格设定长度必须介于 10 和 1000 之间', trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询角色列表 */
    getList() {
      this.loading = true;
      listCandyRole(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.roleList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 角色状态修改
    handleStatusChange(row) {
      let text = row.isActive === "1" ? "激活" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.name + '"角色吗？').then(function() {
        return updateCandyRole(row);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.isActive = row.isActive === "1" ? "0" : "1";
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        name: undefined,
        description: undefined,
        personality: undefined,
        avatarUrl: undefined,
        characterType: undefined,
        gender: undefined,
        age: undefined,
        isActive: 1,
        isPublic: 0
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
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
      this.title = "添加Candy AI角色";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCandyRole(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改Candy AI角色";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCandyRole(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCandyRole(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除角色编号为"' + ids + '"的数据项？').then(function() {
        return delCandyRole(ids);
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
      getCandyRole(id).then(response => {
        this.detailForm = response.data;
        this.detailOpen = true;
      });
    },
    /** 复制角色操作 */
    handleCopy(row) {
      this.$modal.confirm('确认要复制"' + row.name + '"角色吗？').then(function() {
        return copyCandyRole(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("复制成功");
      }).catch(() => {});
    }
  }
};
</script> 