<template>
 <div >
    <div style="margin-top:20px">
        <div style="margin-bottom:20px ;font-size:20px;text-align:left">已提交申请（{{ applications.length }}）</div>
        <el-row :gutter="20">
            <el-col v-for="application in applications" :key="application.recordId" :xs="24" :sm="12" :md="8" :lg="6">
                <el-card class="application-card" shadow="hover" style="text-align:left">
                    <!-- 卡片头部 -->
                    <div slot="header" class="card-header">
                    <span>{{ application.applyId }}的申请</span>
                    <el-button type="warning" size="mini" effect="dark" style="float:right" @click="pendingDetail(application)">待审核</el-button>
                    </div>
                    <!-- 申请内容 -->
                    <div>
                        <div>申请类型：{{ application.type }}</div>
                        <div>变动产品：{{application.pname}}</div>
                        <div>产品去向：{{application.deposityOld}}————{{application.deposityNew}}</div>
                        <div>申请时间：{{ application.applyTime }}</div>
                        <div>审核人：{{ application.reviewId }}</div>
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>


    <div style="margin-top:20px">
        <div style="margin-bottom:20px ;font-size:20px;text-align:left">已通过申请（{{ completeds.length }}）</div>
        <el-row :gutter="20">
            <el-col v-for="completed in completeds" :key="completed.recordId" :xs="24" :sm="12" :md="8" :lg="6">
                <el-card class="completed-card" shadow="hover" style="text-align:left">
                    <!-- 卡片头部 -->
                    <div slot="header" class="card-header">
                    <span>{{ completed.applyId }}的申请</span>
                    <el-button type="success" size="mini" effect="dark" style="float:right" @click="completedDetail(completed)">已通过</el-button>
                    </div>
                    <!-- 申请内容 -->
                    <div>
                        <div>申请类型：{{ completed.type }}</div>
                        <div>变动产品：{{completed.pname}}</div>
                        <div>产品去向：{{completed.deposityOld}}————{{completed.deposityNew}}</div>
                        <div>申请时间：{{ completed.applyTime }}</div>
                        <div>审核人：{{ completed.reviewId }}</div>
                        <div>审核时间：{{completed.reviewTime }}</div>
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>

    <div style="margin-top:20px">
        <div style="margin-bottom:20px ;font-size:20px;text-align:left">已驳回申请（{{ rejecteds.length }}）</div>
        <el-row :gutter="20">
            <el-col v-for="rejected in rejecteds" :key="rejected.recordId" :xs="24" :sm="12" :md="8" :lg="6">
                <el-card class="rejected-card" shadow="hover" style="text-align:left">
                    <!-- 卡片头部 -->
                    <div slot="header" class="card-header">
                    <span>{{ rejected.applyId }}的申请</span>
                    <el-button type="danger" size="mini" effect="dark" style="float:right" @click="rejectedDetail(rejected)">已驳回</el-button>
                    </div>
                    <!-- 申请内容 -->
                    <div>
                        <div>申请类型：{{ rejected.type }}</div>
                        <div>变动产品：{{rejected.pname}}</div>
                        <div>产品去向：{{rejected.deposityOld}}————{{rejected.deposityNew}}</div>
                        <div>申请时间：{{ rejected.applyTime }}</div>
                        <div>审核人：{{ rejected.reviewId }}</div>
                        <div>审核时间：{{rejected.reviewTime }}</div>
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>



    <!-- 待审核弹出框 -->
<el-dialog title="申请详情" :visible.sync="editFormVisible" width="40%">
<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" size="small" >
  <div style="margin:25px ; text-align:center;justify-content:center;display:flex" >
          <img v-if="ruleForm.picture" :src="ruleForm.picture" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
  </div>
  <el-form-item label="产品名称" prop="pname">
    <el-input v-model="ruleForm.pname"></el-input>
  </el-form-item>
  <el-form-item label="产品数量" prop="quantity">
    <el-input v-model.number="ruleForm.quantity" type="number" min="0"></el-input>
  </el-form-item>
  <el-form-item label="产品价格" prop="price">
    <el-input v-model.number="ruleForm.price" ></el-input>
  </el-form-item>
  <el-form-item label="原仓库" prop="deposityOld">
    <el-select v-model="ruleForm.deposityOld" placeholder="请选择仓库名称" style="width:100%" clearable>
      <el-option v-for="item in ['1号仓库','2号仓库','3号仓库','4号仓库','5号仓库']" :key="item" :label="item" :value="item"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="新仓库" prop="deposityNew">
    <el-select v-model="ruleForm.deposityNew" placeholder="请选择仓库名称" style="width:100%" clearable>
      <el-option v-for="item in ['1号仓库','2号仓库','3号仓库','4号仓库','5号仓库']" :key="item" :label="item" :value="item"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="操作类型" prop="type">
    <el-radio-group v-model="ruleForm.type">
      <el-radio label="出货单"></el-radio>
      <el-radio label="进货单"></el-radio>
      <el-radio label="调货单"></el-radio>
    </el-radio-group>
  </el-form-item>
    <el-form-item label="审核人" prop="reviewId">
    <el-select v-model="ruleForm.reviewId" placeholder="请选择审核人" style="width:100%">
      <el-option v-for="item in ['2001','2002','2003','7933']" :key="item" :label="item" :value="item"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="申请备注" >
    <el-input type="textarea" v-model="ruleForm.applyRemark"></el-input>
  </el-form-item>
</el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="editFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="apply">确 定</el-button>
    </div>
</el-dialog>


    <!-- 已完成弹出框 -->
<el-dialog title="申请详情" :visible.sync="completedFormVisible" width="40%">
<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" size="small" >
  <div style="margin:25px ; text-align:center;justify-content:center;display:flex" >
          <img v-if="ruleForm.picture" :src="ruleForm.picture" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
  </div>
  <el-form-item label="产品名称">
    <el-input v-model="ruleForm.pname" disabled></el-input>
  </el-form-item>
  <el-form-item label="产品数量">
    <el-input v-model.number="ruleForm.quantity" type="number" min="0" disabled></el-input>
  </el-form-item>
  <el-form-item label="产品价格">
    <el-input v-model.number="ruleForm.price" disabled></el-input>
  </el-form-item>
  <el-form-item label="原仓库" prop="deposityOld">
    <el-select v-model="ruleForm.deposityOld" style="width:100%" disabled>
      <el-option v-for="item in ['1号仓库','2号仓库','3号仓库','4号仓库','5号仓库']" :key="item" :label="item" :value="item"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="新仓库">
    <el-select v-model="ruleForm.deposityNew" style="width:100%" disabled>
      <el-option v-for="item in ['1号仓库','2号仓库','3号仓库','4号仓库','5号仓库']" :key="item" :label="item" :value="item"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="操作类型">
    <el-radio-group v-model="ruleForm.type" disabled>
      <el-radio label="出货单"></el-radio>
      <el-radio label="进货单"></el-radio>
      <el-radio label="调货单"></el-radio>
    </el-radio-group>
  </el-form-item>
    <el-form-item label="审核人">
    <el-select v-model="ruleForm.reviewId" placeholder="请选择审核人" style="width:100%" disabled>
      <el-option v-for="item in ['2001','2002','2003','7933']" :key="item" :label="item" :value="item"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="申请备注" >
    <el-input type="textarea" v-model="ruleForm.applyRemark" disabled></el-input>
  </el-form-item>
  <el-form-item label="审核意见" >
    <el-input type="textarea" v-model="ruleForm.reviewRemark" disabled></el-input>
  </el-form-item>
</el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="completedFormVisible = false">取 消</el-button>
    </div>
</el-dialog>

    <!-- 已驳回弹出框 -->
<el-dialog title="申请详情" :visible.sync="rejectedFormVisible" width="40%">
<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" size="small" >
  <div style="margin:25px ; text-align:center;justify-content:center;display:flex" >
          <img v-if="ruleForm.picture" :src="ruleForm.picture" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
  </div>
  <el-form-item label="产品名称">
    <el-input v-model="ruleForm.pname" disabled></el-input>
  </el-form-item>
  <el-form-item label="产品数量">
    <el-input v-model.number="ruleForm.quantity" type="number" min="0" disabled></el-input>
  </el-form-item>
  <el-form-item label="产品价格">
    <el-input v-model.number="ruleForm.price" disabled></el-input>
  </el-form-item>
  <el-form-item label="原仓库" prop="deposityOld">
    <el-select v-model="ruleForm.deposityOld" style="width:100%" disabled>
      <el-option v-for="item in ['1号仓库','2号仓库','3号仓库','4号仓库','5号仓库']" :key="item" :label="item" :value="item"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="新仓库">
    <el-select v-model="ruleForm.deposityNew" style="width:100%" disabled>
      <el-option v-for="item in ['1号仓库','2号仓库','3号仓库','4号仓库','5号仓库']" :key="item" :label="item" :value="item"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="操作类型">
    <el-radio-group v-model="ruleForm.type" disabled>
      <el-radio label="出货单"></el-radio>
      <el-radio label="进货单"></el-radio>
      <el-radio label="调货单"></el-radio>
    </el-radio-group>
  </el-form-item>
    <el-form-item label="审核人">
    <el-select v-model="ruleForm.reviewId" placeholder="请选择审核人" style="width:100%" disabled>
      <el-option v-for="item in ['2001','2002','2003','7933']" :key="item" :label="item" :value="item"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="申请备注" >
    <el-input type="textarea" v-model="ruleForm.applyRemark" disabled></el-input>
  </el-form-item>
   <el-form-item label="审核意见" >
    <el-input type="textarea" v-model="ruleForm.reviewRemark" disabled></el-input>
  </el-form-item>
</el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="rejectedFormVisible = false">取 消</el-button>
    </div>
</el-dialog>





 </div>
</template>

<script>
import request from '@/request/request'
 export default {
    name:'app',
    data () {
       return {
        applications: [],
        completeds: [],
        rejecteds: [],
 
        editFormVisible: false,
        completedFormVisible:false,
        rejectedFormVisible:false,

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
          reviewRemark: '',
          picture: '',
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

          type: [
            { required: true, message: '请选择操作类型', trigger: 'change' }
          ],
          reviewId: [ 
            { required: true, message: '请选择审核人', trigger: 'change' }
          ],
        },


       }
    },
    mounted () {
        this.loadpendings();
        this.loadcompleteds();
        this.loadrejecteds();
    },
    methods: { 
        loadpendings(){
            request.get('/recordIn/myPendingData').then(res =>{
            if(res.code==0)
            {
                console.log(res.data)
                this.applications=res.data;
            }
        })
        },
        loadcompleteds(){
            request.get('/recordIn/myCompletedData').then(res =>{
            if(res.code==0)
            {
                this.completeds=res.data;
            }
        })
        },
        loadrejecteds(){
            request.get('/recordIn/myRejectedData').then(res =>{
            if(res.code==0)
            {
                this.rejecteds=res.data;
            }
        })
        },
        pendingDetail(application){//编辑
            console.log("当前卡片的数据：", application);
            this.ruleForm=application;
            this.editFormVisible=true
        },
        completedDetail(completed){//编辑
            console.log("当前卡片的数据：", completed);
            this.ruleForm=completed;
            this.completedFormVisible=true
        },
        rejectedDetail(rejected){//编辑
            console.log("当前卡片的数据：", rejected);
            this.ruleForm=rejected;
            this.rejectedFormVisible=true
        },
        apply(){
         this.$refs.ruleForm.validate((valid) => {
          console.log(this.ruleForm)
                if(valid){
                    this.$request.put('/recordIn/update',this.ruleForm).then(res => {
                      if(res.code===0)
                      {
                        this.$message.success("修改成功");
                        this.editFormVisible=false;
                      }else{
                        this.$message.error(res.msg);
                      }
                    })
                }
            })

        },

    }
 }
</script>

<style scoped>
 ::v-deep .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  
   ::v-deep .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
 
</style>
