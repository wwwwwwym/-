<template>
 <div style="margin-top: 30px;">

<el-steps :active="active" finish-status="success" align-center>
  <el-step title="申请提交"></el-step>
  <el-step title="审核中"></el-step>
  <!-- <el-step title="已完成"></el-step> -->
</el-steps>

<!-- 表单区域 -->
<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" size="small" style="width:600px">
  <el-form-item label="产品名称" prop="pname">
    <el-input v-model="ruleForm.pname"></el-input>
  </el-form-item>
  <el-form-item label="产品数量" prop="quantity">
    <el-input v-model.number="ruleForm.quantity" type="number" min="0"></el-input>
  </el-form-item>
  <el-form-item label="产品价格" prop="price">
    <el-input v-model.number="ruleForm.price" ></el-input>
  </el-form-item>
  <!-- <el-form-item label="原仓库" prop="deposityOld">
    <el-select v-model="ruleForm.deposityOld" placeholder="请选择仓库名称">
      <el-option v-for="item in ['仓库1','仓库2','仓库3']" :key="item" :label="item" :value="item"></el-option>
    </el-select>
  </el-form-item> -->
  <el-form-item label="新仓库" prop="deposityNew">
    <el-select v-model="ruleForm.deposityNew" placeholder="请选择仓库名称">
      <el-option v-for="item in ['仓库1','仓库2','仓库3']" :key="item" :label="item" :value="item"></el-option>
    </el-select>
  </el-form-item>
    <el-form-item label="审核人" prop="reviewId">
    <el-select v-model="ruleForm.reviewId" placeholder="请选择审核人">
      <el-option v-for="item in ['2001','7933','333']" :key="item" :label="item" :value="item"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="申请备注" >
    <el-input type="textarea" v-model="ruleForm.applyRemark"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="apply">提交申请</el-button>
    <el-button @click="resetForm('ruleForm')">重置</el-button>
  </el-form-item>
</el-form>



<!-- <el-button style="margin-top: 12px;" @click="next">提交申请</el-button> -->

 </div>
</template>

<script>

 export default {
    
    data () {
       return {
        active: 0,
        user:JSON.parse(localStorage.getItem('user') || '{}'),
        ruleForm: {
          pname: '',
          type: '',
          deposityOld:'',
          deposityNew: '',
          quantity: '',
          price: '',
          state:'待审核',
          applyId:'',
          applyTime: '',
          applyRemark: '',
          reviewId: '',
        },
        rules: {
          pname: [
            { required: true, message: '请输入产品名称', trigger: 'blur' }, 
        ],
          quantity:[
            { required: true, message: '请输入产品数量', trigger: 'blur' },
            { validator: (rule, value, callback) => {
                if (typeof value !== 'number' || isNaN(value)) {
                    callback(new Error('必须输入数字'));
                } else if (value < 0) {
                    callback(new Error('数量不能为负数'));
                } else {
                    callback();
                }
              },
            trigger: ['blur'] }
          ],
          price:[
            { required: true, message: '请输入产品价格', trigger: 'blur' },
            { validator: (rule, value, callback) => {
                if (typeof value !== 'number' || isNaN(value)) {
                    callback(new Error('必须输入数字'));
                } else if (value < 0) {
                    callback(new Error('价格不能为负数'));
                } else {
                    callback();
                }
              },
            trigger: ['blur'] }
          ],
          deposityNew: [
            { required: true, message: '请选择仓库', trigger: 'change' }
          ],
          deposityOld: [
            { required: true, message: '请选择仓库', trigger: 'change' }
          ],
          reviewId: [
            { required: true, message: '请选择审核人', trigger: 'change' }
          ],
        }

       }
    },
    components: {
        

    },
    methods: { 
        next() {
            this.active=1;
        },
        apply(){
         this.$refs.ruleForm.validate((valid) => {
          this.ruleForm.type='调货单';
          this.ruleForm.applyId=this.user.userid;
          this.ruleForm.deposityOld=this.user.deposity;
          console.log(this.ruleForm)
                if(valid){
                    this.$request.post('/recordIn/add',this.ruleForm).then(res => {
                      if(res.code===0)
                      {
                        this.$message.success("提交成功");
                        this.active=1;
                      }else{
                        this.$message.error(res.msg);
                      }
                    })
                }
            })

        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }
    }
 }
</script>

<style scoped>
.demo-ruleForm{
    margin: 0 auto;        /* 水平居中 */
    padding: 20px;
    margin-top:50px
}

.el-form-item {
  margin-bottom: 22px; /* 默认是 18px，增加间距 */
  text-align: left;
}
 
</style>
