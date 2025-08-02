<template>
  <div class="app-container">
    <h1>Candy AI 用户管理测试页面</h1>
    <p>如果您能看到这个页面，说明路由配置正确。</p>
    <el-button @click="testApi">测试API</el-button>
    <div v-if="apiResult">
      <h3>API测试结果：</h3>
      <pre>{{ apiResult }}</pre>
    </div>
  </div>
</template>

<script>
import { listCandyUser } from "@/api/candy/user"

export default {
  name: "CandyUserTest",
  data() {
    return {
      apiResult: null
    }
  },
  methods: {
    testApi() {
      listCandyUser({ pageNum: 1, pageSize: 10 }).then(response => {
        this.apiResult = JSON.stringify(response, null, 2)
      }).catch(error => {
        this.apiResult = JSON.stringify(error, null, 2)
      })
    }
  }
}
</script> 