<template>
 <div style="display:flex ;flex-direction:column">

    <div style="margin-top:20px">
        <div style="margin-bottom:20px ;font-size:20px;text-align:left">待审核申请（{{ pendings.length }}）</div>
        <el-row :gutter="20">
            <el-col v-for="pending in pendings" :key="pending.recordId" :xs="24" :sm="12" :md="8" :lg="6">
                <el-card class="pending-card" shadow="hover" style="text-align:left">
                    <!-- 卡片头部 -->
                    <div slot="header" class="card-header">
                    <span>{{ pending.applyId }}的申请</span>
                    <el-button type="warning" size="mini" effect="dark" style="float:right" @click="pendingDetail(pending)">待审核</el-button>
                    </div>
                    <!-- 申请内容 -->
                    <div>
                        <div>申请类型：{{ pending.type }}</div>
                        <div>变动产品：{{pending.pname}}</div>
                        <div>产品去向：{{pending.deposityOld}}————{{pending.deposityNew}}</div>
                        <div>申请时间：{{ pending.applyTime }}</div>
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>

    <div style="margin-top:20px">
        <div style="margin-bottom:20px;font-size:20px;text-align:left">近10条完成申请</div>
        <el-row :gutter="20">
            <el-col v-for="completed in completeds" :key="completed.recordId" :xs="24" :sm="12" :md="8" :lg="6">
                <el-card class="completed-card" shadow="hover" style="text-align:left">
                    <!-- 卡片头部 -->
                    <div slot="header" class="card-header">
                    <span>{{ completed.applyId }}的申请</span>
                    <el-button type="success" size="mini" effect="dark" style="float:right" @click="completedDetail(completed)">已完成</el-button>
                    </div>
                    <!-- 申请内容 -->
                    <div>
                        <div>申请类型：{{completed.type }}</div>
                        <div>变动产品：{{completed.pname}}</div>
                        <div>产品去向：{{completed.deposityOld}}————{{completed.deposityNew}}</div>
                        <div>申请时间：{{completed.applyTime }}</div>
                        <div>审核时间：{{completed.reviewTime }}</div>
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>

<!-- 已完成弹出框 -->
<el-dialog title="申请详情" :visible.sync="pendingFormVisible" width="30%">
<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" size="small" >
  <el-form-item label="产品名称">
    <el-input v-model="ruleForm.pname" disabled></el-input>
  </el-form-item>
  <el-form-item label="产品数量">
    <el-input v-model.number="ruleForm.quantity" type="number" min="0" disabled></el-input>
  </el-form-item>
  <el-form-item label="产品价格">
    <el-input v-model.number="ruleForm.price" disabled></el-input>
  </el-form-item>
  <el-form-item label="仓库">
    <el-select v-model="ruleForm.deposityNew" placeholder="请选择仓库名称" style="width:100%" disabled>
      <el-option v-for="item in ['仓库1','仓库2','仓库3']" :key="item" :label="item" :value="item"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="操作类型">
    <el-radio-group v-model="ruleForm.type" disabled>
      <el-radio label="出货单"></el-radio>
      <el-radio label="进货单"></el-radio>
    </el-radio-group>
  </el-form-item>
    <el-form-item label="审核人">
    <el-select v-model="ruleForm.reviewId" placeholder="请选择审核人" style="width:100%" disabled>
      <el-option v-for="item in ['111','222','333']" :key="item" :label="item" :value="item"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="申请备注" >
    <el-input type="textarea" v-model="ruleForm.applyRemark" disabled></el-input>
  </el-form-item>
</el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="pendingFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="reject">驳 回</el-button>
    </div>
</el-dialog>

<!-- 已完成弹出框 -->
<el-dialog title="申请详情" :visible.sync="completedFormVisible" width="30%">
<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" size="small" >
  <el-form-item label="产品名称">
    <el-input v-model="ruleForm.pname" disabled></el-input>
  </el-form-item>
  <el-form-item label="产品数量">
    <el-input v-model.number="ruleForm.quantity" type="number" min="0" disabled></el-input>
  </el-form-item>
  <el-form-item label="产品价格">
    <el-input v-model.number="ruleForm.price" disabled></el-input>
  </el-form-item>
  <el-form-item label="仓库">
    <el-select v-model="ruleForm.deposityNew" placeholder="请选择仓库名称" style="width:100%" disabled>
      <el-option v-for="item in ['仓库1','仓库2','仓库3']" :key="item" :label="item" :value="item"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="操作类型">
    <el-radio-group v-model="ruleForm.type" disabled>
      <el-radio label="出货单"></el-radio>
      <el-radio label="进货单"></el-radio>
    </el-radio-group>
  </el-form-item>
    <el-form-item label="审核人">
    <el-select v-model="ruleForm.reviewId" placeholder="请选择审核人" style="width:100%" disabled>
      <el-option v-for="item in ['111','222','333']" :key="item" :label="item" :value="item"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="申请备注" >
    <el-input type="textarea" v-model="ruleForm.applyRemark" disabled></el-input>
  </el-form-item>
</el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="completedFormVisible = false">取 消</el-button>
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
        pendings: [],
        completeds: [],
        completedFormVisible:false,
        pendingFormVisible:false,
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
   

       }
    },
    components: {

    },
    mounted () {
        this.loadpendings();
        this.loadcompleteds();
    },
    methods: { 
        loadpendings(){
            request.get('/recordIn/allPendingData').then(res =>{
            if(res.code==0)
            {
                this.pendings=res.data;
            }
        })
        },
        loadcompleteds(){
            request.get('/recordIn/allCompletedData').then(res =>{
            if(res.code==0)
            {
                this.completeds=res.data;
            }
        })
        },
        completedDetail(completed){//编辑
            console.log("当前卡片的数据：", completed);
            this.ruleForm=completed;
            this.completedFormVisible=true
        },
        pendingDetail(pending){//编辑
            console.log("当前卡片的数据：", pending);
            this.ruleForm=pending;
            this.pendingFormVisible=true
        },
        reject(){

        }

    }
 }
</script>

<style scoped>

 
</style>

