<template>
 <div>
  
  <div style="margin-top:20px; margin-bottom: 30px; display:flex;">
      <el-input v-model="operation" placeholder="请输入操作名称" style="width:20%; margin-left: 10px"></el-input>
      <el-select v-model="type" placeholder="请选择操作类型" style="width:20%; margin-left: 10px">
        <el-option v-for="item in ['新增','修改','删除','注册','登录','批量删除']" :key="item" :label="item" :value="item"></el-option>
      </el-select>
      <el-input v-model="OptUser" placeholder="请输入操作人" style="width:20%; margin-left: 10px"></el-input>
      <el-button type="primary" style="margin-left: 40px" @click="load(1)"><i class="el-icon-search"></i> 查询</el-button>
      <el-button type="primary" style="margin-left: 10px" @click="reset"><i class="el-icon-refresh-right"></i> 重置</el-button>
  </div>


  <div style="margin-top: 20px;margin-bottom: 10px">
    <el-button type="danger" plain @click="delBatch" v-if="user.roleid!=1">批量删除</el-button>
  </div>
  

<!-- 表单区域 -->
  <el-table :data="tableData" stripe border style="width: 100%" 
    :cell-style="{textAlign:'center'}" :header-cell-style="{textAlign:'center',backgroundColor:'aliceblue',color:'#666'}"
    @selection-change="handleSelectionChange">
    <!-- 多选 -->
    <el-table-column type="selection" width="60" align="center"> </el-table-column>
    <el-table-column prop="id" label="记录编号" > </el-table-column>
    <el-table-column prop="operation" label="操作模块" > </el-table-column>
    <el-table-column prop="type" label="操作类型" > </el-table-column>
    <el-table-column prop="user" label="操作人" > </el-table-column>
    <el-table-column prop="time" label="操作时间" > </el-table-column>
    <el-table-column fixed="right" label="操作" width="180">
      <template slot-scope="scope">
        <el-button type="text" size="medium" @click="del(scope.row.id)" v-if="user.roleid!=1">删除</el-button>
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

 export default {
  created(){
    this.load();
  },
    data() {
      return {
        value1: '',
        tableData: [],
        pageNum: 1,//当前页码
        pageSize: 5,//每页个数
        operation: '',
        id: '',
        total: 0,
        ids: [],
        user:JSON.parse(localStorage.getItem('user') || '{}'),
        type: '',
        OptUser: '', 

      }
    },
    methods: {
        delBatch(){
        if(!this.ids.length)
        {
          this.$message.warning("请选择要删除的数据");
          return
        }
        this.$confirm('您确认批量删除这些数据吗?', '确认删除', {type:"warning"}).then(() => {
          this.$request.delete('/logs/delete/batch',{
            data:this.ids
          }).then(res =>{
            if(res.code===0)
            {
              this.$message.success("批量删除成功");
              this.load(1)
            }else{
              this.$message.error(res.msg);
            }
          })
        }).catch(() => {})
      },
        del(id){
        this.$confirm('您确认删除吗?', '确认删除', {type:"warning"}).then(() => {
          this.$request.delete('/logs/delete/'+ id).then(res =>{
            if(res.code===0)
            {
              this.$message.success("删除成功");
              this.load(1)
            }else{
              this.$message.error(res.msg);
            }
          })
        }).catch(() => {})
      },
      reset(){
        this.operation=''
        this.type=''
        this.OptUser=''
        this.load(this.pageNum)
      },
      handleSelectionChange(rows){   
        this.ids=rows.map(v=>v.id)//把对象数组变成数字数组,v.recordId与前端获取的row的字段名一致，刚开始写recoord_id一直不成功
      },
      load(pageNum){//分页查询
      if(pageNum)
      {
        this.pageNum=pageNum
      }
        request.get('/logs/selectByPage', {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            operation: this.operation,
            type: this.type,
            user: this.OptUser
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
    handleCurrentChange(pageNum){
      this.pageNum=pageNum
      this.load(pageNum)
    },

    }
  }
</script>

<style scoped>
.title{
  text-align:left;
}
 
</style>
