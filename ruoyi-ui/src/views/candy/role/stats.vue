<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!-- 统计概览卡片 -->
      <el-col :span="6" v-for="(item, index) in statsCards" :key="index">
        <el-card class="stats-card" shadow="hover">
          <div class="stats-content">
            <div class="stats-icon" :style="{ backgroundColor: item.color }">
              <i :class="item.icon"></i>
            </div>
            <div class="stats-info">
              <div class="stats-number">{{ item.value }}</div>
              <div class="stats-label">{{ item.label }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="mt-20">
      <!-- 使用排行 -->
      <el-col :span="12">
        <el-card class="chart-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>角色使用排行</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="refreshRanking">刷新</el-button>
          </div>
          <div class="ranking-list">
            <div
              v-for="(character, index) in rankingList"
              :key="character.id"
              class="ranking-item"
            >
              <div class="ranking-number" :class="getRankingClass(index + 1)">{{ index + 1 }}</div>
              <div class="character-avatar">
                <el-image :src="character.avatarUrl" style="width: 40px; height: 40px; border-radius: 50%;">
                  <div slot="error" class="image-slot">
                    <i class="el-icon-user"></i>
                  </div>
                </el-image>
              </div>
              <div class="character-info">
                <div class="character-name">{{ character.name }}</div>
                <div class="character-stats">
                  <span>用户: {{ character.totalUsers }}</span>
                  <span>对话: {{ character.totalConversations }}</span>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>

      <!-- 风格分布 -->
      <el-col :span="12">
        <el-card class="chart-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>风格分布</span>
          </div>
          <div class="chart-container">
            <div
              v-for="(item, key) in styleDistribution"
              :key="key"
              class="distribution-item"
            >
              <div class="distribution-label">{{ getStyleLabel(key) }}</div>
              <div class="distribution-bar">
                <div class="distribution-progress" :style="{ width: getDistributionPercentage(item, styleTotal) + '%' }"></div>
              </div>
              <div class="distribution-value">{{ item }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="mt-20">
      <!-- 个性分布 -->
      <el-col :span="12">
        <el-card class="chart-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>个性分布</span>
          </div>
          <div class="chart-container">
            <div
              v-for="(item, key) in personalityDistribution"
              :key="key"
              class="distribution-item"
            >
              <div class="distribution-label">{{ getPersonalityLabel(key) }}</div>
              <div class="distribution-bar">
                <div class="distribution-progress" :style="{ width: getDistributionPercentage(item, personalityTotal) + '%' }"></div>
              </div>
              <div class="distribution-value">{{ item }}</div>
            </div>
          </div>
        </el-card>
      </el-col>

      <!-- 创建趋势 -->
      <el-col :span="12">
        <el-card class="chart-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>创建趋势</span>
          </div>
          <div class="trend-info">
            <div class="trend-item">
              <div class="trend-label">总创建数</div>
              <div class="trend-value">{{ creationTrend.totalCreated }}</div>
            </div>
            <div class="trend-item">
              <div class="trend-label">统计天数</div>
              <div class="trend-value">{{ creationTrend.days }}天</div>
            </div>
            <div class="trend-item">
              <div class="trend-label">日均创建</div>
              <div class="trend-value">{{ creationTrend.averagePerDay.toFixed(1) }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getCharacterStats, getCharacterRanking, getStyleDistribution, getPersonalityDistribution } from "@/api/candy/role";

export default {
  name: "CharacterStats",
  data() {
    return {
      // 统计数据
      statsData: {},
      // 使用排行
      rankingList: [],
      // 风格分布
      styleDistribution: {},
      // 个性分布
      personalityDistribution: {},
      // 创建趋势
      creationTrend: {
        totalCreated: 0,
        days: 30,
        averagePerDay: 0
      }
    };
  },
  computed: {
    // 统计卡片数据
    statsCards() {
      return [
        {
          label: '用户角色',
          value: this.statsData.totalUserCharacters || 0,
          icon: 'el-icon-user',
          color: '#409eff'
        },
        {
          label: '系统角色',
          value: this.statsData.totalSystemCharacters || 0,
          icon: 'el-icon-s-custom',
          color: '#67c23a'
        },
        {
          label: '总角色数',
          value: this.statsData.totalCharacters || 0,
          icon: 'el-icon-s-data',
          color: '#e6a23c'
        },
        {
          label: '总使用量',
          value: this.statsData.totalUsers || 0,
          icon: 'el-icon-view',
          color: '#f56c6c'
        }
      ];
    },
    // 风格总数
    styleTotal() {
      return Object.values(this.styleDistribution).reduce((sum, value) => sum + value, 0);
    },
    // 个性总数
    personalityTotal() {
      return Object.values(this.personalityDistribution).reduce((sum, value) => sum + value, 0);
    }
  },
  created() {
    this.loadStats();
  },
  methods: {
    // 加载统计数据
    async loadStats() {
      try {
        // 加载统计概览
        const statsRes = await getCharacterStats();
        this.statsData = statsRes.data;
        
        // 加载使用排行
        await this.loadRanking();
        
        // 加载风格分布
        const styleRes = await getStyleDistribution();
        this.styleDistribution = styleRes.data;
        
        // 加载个性分布
        const personalityRes = await getPersonalityDistribution();
        this.personalityDistribution = personalityRes.data;
        
        // 计算创建趋势
        this.calculateCreationTrend();
      } catch (error) {
        this.$message.error('加载统计数据失败');
        console.error(error);
      }
    },
    
    // 加载使用排行
    async loadRanking() {
      try {
        const res = await getCharacterRanking(10);
        this.rankingList = res.data;
      } catch (error) {
        this.$message.error('加载使用排行失败');
        console.error(error);
      }
    },
    
    // 刷新排行
    refreshRanking() {
      this.loadRanking();
    },
    
    // 计算创建趋势
    calculateCreationTrend() {
      this.creationTrend.totalCreated = this.statsData.totalUserCharacters || 0;
      this.creationTrend.days = 30;
      this.creationTrend.averagePerDay = this.creationTrend.totalCreated / this.creationTrend.days;
    },
    
    // 获取排行样式类
    getRankingClass(rank) {
      if (rank === 1) return 'rank-gold';
      if (rank === 2) return 'rank-silver';
      if (rank === 3) return 'rank-bronze';
      return 'rank-normal';
    },
    
    // 获取分布百分比
    getDistributionPercentage(value, total) {
      if (total === 0) return 0;
      return Math.round((value / total) * 100);
    },
    
    // 获取风格标签
    getStyleLabel(key) {
      const labels = {
        'realistic': '写实风格',
        'anime': '动漫风格',
        'unknown': '未知风格'
      };
      return labels[key] || key;
    },
    
    // 获取个性标签
    getPersonalityLabel(key) {
      const labels = {
        'caregiver': '照顾者',
        'sage': '智者',
        'innocent': '天真',
        'jester': '小丑',
        'temptress': '诱惑者',
        'dominant': '支配者',
        'submissive': '顺从者',
        'lover': '爱人',
        'nympho': '花痴',
        'mean': '刻薄',
        'confidant': '知己',
        'experimenter': '实验者',
        'unknown': '未知个性'
      };
      return labels[key] || key;
    }
  }
};
</script>

<style scoped>
.mt-20 {
  margin-top: 20px;
}

.stats-card {
  margin-bottom: 20px;
}

.stats-content {
  display: flex;
  align-items: center;
}

.stats-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 15px;
}

.stats-icon i {
  font-size: 24px;
  color: white;
}

.stats-info {
  flex: 1;
}

.stats-number {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 5px;
}

.stats-label {
  font-size: 14px;
  color: #909399;
}

.chart-card {
  margin-bottom: 20px;
}

.ranking-list {
  max-height: 400px;
  overflow-y: auto;
}

.ranking-item {
  display: flex;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #f0f0f0;
}

.ranking-item:last-child {
  border-bottom: none;
}

.ranking-number {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  margin-right: 15px;
}

.rank-gold {
  background: linear-gradient(135deg, #ffd700, #ffed4e);
  color: #8b6914;
}

.rank-silver {
  background: linear-gradient(135deg, #c0c0c0, #e8e8e8);
  color: #696969;
}

.rank-bronze {
  background: linear-gradient(135deg, #cd7f32, #daa520);
  color: #8b4513;
}

.rank-normal {
  background: #f5f7fa;
  color: #909399;
}

.character-avatar {
  margin-right: 15px;
}

.character-info {
  flex: 1;
}

.character-name {
  font-weight: bold;
  color: #303133;
  margin-bottom: 5px;
}

.character-stats {
  font-size: 12px;
  color: #909399;
}

.character-stats span {
  margin-right: 15px;
}

.chart-container {
  max-height: 400px;
  overflow-y: auto;
}

.distribution-item {
  display: flex;
  align-items: center;
  padding: 8px 0;
  border-bottom: 1px solid #f0f0f0;
}

.distribution-item:last-child {
  border-bottom: none;
}

.distribution-label {
  width: 80px;
  font-size: 14px;
  color: #303133;
  margin-right: 15px;
}

.distribution-bar {
  flex: 1;
  height: 20px;
  background: #f5f7fa;
  border-radius: 10px;
  overflow: hidden;
  margin-right: 15px;
}

.distribution-progress {
  height: 100%;
  background: linear-gradient(90deg, #409eff, #67c23a);
  border-radius: 10px;
  transition: width 0.3s ease;
}

.distribution-value {
  width: 40px;
  text-align: right;
  font-size: 14px;
  color: #606266;
  font-weight: bold;
}

.trend-info {
  padding: 20px 0;
}

.trend-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 0;
  border-bottom: 1px solid #f0f0f0;
}

.trend-item:last-child {
  border-bottom: none;
}

.trend-label {
  font-size: 14px;
  color: #606266;
}

.trend-value {
  font-size: 18px;
  font-weight: bold;
  color: #409eff;
}

.image-slot {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: #f5f7fa;
  color: #909399;
  font-size: 20px;
}
</style> 