<template>
 <div >
    <el-card style="width:50%">
        <el-form :model="user" :rules="rules" label-width="80px" style="padding-right:20px" ref="form" >
            <el-form-item label="用户名" prop="username">
                <el-input placeholder="请输入用户名" v-model="user.username" ></el-input>
            </el-form-item>
            <el-form-item label="工号" prop="userid">
                <el-input placeholder="请输入工号" v-model="user.userid" disabled></el-input>
            </el-form-item>
            <!-- <el-form-item label="密码" prop="password">
                <el-input placeholder="请输入密码" v-model="user.password" ></el-input>
            </el-form-item> -->
            <el-form-item label="电话" prop="telephone">
                <el-input placeholder="请输入电话" v-model="user.telephone" ></el-input>
            </el-form-item>
            <el-form-item label="角色" prop="roleid">
                <el-input  v-model="user.role" disabled></el-input>
            </el-form-item>
            <el-form-item label="入职时间" prop="time">
                <el-input  v-model="formattedTime" disabled></el-input>
            </el-form-item>
            <!-- <el-form-item label="负责仓库" prop="deposity">
                <el-input placeholder="请输入仓库名" v-model="user.deposity" ></el-input>
            </el-form-item> -->
            <!-- <el-form-item label="负责仓库" prop="deposity">
                <el-input  v-model="user.deposity" disabled></el-input>
            </el-form-item> -->
            <div>
                <el-button type="primary" @click="update">确 认</el-button>
            </div>


        </el-form>

    </el-card>

 </div>
</template>

<script>
import dayjs from 'dayjs'
 export default {
    name:'app',
    data () {
        var checkNull = (rule, value, callback) => {
        if (value === '' || value === null || value === undefined) {
          return callback(new Error('不能为空'));
        }
        return callback();
      };
        
       return {
        user:JSON.parse(localStorage.getItem('user') || '{}'),
        deposityValue:'',
   
        options: [{
          value: '选项1',
          label: '仓库1'
        }, {
          value: '选项2',
          label: '仓库2'
        }, {
          value: '选项3',
          label: '仓库3'
        }, {
          value: '选项4',
          label: '仓库4'
        }, {
          value: '选项5',
          label: '仓库5'
        }],


        rules: {
            telephone: [
                { required: true, message: '请输入电话号码', trigger: 'change' },
                { min: 11, max: 11, message: '长度为11个字符', trigger: 'change' }
            ],
            username: [
            {required: true, validator: checkNull, trigger: 'blur' }
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
                    this.$request.put('/user/update',this.user).then(res=>{
                        if(res.code==0){
                            this.$message.success('修改成功')
                            localStorage.setItem('user',JSON.stringify(this.user))
                        }else{
                            this.$message.error(res.msg)
                        }
                    })
                }
            })
        },

        handleDepositChange(value) {
      // 根据 value 查找对应的 label
      const selectedOption = this.options.find(item => item.value === value);
      if (selectedOption) {
        this.user.deposity = selectedOption.label; // 将 label 存入 ruleForm
      }
    }

    },
    computed: {
    // 格式化时间
    formattedTime() {
      return this.user.time 
        ? dayjs(this.user.time).format('YYYY-MM-DD') 
        : '';
    }
  }
 }
</script>

<style scoped>


 ::v-deep .el-form-item__label{
    font-weight: bold;
 }

</style>
