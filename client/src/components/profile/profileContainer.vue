<template>
  <div class="profile-container">
    <el-container>
      <el-header>
        <el-row>
          <el-col :span="1">
            <el-button type="primary" @click="push_back" icon="el-icon-back" circle>
            </el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-container>
          <el-aside width="15vw">
            <div class="up-icon">
              <img src="../../assets/task-icon.jpg" class="up-icon">
            </div>
            <div class="aside-page">
              <el-menu :default-active="page" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose"
                       :collapse="isCollapse" @select="handleSelect">
                  <el-menu-item-group>
                    <el-menu-item index="0">个人信息</el-menu-item>
                    <el-menu-item index="1">数据统计</el-menu-item>
                    <el-menu-item index="2">修改信息</el-menu-item>
                  </el-menu-item-group>
              </el-menu>
            </div>
            <div class="aside-down">
              The nearest Task:
              HomeWork
            </div>
          </el-aside>

        <el-container>
          <el-main>
            <div class="main-page">
              <profile-info v-show="this.page === '0'"></profile-info>
              <data_analysis v-show="this.page === '1'"></data_analysis>
              <changePasswd v-show="this.page === '2'" :username="username"></changePasswd>
            </div>
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import profileInfo from "./profileInfo";
import changePasswd from "./ChangePasswd";
import Data_analysis from "./data-analysis";

export default {
  name: "profileContainer",
  components: {Data_analysis, profileInfo, changePasswd},
  props: {
    username: {
      type: String,
      default: ''
    },
    page_in: {
      type: String,
      default: "0"
    }
  },
  mounted() {
    this.page = this.page_in
  },
  data() {
    return {
      isCollapse: false,
      drawer: false,
      direction: 'ltr',
      size: '15vw',
      fit: 'fill',
      page: "0"
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    handleSelect(key, keyPath){
      if(key === "0" && this.page !== "0"){
        this.page = "0";
      }
      if(key === "1" && this.page !== "1"){
        this.page = "1";
      }
      if(key === "2" && this.page !== "2"){
        this.page = "2";
      }
    },
    push_back(event){
      console.log(this.username)
      this.$router.push({name: 'Main',params:{username:this.username}});
    }
  }
}
</script>

<style scoped>

.main-page {
  min-height: 100vh;
  min-width: 100vh;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 15vw;
  min-height: 400px;
}

.profile-container {
  width: 100%;
  height: 100%;
  background-color: transparent;
  position: absolute;
}

.el-header, .el-footer {
  background-color: #409EFF;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  /*background-color: #D3DCE6;*/
  color: #333;
  text-align: center;
  line-height: 200px;
  height: 100vh;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  line-height: 160px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}

.aside-page {
  height: 70vh;
  width: 15vw;
}

.aside-down {
  height: 15vh;
  width: 15vw;
}

.el-menu-vertical-demo[data-v-514e58e8]:not(.el-menu--collapse) {
  width: 14.9vw;
  height: 70vh;
}

.up-icon{
  height: 10vh;
  width: 15vw;
  object-fit: contain;
}

a {
  text-decoraction: none;
  color: #050505;
}
.router-link-active {
  text-decoration: none;
}

</style>
