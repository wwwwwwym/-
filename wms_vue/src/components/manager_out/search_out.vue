<template>
 <div>
  
  <div style="margin-top:20px; margin-bottom: 30px; display:flex;">
<!-- 查询组件 -->
      <el-select style="margin-left: 10px;" v-model="value" filterable placeholder="请选择目的仓库">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      
      <div class="block" style="margin-left: 10px">
        <el-date-picker
          v-model="value1"
          type="date"
          placeholder="选择日期">
        </el-date-picker>
      </div>

      <el-input v-model="input" placeholder="请输入产品名称" style="width:20%; margin-left: 10px"></el-input> 

      <el-button type="primary" style="margin-left: 40px"><i class="el-icon-search"></i> 查询</el-button>
  </div>
  <div style="margin-top: 20px;margin-bottom: 10px">
    <el-button type="primary" plain>新增</el-button>
    <el-button type="danger" plain>批量删除</el-button>
  </div>
  

<!-- 表单区域 -->
  <el-table :data="tableData" stripe border style="width: 100%" 
    :cell-style="{textAlign:'center'}" :header-cell-style="{textAlign:'center',backgroundColor:'aliceblue',color:'#666'}">
    <!-- 多选 -->
    <el-table-column type="selection" width="55" align="center"> </el-table-column>
    <el-table-column prop="recordId" label="记录编号" width="90"> </el-table-column>
    <el-table-column prop="pname" label="产品名称" width="180"> </el-table-column>
    <el-table-column prop="deposityId" label="仓库编号" width="90"> </el-table-column>
    <el-table-column prop="quantity" label="数量" width="90"> </el-table-column>
    <el-table-column prop="price" label="价格" width="90"> </el-table-column>
    <el-table-column prop="state" label="当前状态" width="90"> </el-table-column>
    <el-table-column prop="applyId" label="申请人" width="90"> </el-table-column>
    <el-table-column prop="applyTime" label="申请时间" width="180" :formatter="formatTime"> </el-table-column>
    <el-table-column prop="reviewId" label="审核人" width="90"> </el-table-column>
    <el-table-column prop="reviewTime" label="审核时间" width="180" :formatter="formatTime"> </el-table-column>
    <el-table-column fixed="right" label="操作" >
      <template slot-scope="scope">
        <el-button @click="handleClick(scope.row)" type="text" size="medium">编辑</el-button>
        <el-button type="text" size="medium">删除</el-button>
      </template>
    </el-table-column>
  </el-table>


<!-- 分页组件 -->
  <div class="block" style="margin-top: 30px; text-align:right">
  <el-pagination
    layout="prev, pager, next"
    :total="1000">
  </el-pagination>
  </div>

 </div>
</template>

<script>
import request from '@/request/request'
import dayjs from 'dayjs'
 export default {
  created(){
    this.load();
  },
    data() {
      return {
        input:'',
        value1: '',
        tableData: [],

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
        value: ''
      }
    },
    methods: {
      load(){
        request.get('/recordOut/allRecordOutData').then(res => {
          if(res.code == 0){
            this.tableData = res.data; 
          }
          else{
            this.$notify.error(res.msg);
          }
    
        })
      },
      formatTime(row, column, cellValue) {
      if (!cellValue) return '暂无时间'; // 处理空值
      return dayjs(cellValue).format('YYYY-MM-DD');
    },



    }
  }
</script>

<style scoped>
.title{
  text-align:left;
}
 
</style>
