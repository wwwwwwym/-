<template>
 <div>
  
  <div style="margin-top:20px; margin-bottom: 30px; display:flex;">
<!-- 查询组件 -->
      <!-- <el-select style="margin-left: 10px;" v-model="value" filterable placeholder="请选择来源仓库">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select> -->
      <!-- <el-input v-model="deposity" placeholder="请输入来源仓库" style="width:20%; margin-left: 10px"></el-input> -->
      <el-select v-model="deposityIn" placeholder="请选择进货仓库" style="width:20%; margin-left: 10px">
        <el-option v-for="item in ['仓库1','仓库2','仓库3','仓库4','仓库5']" :key="item" :label="item" :value="item"></el-option>
      </el-select>
      <el-select v-model="deposityOut" placeholder="请选择出货仓库" style="width:20%; margin-left: 10px">
        <el-option v-for="item in ['仓库1','仓库2','仓库3','仓库4','仓库5']" :key="item" :label="item" :value="item"></el-option>
      </el-select>
      <el-select v-model="type" placeholder="请选择记录类型" style="width:20%; margin-left: 10px">
        <el-option v-for="item in ['出货单','进货单','调货单']" :key="item" :label="item" :value="item"></el-option>
      </el-select>
      
      <div class="block" style="margin-left: 10px">
        <el-date-picker
          v-model="applyTime"
          type="date"
          placeholder="选择日期"
          value-format="yyyy-MM-dd">
        </el-date-picker>
      </div>

      <el-input v-model="pname" placeholder="请输入产品名称" style="width:20%; margin-left: 10px"></el-input> 

      <el-button type="primary" style="margin-left: 40px" @click="load(1)"><i class="el-icon-search"></i> 查询</el-button>
      <el-button type="primary" style="margin-left: 10px" @click="reset"><i class="el-icon-refresh-right"></i> 重置</el-button>
  </div>


  <div style="margin-top: 20px;margin-bottom: 10px">
    <!-- <el-button type="primary" plain @click="handleAdd">新增</el-button>
    <el-button type="danger" plain @click="delBatch">批量删除</el-button> -->
    <el-button type="primary" plain @click="exportData">批量导出</el-button>
    <el-upload action="http://localhost:9000/recordIn/import" :headers="{token:user.token}" :on-success="handleImport" style="display:inline-block ;margin-left: 10px" :show-file-list="false">
      <el-button type="primary" plain>批量导入</el-button>
    </el-upload>
  </div>
  

<!-- 表单区域 -->
  <el-table :data="tableData" stripe border style="width: 100%" 
    :cell-style="{textAlign:'center'}" :header-cell-style="{textAlign:'center',backgroundColor:'aliceblue',color:'#666'}"
    @selection-change="handleSelectionChange">
    <!-- 多选 -->
    <el-table-column type="selection" width="55" align="center"> </el-table-column>
    <el-table-column prop="recordId" label="记录编号" width="90"> </el-table-column>
    <el-table-column prop="pname" label="产品名称" > </el-table-column>
    <el-table-column prop="type" label="记录类型" > </el-table-column>
    <el-table-column prop="deposityIn" label="进货仓库" width="90"> </el-table-column>
    <el-table-column prop="deposityOut" label="出货仓库" width="90"> </el-table-column>
    <el-table-column prop="quantity" label="数量" width="90"> </el-table-column>
    <el-table-column prop="price" label="价格" width="90"> </el-table-column>
    <el-table-column prop="state" label="当前状态" width="90"> </el-table-column>
    <el-table-column prop="applyId" label="申请人" width="90"> </el-table-column>
    <el-table-column prop="applyTime" label="申请时间" width="180" > </el-table-column>
    <el-table-column prop="reviewId" label="审核人" width="90"> </el-table-column>
    <el-table-column prop="reviewTime" label="审核时间" width="180"> </el-table-column>
    <!-- <el-table-column fixed="right" label="操作" >
      <template slot-scope="scope">
        <el-button  type="text" size="medium" @click="handleEdit(scope.row)">编辑</el-button>
        <el-button type="text" size="medium" @click="del(scope.row.userid)">删除</el-button>
      </template>
    </el-table-column> -->
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
        tableData: [],
        pageNum: 1,//当前页码
        pageSize: 10,//每页个数
        pname: '',
        recordId: '',
        total: 0,
        record_ids: [],
        user:JSON.parse(localStorage.getItem('user') || '{}'),
        deposityIn: '',
        deposityOut: '',
        applyTime: '',
        type: '',

      }
    },
    methods: {
      reset(){
        this.pname=''
        this.deposityIn=''
        this.deposityOut=''
        this.applyTime=''
        this.type=''

        this.load(this.pageNum)
      },
      handleImport(res){
        if(res.code===0){
          this.$message.success("导入成功");
          this.load(1)
        }else{
          this.$message.error(res.msg);
        }
      },
      exportData(){//批量导出
      console.log(this.record_ids.length)
      if(!this.record_ids.length){//没有选择行的时候，全部导出，或者根据搜索条件查询到的数据全部导出
        window.open('http://localhost:9000/recordIn/export?token='+ this.user.token + "&pname="+this.pname + "&deposity="+this.deposity )
      }else{
        let record_idsStr = this.record_ids.join(',')
        window.open('http://localhost:9000/recordIn/export?token='+ this.user.token + "&record_ids="+record_idsStr)//传了选中的行
      }
      },
      handleSelectionChange(rows){   
        console.log("原始 rows 数据:", rows);
        this.record_ids=rows.map(v=>v.recordId)//把对象数组变成数字数组,v.recordId与前端获取的row的字段名一致，刚开始写recoord_id一直不成功
        console.log(this.record_ids[0])
      },
      load(pageNum){//分页查询
      if(pageNum)
      {
        this.pageNum=pageNum
      }
        request.get('/recordIn/selectByPage', {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            pname: this.pname,
            deposityIn:this.deposityIn,
            deposityOut:this.deposityOut,
            type:this.type,
            applyTime:this.applyTime
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
