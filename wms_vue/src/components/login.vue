<template>
 <div id='app'>
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" style="width:400px">
    <h2 class="text-center">登录</h2>
    <el-form-item label="工号" prop="id">
        <el-input v-model.number="ruleForm.id"></el-input>
    </el-form-item>

    <el-form-item label="密码" prop="pass">
        <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
    </el-form-item>
    
    <el-form-item>                      
        <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
        <!-- <el-button type="primary" @click="tomain">登录</el-button> -->
        <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>

    <p class="text-center mt-3">已有账号? <a href="#"  @click="toregister">点击注册</a></p>
    </el-form>

 </div>
</template>

<script>
// import request from '@/request/request'
 export default {
    name:'app',
    data () {
//id校验
      var checkId = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('工号不能为空'));
        }
        setTimeout(() => {
          if (!Number.isInteger(value)) {
            callback(new Error('请输入数字值'));
          } else {
            if (length < 4) {
              callback(new Error('必须是4位数字值'));
            } else {
              callback();
            }
          }
        }, 1000);
      };

//密码校验
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm.checkPass !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };

       return {
        comName:"login",
        ruleForm: {
          pass: '',
          id: ''
       },
       rules: {
          pass: [
            { validator: validatePass, trigger: 'blur' }
          ],
          id: [
            { validator: checkId, trigger: 'blur' }
          ],
        }
       };
    },
    components: {

    },
    methods: { 
        submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      toregister(){
        this.$router.push({path:'/register'})
      },
      tomain(){
        this.$router.push({path:'/main'})
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
