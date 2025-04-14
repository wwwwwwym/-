<template>
 <div id='app'>
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" style="width:400px">
    <h2 class="text-center">注册</h2>
    <el-form-item label="工号" prop="id">
        <el-input v-model.number="ruleForm.userid"></el-input>
    </el-form-item>
    
    <el-form-item label="用户名" prop="name">
        <el-input v-model="ruleForm.userName"></el-input>
    </el-form-item>

    <el-form-item label="密码" prop="pass">
        <el-input type="password" v-model="ruleForm.passwrd" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="checkPass">
        <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
    </el-form-item>
    
    <el-form-item>                      
        <el-button type="primary" @click="submit">注册</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>

    <p class="text-center mt-3">已有账号? <a href="#"  @click="tologin">点击登录</a></p>
    </el-form>

 </div>
</template>

<script>
import request from '@/request/request';
// import request from '@/request/request'
 export default {
    name:'app',
    data () {
       return {
        ruleForm: {
          password: '',
          checkPass: '',
          userid: '',
          userName: '',
       },
      //  rules: {
      //     pass: [
      //       { required: true, message: '输入密码', trigger: 'blur' },
      //       {
      //         validator : (rule, value, callback) => {
      //         if (value === '') {
      //           callback(new Error('请输入密码'));
      //         }
      //         // else {
      //         //   if (this.ruleForm.checkPass !== '') {
      //         //     this.$refs.ruleForm.validateField('checkPass');
      //         //   }
      //         //   callback();
      //         // }
      //       },
      //       trigger: ['blur'] }
      //     ],
      //     checkPass: [
      //       { required: true, message: '请再次输入密码', trigger: 'blur' },
      //       {validator : (rule, value, callback) => {
      //         if (value === '') {
      //           callback(new Error('请再次输入密码'));
      //         } else if (value !== this.ruleForm.pass) {
      //           callback(new Error('两次输入密码不一致!'));
      //         } else {
      //           callback();
      //         }
      //       },
      //       trigger: ['blur'] }
      //     ],
      //     id: [
      //       { required: true, message: '请输入工号', trigger: 'blur' },
      //       { validator: (rule, value, callback) => {
      //           if (typeof value !== 'number' || isNaN(value)) {
      //               callback(new Error('必须输入数字'));
      //           } else if (value.length !== 4) {
      //               callback(new Error('必须是4位数字值'));
      //           } else {
      //               callback();
      //           }
      //         },
      //       trigger: ['blur'] }
      //     ],
      //     name: [
      //       { required: true, message: '请输入用户名', trigger: 'blur' }, 
      //     ]
      //   }
       };
    },
    components: {

    },
    methods: { 
      submit(){
        request.post("/user/register",this.ruleForm).then(res=>{
          if(res.code==0){
            this.$notify.success("注册成功")
            this.$router.push({path:'/login'})
          }else{
            this.$notify.error("res.msg")
          }
        })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      tologin(){
        this.$router.push({path:'/login'})
      }

    }

}
</script>

<style scoped>
.demo-ruleForm{
    margin: 0 auto;        /* 水平居中 */
    padding: 20px;
    margin-top:250px;
    border: 1px solid #ccc;
}

.el-form-item {
  margin-bottom: 22px; /* 默认是 18px，增加间距 */
  text-align: left;
}              
 
</style>
