<template>
 <div >
   <el-card style="width:50%">
   <el-form :model="user" :rules="rules" ref="form" label-width="80px" style="padding-right:20px" >
      <el-form-item label="原始密码" prop="password">
         <el-input show-password placeholder="请输入原始密码" v-model="user.password" disabled></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
         <el-input show-password placeholder="请输入新密码" v-model="user.newPassword"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPassword">
         <el-input show-password placeholder="请再次确认密码" v-model="user.checkPassword"></el-input>
      </el-form-item>
      <div>
         <el-button type="primary" @click="update">确 认</el-button>
      </div>
        </el-form>
   </el-card>
 </div>
</template>

<script>
 export default {
    name:'app',
    data () {

      var validatePass = (rule, value, callback) => {
        if (value === '') {
          return callback(new Error('请输入密码'));
        } else {
          if (this.user.checkPassword !== '') {
            this.$refs.form.validateField('checkPassword');
          }
          return callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          return callback(new Error('请再次输入密码'));
        } else if (value !== this.user.newPassword) {
          return callback(new Error('两次输入密码不一致!'));
        } else {
          return callback();
        }
      };

       return {
         user:JSON.parse(localStorage.getItem('user') || '{}'),

      //    ruleForm: {
      //     newPassword: '',
      //     checkPassword: '',
      //  },
       rules: {
          newPassword: [
            { required: true,validator: validatePass, trigger: 'blur' }
          ],
          checkPassword: [
            { required: true,validator: validatePass2, trigger: 'change' }
          ],
        },



       }
    },
    components: {

    },
    methods: { 
      update(){
         this.$refs.form.validate((valid) => {
         if(valid){
            this.user.password = this.user.newPassword
            this.$request.put('/user/update',this.user).then(res=>{
                if(res.code==0){
                   this.$message.success('修改成功')
                   this.$router.push('/login')
               }else{
                   this.$message.error(res.msg)
               }
            })
         }
         })
           
        },

    }
 }
</script>

<style scoped>

 ::v-deep .el-form-item__label{
    font-weight: bold;
 }
 
</style>
