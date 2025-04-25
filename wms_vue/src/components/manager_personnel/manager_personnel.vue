<template>
 <div>
  
  <div style="display:flex;">
<!-- 查询组件 -->

      <el-input v-model="deposity" placeholder="请输入负责仓库" style="width:20%; margin-left: 10px"></el-input>
      <el-input v-model="username" placeholder="请输入用户名" style="width:20%; margin-left: 10px"></el-input>
      <el-input v-model="userid" placeholder="请输入工号" style="width:20%; margin-left: 10px"></el-input>  

      <el-button type="primary" style="margin-left: 40px" @click="load(1)"><i class="el-icon-search"></i> 查询</el-button>
      <el-button type="primary" style="margin-left: 10px" @click="reset"><i class="el-icon-refresh-right"></i> 重置</el-button>
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
    <el-table-column prop="userid" label="用户id"> </el-table-column>
    <el-table-column prop="username" label="用户名称" > </el-table-column>
    <el-table-column prop="roleid" label="角色id" > </el-table-column>
    <el-table-column prop="deposity" label="负责仓库" > </el-table-column>
    <el-table-column prop="telephone" label="电话号码" > </el-table-column>
    <el-table-column prop="time" label="入职时间" :formatter="formatTime"> </el-table-column>
    <el-table-column fixed="right" label="操作" width="180" >
      <template v-slot="scope">
        <el-button @click="handleClick(scope.row)" type="text" size="medium">编辑</el-button>
        <el-button type="text" size="medium">删除</el-button>
      </template>
    </el-table-column>
  </el-table>


<!-- 分页组件 -->
  <div style="margin:10px">
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="pageSize"
      layout="total, prev, pager, next"
      :total="total">
    </el-pagination>
  </div>

 </div>
</template>

<script>
import request from '@/request/request'
import dayjs from 'dayjs'
export default {
  created(){
    this.load()
  },
    data() {
      return {
        value1: '',
        tableData: [],
        pageNum: 1,//当前页码
        pageSize: 5,//每页个数
        username: '',
        userid: '',
        total: 0,
        deposity: '',

      }
    },
    methods: {
      reset(){
        this.username=''
        this.userid=''
        this.deposity=''
        this.load()
      },
      load(pageNum){//分页查询
      if(pageNum)
      {
        this.pageNum=pageNum
      }
        request.get('/user/selectByPage', {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            userid: this.userid,
            username: this.username,
            deposity:this.deposity
          }
        }).then(res => {
          if(res.code == 0){
            this.tableData = res.data.records
            this.total=res.data.total
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
    handleCurrentChange(pageNum){
      this.pageNum=pageNum
      this.load()
    }

    },
  }
</script>

<style scoped>
.title{
  text-align:left;
}
 
</style>
