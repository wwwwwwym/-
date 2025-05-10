<template>
 <div style="display:flex ;flex-direction:column">

    <div style="margin-top:20px">
        <div style="margin-bottom:20px ;font-size:20px;text-align:left">待审核申请（{{ pendings.length }}）</div>
        <el-row :gutter="20">
            <el-col v-for="pending in pendings" :key="pending.recordId" :xs="24" :sm="12" :md="8" :lg="6">
            
            <el-card class="pending-card" shadow="hover">
                <!-- 卡片头部 -->
                <div slot="header" class="card-header">
                <span>{{ pending.applyId }}的申请</span>
                <el-tag 
                    type="warning" 
                    size="mini"
                    effect="dark"
                >待审核</el-tag>
                </div>
                <!-- 申请内容 -->
                <div>
                    <div>申请类型：{{ pending.applyType }}</div>
                    <div>变动产品：{{pending.pname}}</div>
                    <div>产品去向：{{pending.deposityOld}}————{{pending.deposityNew}}</div>
                    <div>申请时间：{{ pending.applyTime }}</div>
   
                </div>
            </el-card>

        </el-col>
        </el-row>

    </div>

    <div style="margin-top:20px">
        <div style="margin-bottom:20px;font-size:20px;text-align:left">已完成申请（{{ completeds.length }}）</div>
        <el-row :gutter="20">
            <el-col :span="6">
                <div>
                     <el-card >
                        111

                    </el-card>
                </div>
            </el-col>
        </el-row>
    </div>
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

       }
    },
    components: {

    },
    methods: { 
        load(){
            request.get('/recordIn/allInData').then(res =>{
            if(res.code==0)
            {
                console.log(res.data)
                this.$set(this.$data,'tabledata',res.data); 
            }
   
        })
        },

    }
 }
</script>

<style scoped>

 
</style>
