<template>
  <div>
    <el-form ref="form">
      <el-form-item class="text_left">
        <el-row :gutter="20">
          <el-col :span="8">
            <div>
              Passwd
              <br>
              <el-input placeholder="passwd here" show-password v-model="passwd" @change="handlePasswd"></el-input>
            </div>
          </el-col>

          <el-col :span="8">
            <div>
              ?
              <br>
              <el-input placeholder="?"></el-input>
            </div>
          </el-col>

<!--          <el-col :span="8">-->
<!--            &lt;!&ndash;          <div>&ndash;&gt;-->
<!--            &lt;!&ndash;            ?&ndash;&gt;-->
<!--            &lt;!&ndash;            <br>&ndash;&gt;-->
<!--            &lt;!&ndash;            <el-input placeholder="?"></el-input>&ndash;&gt;-->
<!--            &lt;!&ndash;          </div>&ndash;&gt;-->
<!--            <el-button @click="handleGithub" v-show="githubbind === false">bind to github</el-button>-->
<!--            <div v-show="githubbind">-->
<!--              You are now bind with github profile-->
<!--              <el-button @click="handleGithubUntie" v-show="githubbind === true">Untie to github</el-button>-->
<!--            </div>-->
<!--          </el-col>-->

        </el-row>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "ChangePasswd",
  data() {
    return {
      // githubbind: false,
      passwd: ''
    }
  },
  props: ['username'],
  methods: {
    // handleGithub(event) {
    //   //github登录授权页面
    //   let oauth_uri = 'https://github.com/login/oauth/authorize'
    //   //github中获取
    //   let client_id = 'Iv1.187f346cb4978b94'
    //   //授权回调地址
    //   let redirect_uri = 'http://localhost:8080/oauth/bind'
    //   window.location.href = `${oauth_uri}?client_id=${client_id}&redirect_uri=${redirect_uri}`
    // },
    // handleGithubUntie(event) {
    //   const that = this
    //   axios.post(
    //       'http://localhost:8081/api/user/unbundgithub',
    //       {},
    //       {
    //         headers: {
    //           Authorization: window.localStorage.getItem('token')
    //         }
    //       }
    //   ).then(
    //       function (response) {
    //         if (response.data.code === 200) {
    //           this.$message({
    //                 message: 'Unite success',
    //                 type: 'success'
    //               }
    //           )
    //           location.reload();
    //         } else {
    //           this.$message({
    //             message: 'Unite failed',
    //             type: 'error'
    //           })
    //         }
    //       },
    //       function (err) {
    //         this.$message({
    //               message: 'server failed',
    //               type: 'error'
    //             }
    //         )
    //       }
    //   )
    // },
    handlePasswd(val){
      if(val.length < 6 || val.length > 15){
        this.$message({
          message: 'not a valid password',
          type: 'error'
        })
        return
      }
      // console.log(val)
      const that = this
      axios.post(
          '/user/edituser',
          {
            password: that.$md5(val)
          },
          {
            headers: {
              Authorization: window.sessionStorage.getItem('token')
            }
          }
      ).then(
          function (response) {
            if (response.data.code === 200) {
              that.$message({
                message: 'password change success',
                type: 'success'
              })
              let newToken = response.headers.authorization
              if(newToken != null) window.sessionStorage.setItem('token', newToken)
            }
            else {
              that.$message({
                message: 'password change failed',
                type: 'error'
              })
              let newToken = response.headers.authorization
              if(newToken != null) window.sessionStorage.setItem('token', newToken)
            }
          },
          function (err) {
            that.$message({
              message: 'server failed',
              type: 'error'
            })
          }
      )
    }
  }
}
</script>

<style scoped>

</style>
