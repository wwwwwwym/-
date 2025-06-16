<template>
 <div id='app'>
    <div :style="{ backgroundImage: `url('${require('@/assets/background.jpg')}'` }" style="height:100vh; display:flex; justify-content:center; align-items:center; background-size:cover">     
    <div style="display:flex; align-items:center;justify-content:center; background-color:white; width:30% ; height:50%; border-radius:5px">
      <el-form :model="ruleForm" style="width:80%;" :rules="rules" ref="ruleForm"  class="demo-ruleForm" >
      <!-- <el-form > -->
        <div style="font-size:20px ;font-weight:bold ; text-align:center;">欢迎登录仓库管理系统</div>
        <el-form-item prop="userid" style="margin-top:20px;">
            <el-input placeholder=" 请输入工号"  v-model="ruleForm.userid"></el-input>
        </el-form-item>

        <el-form-item prop="password">
            <el-input show-password placeholder=" 请输入密码" type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item>                      
            <el-button type="primary" @click="login">登录</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
        <div > 
          <div>  <p class="text-center mt-3">没有账号? <a href="#"  @click="toregister">点击注册</a></p></div>
          <div> <p class="text-center mt-3">忘记密码? <a href="#"  @click="handleForgetPass">点击修改</a></p></div>
        </div>
      </el-form>
    </div>
  </div>

  <el-dialog title="忘记密码" :visible.sync="forgetPassDialog" width="30%">
  <el-form :model="forgetUserForm" :rules="rules" ref="forgetUserForm"  label-width="80px" style="padding-right:20px">
    <el-form-item label="用户工号" prop="userid" >
      <el-input v-model="forgetUserForm.userid" autocomplete="off" placeholder="请输入工号"></el-input>
    </el-form-item>
    <el-form-item label="手机号" prop="telephone">
      <el-input v-model="forgetUserForm.telephone" autocomplete="off" placeholder="请输入手机号"></el-input>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="forgetPassDialog = false">取 消</el-button>
    <el-button type="primary" @click.prevent="resetPassword">确 定</el-button>
  </div>
  </el-dialog>


  

 </div>
</template>

<script>
import request from '@/request/request'
 export default {
    name:'app',
    data () {
//id校验
      var checkId = (rule, value, callback) => {
        const num = Number(value);
        if (isNaN(num)) { // 检查是否为有效数字
          return callback(new Error('请输入数字值')); // 返回并终止
        }

        if (!Number.isInteger(num)) { // 检查是否为整数
          return callback(new Error('必须为整数')); // 返回并终止
        }

        if (value.length !== 4) { // 检查原始输入长度
          return callback(new Error('必须是4位工号')); // 返回并终止
        }
        return callback();
      };
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          return callback(new Error('请输入密码'));
        }
        return callback();
      };

       return {
        forgetUserForm:{
          userid:'',
          telephone:''
        },//忘记密码表单数据
        forgetPassDialog:false,
        ruleForm: {
          password: '',
          userid: '',
       },
       rules: {
          password: [
            { validator: validatePass, trigger: 'change' }
          ],
          userid: [
            { validator: checkId, trigger: 'change' }
          ],
          telephone: [
            { required: true, message: '请输入电话号码', trigger: 'change' },
            { min: 11, max: 11, message: '长度为11个字符', trigger: 'change' }
          ],
        }
       };
    },
    components: {

    },
    methods: { 
      
      handleForgetPass(){//初始化表单
      this.forgetUserForm={};
      this.forgetPassDialog = true;
      },

      resetPassword(){
        this.$refs["forgetUserForm"].validate((valid) => {
        if(valid){
        this.$request.put("/user/forgetPass",this.forgetUserForm).then(res=>{
          if(res.code==0){
            this.$message.success("已重置为初始密码");
            this.forgetPassDialog = false;
          }else{
            this.$message.error(res.msg);
          }
        })
        }
        })
      },
      login(){
        this.$refs["ruleForm"].validate((valid) => {
          // console.log("valid:",valid);
          if (valid){
            request.post('/user/login',this.ruleForm).then(res=>{
              if(res.code==0){
                localStorage.setItem('user',JSON.stringify(res.data));//存储用户数据
                this.$message.success("登录成功");
                this.$router.push({path:'/intro'})
                
              }else{
                this.$message.error(res.msg);
              }
            })
          }  
        })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      toregister(){
        this.$router.push({path:'/register'})
      },

        
    }

}
</script>

<style scoped>


.el-form-item {
  margin-bottom: 22px; /* 默认是 18px，增加间距 */

}              
 
</style>
