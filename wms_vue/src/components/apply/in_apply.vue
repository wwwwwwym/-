<template>
 <div style="margin-top: 30px;">

<el-steps :active="active" finish-status="success" align-center>
  <el-step title="申请提交"></el-step>
  <el-step title="审核中"></el-step>
  <el-step title="已完成"></el-step>
</el-steps>

<!-- 表单区域 -->
<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" size="small" style="width:600px">
  <el-form-item label="产品名称" prop="name">
    <el-input v-model="ruleForm.name"></el-input>
  </el-form-item>
  <el-form-item label="产品数量" prop="number">
    <el-input v-model.number="ruleForm.number" type="number" min="0"></el-input>
  </el-form-item>
  <el-form-item label="产品价格" prop="price">
    <el-input v-model.number="ruleForm.price" ></el-input>
  </el-form-item>
  <el-form-item label="操作仓库" prop="region">
    <el-select v-model="ruleForm.region" placeholder="请选择仓库名称">
      <el-option label="仓库一" value="shanghai"></el-option>
      <el-option label="仓库二" value="beijing"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="操作类型" prop="in_out">
    <el-radio-group v-model="ruleForm.in_out">
      <el-radio label="出库"></el-radio>
      <el-radio label="入库"></el-radio>
    </el-radio-group>
  </el-form-item>
    <el-form-item label="审核人" prop="people">
    <el-select v-model="ruleForm.people" placeholder="请选择审核人">
      <el-option label="审核人一" value="shanghai"></el-option>
      <el-option label="审核人二" value="beijing"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="申请备注" >
    <el-input type="textarea" v-model="ruleForm.desc"></el-input>
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
        ruleForm: {
          name: '',
          number: '',
          price: '',
          region: '',
          in_out: '',
          people: '',
          desc: ''
        },
        rules: {
          name: [
            { required: true, message: '请输入产品名称', trigger: 'blur' }, 
        ],
          number:[
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
          region: [
            { required: true, message: '请选择仓库', trigger: 'change' }
          ],
          in_out: [
            { required: true, message: '请选择操作类型', trigger: 'change' }
          ],
          people: [
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
                if(valid){
                    this.$request.post({
                      url:this.ruleForm.in_out==1? '/recordIn/addRecordIn':'/recordOut/addRecordOut',
                      data: this.ruleForm
                    }).then(res => {
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
