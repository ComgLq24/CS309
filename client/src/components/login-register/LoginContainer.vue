<template>
  <div class="login-container">
    <el-container class="login-form">
      <el-main>
        <el-form :model="loginForm" :rules="loginRules" status-icon ref="loginForm" label-width="100px"
                 class="demo-ruleForm login-page">
          <!-- 系统登录标题 -->
          <el-row row type='flex' justify="center">
            <h1 class="title">系统登录</h1>
          </el-row>
          <!-- 用户名输入框 -->
          <el-form-item prop="username" label='用户名'>
            <el-input type="text"
                      v-model="loginForm.username"
                      auto-complete="off"
                      placeholder="请输入用户名"
                      class="input-text"
            ></el-input>
          </el-form-item>
          <!-- 密码输入框 -->
          <el-form-item prop="password" label='密码'>
            <el-input type="password"
                      v-model="loginForm.password"
                      auto-complete="off"
                      placeholder="请输入密码"
                      @keyup.enter.native="handleSubmit"
                      show-password
                      class="input-text"></el-input>
          </el-form-item>
          <!-- 登录注册按钮 -->
          <el-form-item>
            <el-col :span="18" >
              <el-button type="primary" @click="handleSubmit" icon="el-icon-user">登录</el-button>
              <el-button @click="handleRegister" icon="el-icon-s-promotion">注册</el-button>
            </el-col>
          </el-form-item>
          <el-form-item>
            <el-col :span="18">
              <el-button @click="handleGithub" icon="iconfont el-icon-githubgithub">Github</el-button>
<!--              <el-button @click="handleOutlook">Outlook</el-button>-->
            </el-col>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import axios from 'axios'
import process from "_shelljs@0.7.8@shelljs";
axios.defaults.baseURL = process.env.API_ROOT
export default {
  name: 'LoginContainer',
  data() {
    var checkUsername = (rule, value, callback) => {
      if (value === '') {
        return callback(new Error("用户名不能为空"))
      }
      callback()
    }
    var checkPassword = (rule, value, callback) => {
      if (value === '') {
        return callback(new Error("密码不能为空"))
      }
      let pwdRegExp = new RegExp('^[0-9a-zA-Z]+$')
      if (!pwdRegExp.test(value)) {
        return callback(new Error('密码只能为数字或者小写大写字母'))
      }
      callback()
    }
    return {
      logining: false,
      checked: false,
      loginForm: {
        // 登录表单数据的绑定对象
        username: '',
        password: ''
      },
      loginRules: {
        // 表单验证规则
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在3到10个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入登录密码', trigger: 'blur'},
          {min: 6, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    handleSubmit(event) {
      const that = this
      this.logining = true
      this.$refs.loginForm.validate(async (valid) => {
        if (valid) {
          this.loginForm.password = this.$md5(this.loginForm.password)
          axios.post(
              '/user/login',
              {
                username: that.loginForm.username,
                password: that.loginForm.password
              },
          ).then(
              function (response) {
                that.$message({
                  message: '登录成功',
                  type: 'success'
                })
                let token = response.data.data
                window.sessionStorage.setItem('token', token)
                let tmpusername = that.loginForm.username
                that.loginForm.username = ''
                that.loginForm.password = ''
                that.logining = false
                that.$router.push({name: 'Main', params: {username: tmpusername}})
              },
              function (err) {
                that.$message.error('用户名或密码错误')
                that.logining = false
                that.loginForm.username = ''
                that.loginForm.password = ''
              }
          )
        } else {
          return (this.logining = false)
        }
      })
    },
    handleRegister(event) {
      this.$router.push('./register')
    },
    handleGithub(event) {
      //github登录授权页面
      let oauth_uri = 'https://github.com/login/oauth/authorize'
      //github中获取
      let client_id = 'Iv1.187f346cb4978b94'
      //授权回调地址
      let redirect_uri = 'http://localhost:8080/oauth/redirect'
      window.location.href = `${oauth_uri}?client_id=${client_id}&redirect_uri=${redirect_uri}`
    }
  }
};
</script>

<style scoped>
.title {
  font-size: 25px;
}
.login-container {
  width: 100%;
  height: 100%;
}
.login-form {
  width: 100%;
  height: 100%;
}
.login-page {
  -webkit-border-radius: 5px;
  border-radius: 20px;
  margin: 180px auto;
  width: 500px;
  height: 300px;
  padding: 35px 35px 15px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

label.el-checkbox.rememberme {
  margin: 0 0 15px;
  text-align: left;
}

.input-text >>> .el-input__inner {
  border-top-width: 0px;
  border-left-width: 0px;
  border-right-width: 0px;
  font-size: 17px;
  border-bottom-width: 1px;
}
</style>
