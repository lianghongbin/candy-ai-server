<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>创建新角色</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="goBack">返回列表</el-button>
      </div>

      <!-- 进度条 -->
      <el-steps :active="currentStepIndex" finish-status="success" align-center class="mb-20">
        <el-step v-for="(step, index) in steps" :key="index" :title="step.title" :description="step.description"></el-step>
      </el-steps>

      <!-- 步骤内容 -->
      <div class="step-content">
        <!-- 步骤1: 选择风格 -->
        <div v-if="currentStep === 'style'" class="step-panel">
          <h3>选择风格</h3>
          <p class="step-description">选择你喜欢的角色风格</p>
          <div class="options-grid">
            <div
              v-for="option in currentStepOptions"
              :key="option.value"
              class="option-card"
              :class="{ 'selected': selections.style === option.value }"
              @click="selectOption('style', option.value)"
            >
              <div class="option-image">
                <el-image :src="option.imageUrl" fit="cover">
                  <div slot="error" class="image-slot">
                    <i class="el-icon-picture-outline"></i>
                  </div>
                </el-image>
              </div>
              <div class="option-info">
                <h4>{{ option.label }}</h4>
                <p v-if="option.description">{{ option.description }}</p>
              </div>
            </div>
          </div>
        </div>

        <!-- 步骤2: 基础属性 -->
        <div v-if="currentStep === 'basic_attributes'" class="step-panel">
          <h3>基础属性</h3>
          <p class="step-description">设置角色的基础属性</p>
          <el-form :model="selections" label-width="120px" class="step-form">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="种族">
                  <el-select v-model="selections.ethnicity" placeholder="请选择种族" style="width: 100%">
                    <el-option
                      v-for="option in ethnicityOptions"
                      :key="option.value"
                      :label="option.label"
                      :value="option.value"
                    >
                      <div class="option-with-image">
                        <el-image :src="option.imageUrl" style="width: 30px; height: 30px; margin-right: 10px;"></el-image>
                        <span>{{ option.label }}</span>
                      </div>
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="年龄">
                  <el-select v-model="selections.age" placeholder="请选择年龄" style="width: 100%">
                    <el-option
                      v-for="option in ageOptions"
                      :key="option.value"
                      :label="option.label"
                      :value="option.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="眼睛颜色">
                  <el-select v-model="selections.eyeColor" placeholder="请选择眼睛颜色" style="width: 100%">
                    <el-option
                      v-for="option in eyeColorOptions"
                      :key="option.value"
                      :label="option.label"
                      :value="option.value"
                    >
                      <div class="option-with-image">
                        <el-image :src="option.imageUrl" style="width: 30px; height: 30px; margin-right: 10px;"></el-image>
                        <span>{{ option.label }}</span>
                      </div>
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>

        <!-- 步骤3: 发型发色 -->
        <div v-if="currentStep === 'hair'" class="step-panel">
          <h3>发型发色</h3>
          <p class="step-description">选择发型和发色</p>
          <el-form :model="selections" label-width="120px" class="step-form">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="发型">
                  <el-select v-model="selections.hairStyle" placeholder="请选择发型" style="width: 100%">
                    <el-option
                      v-for="option in hairStyleOptions"
                      :key="option.value"
                      :label="option.label"
                      :value="option.value"
                    >
                      <div class="option-with-image">
                        <el-image :src="option.imageUrl" style="width: 30px; height: 30px; margin-right: 10px;"></el-image>
                        <span>{{ option.label }}</span>
                      </div>
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="发色">
                  <el-select v-model="selections.hairColor" placeholder="请选择发色" style="width: 100%">
                    <el-option
                      v-for="option in hairColorOptions"
                      :key="option.value"
                      :label="option.label"
                      :value="option.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>

        <!-- 步骤4: 体型特征 -->
        <div v-if="currentStep === 'body'" class="step-panel">
          <h3>体型特征</h3>
          <p class="step-description">设置体型特征</p>
          <el-form :model="selections" label-width="120px" class="step-form">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="体型">
                  <el-select v-model="selections.bodyType" placeholder="请选择体型" style="width: 100%">
                    <el-option
                      v-for="option in bodyTypeOptions"
                      :key="option.value"
                      :label="option.label"
                      :value="option.value"
                    >
                      <div class="option-with-image">
                        <el-image :src="option.imageUrl" style="width: 30px; height: 30px; margin-right: 10px;"></el-image>
                        <span>{{ option.label }}</span>
                      </div>
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="胸围">
                  <el-select v-model="selections.breastSize" placeholder="请选择胸围" style="width: 100%">
                    <el-option
                      v-for="option in breastSizeOptions"
                      :key="option.value"
                      :label="option.label"
                      :value="option.value"
                    >
                      <div class="option-with-image">
                        <el-image :src="option.imageUrl" style="width: 30px; height: 30px; margin-right: 10px;"></el-image>
                        <span>{{ option.label }}</span>
                      </div>
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="臀围">
                  <el-select v-model="selections.buttSize" placeholder="请选择臀围" style="width: 100%">
                    <el-option
                      v-for="option in buttSizeOptions"
                      :key="option.value"
                      :label="option.label"
                      :value="option.value"
                    >
                      <div class="option-with-image">
                        <el-image :src="option.imageUrl" style="width: 30px; height: 30px; margin-right: 10px;"></el-image>
                        <span>{{ option.label }}</span>
                      </div>
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>

        <!-- 步骤5: 个性选择 -->
        <div v-if="currentStep === 'personality'" class="step-panel">
          <h3>个性选择</h3>
          <p class="step-description">选择角色的个性特征</p>
          <div class="options-grid personality-grid">
            <div
              v-for="option in personalityOptions"
              :key="option.value"
              class="option-card personality-card"
              :class="{ 'selected': selections.personality === option.value }"
              @click="selectOption('personality', option.value)"
            >
              <div class="personality-icon">
                <i :class="'el-icon-' + option.icon"></i>
              </div>
              <div class="option-info">
                <h4>{{ option.label }}</h4>
                <p>{{ option.description }}</p>
              </div>
            </div>
          </div>
        </div>

        <!-- 步骤6: 职业爱好 (仅Anime风格) -->
        <div v-if="currentStep === 'occupation_hobbies'" class="step-panel">
          <h3>职业爱好</h3>
          <p class="step-description">选择角色的职业和爱好</p>
          <el-form :model="selections" label-width="120px" class="step-form">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="职业">
                  <el-select v-model="selections.occupation" placeholder="请选择职业" style="width: 100%">
                    <el-option
                      v-for="option in occupationOptions"
                      :key="option.value"
                      :label="option.label"
                      :value="option.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="爱好">
                  <el-select v-model="selections.hobbies" multiple placeholder="请选择爱好(最多3个)" style="width: 100%">
                    <el-option
                      v-for="option in hobbyOptions"
                      :key="option.value"
                      :label="option.label"
                      :value="option.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>

        <!-- 步骤7: 关系选择 -->
        <div v-if="currentStep === 'relationship'" class="step-panel">
          <h3>关系选择</h3>
          <p class="step-description">选择与角色的关系</p>
          <div class="options-grid relationship-grid">
            <div
              v-for="option in relationshipOptions"
              :key="option.value"
              class="option-card relationship-card"
              :class="{ 'selected': selections.relationship === option.value }"
              @click="selectOption('relationship', option.value)"
            >
              <div class="relationship-icon">
                <i :class="'el-icon-' + option.icon"></i>
              </div>
              <div class="option-info">
                <h4>{{ option.label }}</h4>
              </div>
            </div>
          </div>
        </div>

        <!-- 步骤8: 确认信息 -->
        <div v-if="currentStep === 'summary'" class="step-panel">
          <h3>确认信息</h3>
          <p class="step-description">确认角色信息并完成创建</p>
          <el-descriptions :column="2" border>
            <el-descriptions-item label="风格">{{ getOptionLabel('style', selections.style) }}</el-descriptions-item>
            <el-descriptions-item label="种族">{{ getOptionLabel('ethnicity', selections.ethnicity) }}</el-descriptions-item>
            <el-descriptions-item label="年龄">{{ getOptionLabel('age', selections.age) }}</el-descriptions-item>
            <el-descriptions-item label="眼睛颜色">{{ getOptionLabel('eyeColor', selections.eyeColor) }}</el-descriptions-item>
            <el-descriptions-item label="发型">{{ getOptionLabel('hairStyle', selections.hairStyle) }}</el-descriptions-item>
            <el-descriptions-item label="发色">{{ getOptionLabel('hairColor', selections.hairColor) }}</el-descriptions-item>
            <el-descriptions-item label="体型">{{ getOptionLabel('bodyType', selections.bodyType) }}</el-descriptions-item>
            <el-descriptions-item label="胸围">{{ getOptionLabel('breastSize', selections.breastSize) }}</el-descriptions-item>
            <el-descriptions-item label="臀围">{{ getOptionLabel('buttSize', selections.buttSize) }}</el-descriptions-item>
            <el-descriptions-item label="个性">{{ getOptionLabel('personality', selections.personality) }}</el-descriptions-item>
            <el-descriptions-item v-if="selections.style === 'anime'" label="职业">{{ getOptionLabel('occupation', selections.occupation) }}</el-descriptions-item>
            <el-descriptions-item v-if="selections.style === 'anime'" label="爱好">{{ getHobbiesLabel() }}</el-descriptions-item>
            <el-descriptions-item label="关系">{{ getOptionLabel('relationship', selections.relationship) }}</el-descriptions-item>
          </el-descriptions>
        </div>
      </div>

      <!-- 操作按钮 -->
      <div class="step-actions">
        <el-button v-if="currentStepIndex > 0" @click="previousStep">上一步</el-button>
        <el-button v-if="currentStepIndex < steps.length - 1" type="primary" @click="nextStep" :disabled="!canProceed">下一步</el-button>
        <el-button v-if="currentStepIndex === steps.length - 1" type="success" @click="completeCreation" :loading="creating">完成创建</el-button>
        <el-button @click="cancelCreation">取消</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import { getCreationConfig, startCreation, getStepOptions, saveStepSelection, completeCreation } from "@/api/candy/role";

export default {
  name: "CharacterCreate",
  data() {
    return {
      // 当前步骤
      currentStep: 'style',
      currentStepIndex: 0,
      // 步骤配置
      steps: [
        { code: 'style', title: '选择风格', description: '选择角色风格' },
        { code: 'basic_attributes', title: '基础属性', description: '设置基础属性' },
        { code: 'hair', title: '发型发色', description: '选择发型发色' },
        { code: 'body', title: '体型特征', description: '设置体型特征' },
        { code: 'personality', title: '个性选择', description: '选择个性特征' },
        { code: 'occupation_hobbies', title: '职业爱好', description: '设置职业爱好' },
        { code: 'relationship', title: '关系选择', description: '选择关系类型' },
        { code: 'summary', title: '确认信息', description: '确认并创建' }
      ],
      // 当前选择
      selections: {
        style: '',
        ethnicity: '',
        age: '',
        eyeColor: '',
        hairStyle: '',
        hairColor: '',
        bodyType: '',
        breastSize: '',
        buttSize: '',
        personality: '',
        occupation: '',
        hobbies: [],
        relationship: ''
      },
      // 选项配置
      options: {},
      // 会话ID
      sessionId: null,
      // 创建中状态
      creating: false
    };
  },
  computed: {
    // 当前步骤选项
    currentStepOptions() {
      return this.options[this.currentStep] || [];
    },
    // 是否可以继续
    canProceed() {
      const required = this.getRequiredFields();
      return required.every(field => this.selections[field]);
    },
    // 各选项数据
    styleOptions() { return this.options.styles || []; },
    ethnicityOptions() { return this.options.ethnicities || []; },
    ageOptions() { return this.options.ages || []; },
    eyeColorOptions() { return this.options.eyeColors || []; },
    hairStyleOptions() { return this.options.hairStyles || []; },
    hairColorOptions() { return this.options.hairColors || []; },
    bodyTypeOptions() { return this.options.bodyTypes || []; },
    breastSizeOptions() { return this.options.breastSizes || []; },
    buttSizeOptions() { return this.options.buttSizes || []; },
    personalityOptions() { return this.options.personalities || []; },
    occupationOptions() { return this.options.occupations || []; },
    hobbyOptions() { return this.options.hobbies || []; },
    relationshipOptions() { return this.options.relationships || []; }
  },
  created() {
    this.initCreation();
  },
  methods: {
    // 初始化创建流程
    async initCreation() {
      try {
        // 获取创建配置
        const configRes = await getCreationConfig();
        this.options = configRes.data;
        
        // 开始创建会话
        const sessionRes = await startCreation();
        this.sessionId = sessionRes.data.sessionId;
        
        // 加载第一步选项
        await this.loadStepOptions('style');
      } catch (error) {
        this.$message.error('初始化创建流程失败');
        console.error(error);
      }
    },
    
    // 加载步骤选项
    async loadStepOptions(stepCode) {
      try {
        const res = await getStepOptions(stepCode);
        this.options[stepCode] = res.data.options || [];
      } catch (error) {
        this.$message.error('加载步骤选项失败');
        console.error(error);
      }
    },
    
    // 选择选项
    selectOption(field, value) {
      this.selections[field] = value;
      
      // 特殊处理：选择Realistic风格时跳过职业爱好步骤
      if (field === 'style' && value === 'realistic') {
        this.selections.occupation = '';
        this.selections.hobbies = [];
      }
    },
    
    // 下一步
    async nextStep() {
      if (!this.canProceed) {
        this.$message.warning('请完成当前步骤的所有必填项');
        return;
      }
      
      try {
        // 保存当前步骤选择
        await saveStepSelection(this.currentStep, this.selections);
        
        // 移动到下一步
        this.currentStepIndex++;
        this.currentStep = this.steps[this.currentStepIndex].code;
        
        // 加载下一步选项
        if (this.currentStep !== 'summary') {
          await this.loadStepOptions(this.currentStep);
        }
      } catch (error) {
        this.$message.error('保存步骤选择失败');
        console.error(error);
      }
    },
    
    // 上一步
    async previousStep() {
      if (this.currentStepIndex > 0) {
        this.currentStepIndex--;
        this.currentStep = this.steps[this.currentStepIndex].code;
        
        // 加载当前步骤选项
        if (this.currentStep !== 'summary') {
          await this.loadStepOptions(this.currentStep);
        }
      }
    },
    
    // 完成创建
    async completeCreation() {
      if (!this.canProceed) {
        this.$message.warning('请完成所有必填项');
        return;
      }
      
      this.creating = true;
      try {
        // 保存最后一步选择
        await saveStepSelection(this.currentStep, this.selections);
        
        // 完成创建
        const res = await completeCreation();
        
        this.$message.success('角色创建成功！');
        this.$router.push('/candy/role');
      } catch (error) {
        this.$message.error('角色创建失败');
        console.error(error);
      } finally {
        this.creating = false;
      }
    },
    
    // 取消创建
    async cancelCreation() {
      try {
        await this.$confirm('确定要取消创建吗？已保存的信息将丢失。', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        });
        
        this.$router.push('/candy/role');
      } catch (error) {
        // 用户取消
      }
    },
    
    // 获取必填字段
    getRequiredFields() {
      const requiredMap = {
        style: ['style'],
        basic_attributes: ['ethnicity', 'age', 'eyeColor'],
        hair: ['hairStyle', 'hairColor'],
        body: ['bodyType', 'breastSize', 'buttSize'],
        personality: ['personality'],
        occupation_hobbies: this.selections.style === 'anime' ? ['occupation', 'hobbies'] : [],
        relationship: ['relationship'],
        summary: []
      };
      return requiredMap[this.currentStep] || [];
    },
    
    // 获取选项标签
    getOptionLabel(type, value) {
      const options = this.options[type] || [];
      const option = options.find(opt => opt.value === value);
      return option ? option.label : value;
    },
    
    // 获取爱好标签
    getHobbiesLabel() {
      if (!this.selections.hobbies || this.selections.hobbies.length === 0) {
        return '无';
      }
      return this.selections.hobbies.map(hobby => this.getOptionLabel('hobbies', hobby)).join(', ');
    },
    
    // 返回列表
    goBack() {
      this.$router.push('/candy/role');
    }
  }
};
</script>

<style scoped>
.mb-20 {
  margin-bottom: 20px;
}

.step-content {
  min-height: 400px;
  padding: 20px 0;
}

.step-panel {
  max-width: 800px;
  margin: 0 auto;
}

.step-panel h3 {
  margin-bottom: 10px;
  color: #303133;
}

.step-description {
  color: #909399;
  margin-bottom: 30px;
}

.options-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-top: 20px;
}

.option-card {
  border: 2px solid #e4e7ed;
  border-radius: 8px;
  padding: 15px;
  cursor: pointer;
  transition: all 0.3s;
  background: #fff;
}

.option-card:hover {
  border-color: #409eff;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.option-card.selected {
  border-color: #409eff;
  background: #f0f9ff;
}

.option-image {
  width: 100%;
  height: 120px;
  margin-bottom: 10px;
  border-radius: 4px;
  overflow: hidden;
}

.option-image .el-image {
  width: 100%;
  height: 100%;
}

.image-slot {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: #f5f7fa;
  color: #909399;
  font-size: 30px;
}

.option-info h4 {
  margin: 0 0 5px 0;
  color: #303133;
}

.option-info p {
  margin: 0;
  color: #606266;
  font-size: 12px;
}

.personality-grid {
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
}

.personality-card {
  display: flex;
  align-items: center;
  padding: 20px;
}

.personality-icon {
  font-size: 24px;
  color: #409eff;
  margin-right: 15px;
  width: 40px;
  text-align: center;
}

.relationship-grid {
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
}

.relationship-card {
  display: flex;
  align-items: center;
  padding: 15px;
}

.relationship-icon {
  font-size: 20px;
  color: #409eff;
  margin-right: 10px;
  width: 30px;
  text-align: center;
}

.step-form {
  max-width: 600px;
  margin: 0 auto;
}

.option-with-image {
  display: flex;
  align-items: center;
}

.step-actions {
  text-align: center;
  margin-top: 30px;
  padding-top: 20px;
  border-top: 1px solid #e4e7ed;
}

.step-actions .el-button {
  margin: 0 10px;
}
</style> 