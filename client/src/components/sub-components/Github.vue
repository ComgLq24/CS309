<template>
  <div>
    <div>{{ githubUser }}</div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      githubUser: "github登陆中..."
    };
  },
  mounted() {
    // TODO :  Login with github profile (need api)
    let code = this.$route.query.code;
    console.log(code)
    const that = this
    axios.post(
        '/user/loginbygithub',
        {
          code:code
        }
    ).then(
        function (response) {
          if(response.data.code === 200){
            that.$message({
              message: 'github login success',
              type: 'success'
            })
            console.log(response)
            let token = response.data.data
            window.sessionStorage.setItem('token', token)
            let tmpusername = response.data.msg
            that.$router.push({name: 'Main', params: {username: tmpusername}})
          }else {
            that.$message({
              message: 'github帐号未绑定任何帐号，请重新登录',
              type: 'error'
            })
            that.$router.push({name: 'login'})
          }
        },
        function (err) {
          that.$message({
            message: 'github login failed, please register',
            type: 'error'
          })
          that.$router.push({name: 'login'})
        }
    )
    // console.log(code)
    // cannot do it on client
    // const that = this
    // axios.post(
    //     'https://github.com/login/oauth/access_token',
    //     {
    //       client_secret: 'f152c15e30334c9b2357d7fd37075ba6e6adceb4',
    //       client_id: 'Iv1.187f346cb4978b94',
    //       code: code
    //     }
    // ).then(
    //     function (response) {
    //       console.log(response)
    //     },
    //     function (err) {
    //       console.log(err)
    //     }
    // )
  }
};
</script>

<style>
</style>
