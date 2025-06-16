<template>
 <div id='user_register'>
  <div :style="{ backgroundImage: `url('${require('@/assets/background.jpg')}'` }" style="height:100vh; display:flex; justify-content:center; align-items:center;background-size:cover">     
    <div style="display:flex; align-items:center;justify-content:center; background-color:white; width:30% ; height:70%; border-radius:5px">
      <el-form :model="ruleForm" style="width:80%;" :rules="rules" ref="ruleForm"  class="demo-ruleForm" >
      <!-- <el-form > -->
        <div style="font-size:20px ;font-weight:bold ; text-align:center;">欢迎注册仓库管理系统</div>
        <el-form-item prop="userid" style="margin-top:20px;">
            <el-input placeholder=" 请输入工号"  v-model="ruleForm.userid"></el-input>
        </el-form-item>
        
        <el-form-item prop="username">
            <el-input placeholder=" 请输入姓名" v-model="ruleForm.username"></el-input>
        </el-form-item>
        

        <el-form-item prop="password">
            <el-input show-password placeholder=" 请输入密码" type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="checkPass">
            <el-input show-password placeholder=" 请再次输入密码" type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
        </el-form-item>
        
        <!-- <el-select v-model="ruleForm.deposityValue" @change="handleDepositChange" placeholder="请选择所负责仓库" style="width:100%;">
          <el-option  v-for="item in options" :key="item.value" :label="item.label" :value="item.value" >
          </el-option>
        </el-select> -->

        <el-form-item prop="telephone">
            <el-input placeholder=" 请输入电话号码" v-model="ruleForm.telephone"></el-input>
        </el-form-item>

        <el-form-item>                      
            <el-button type="primary" @click="register">注册</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
        <div> <p class="text-center mt-3">已有账号? <a href="#"  @click="tologin">点击登录</a></p></div>
      </el-form>
    </div>
  </div>
 </div>
</template>

<script>
import request from '@/request/request';
// import request from '@/request/request'
 export default {
    name:'user_register',
    data () {
      var checkName = (rule, value, callback) => {
        if (value=='') {
          return callback(new Error('姓名不能为空'));
        }
        return callback();
      };
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
        } else {
          if (this.ruleForm.checkPass !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          return callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          return callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.password) {
          return callback(new Error('两次输入密码不一致!'));
        } else {
          return callback();
        }
      };



       return {
        ruleForm: {
          password: '',
          checkPass: '',
          userid: '',
          username: '',
          telephone: '',
          deposityValue: '',
          deposity: '',
       },
       rules: {
          password: [
            { validator: validatePass, trigger: 'blur' }
          ],
          checkPass: [
            { validator: validatePass2, trigger: 'blur' }
          ],
          username: [
            { validator: checkName, trigger: 'blur' }
          ],
          userid: [
            { validator: checkId, trigger: 'change' }
          ],
          telephone: [
            { required: true, message: '请输入电话号码', trigger: 'change' },
            { min: 11, max: 11, message: '长度为11个字符', trigger: 'change' }
          ],
        },

       };
    },
    components: {

    },
    methods: { 
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      tologin(){
        this.$router.push({path:'/login'})
      },
      register(){
        this.$refs["ruleForm"].validate((valid) => {
          
          if (valid){
            request.post('/user/register',this.ruleForm).then(res=>{
              console.log("ruleForm:",this.ruleForm);
              if(res.code==0){
                this.$message.success("注册成功");
                this.$router.push({path:'/login'})
              }else{
                this.$message.error(res.msg);
              }
              
            })
          }
        })
      },

      handleDepositChange(value) {
      // 根据 value 查找对应的 label
      const selectedOption = this.options.find(item => item.value === value);
      if (selectedOption) {
        this.ruleForm.deposity = selectedOption.label; // 将 label 存入 ruleForm
      }
    }

    }

}
</script>

<style scoped>
/* .demo-ruleForm{
    margin: 0 auto;      
    padding: 20px;
    margin-top:250px;
} */

.el-form-item {
  margin-top: 22px;
}              
 
</style>
