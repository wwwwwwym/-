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
      <el-select v-model="form.deposityNew" placeholder="请选择进货仓库" style="width:20%; margin-left: 10px">
        <el-option v-for="item in ['1号仓库','2号仓库','3号仓库','4号仓库','5号仓库']" :key="item" :label="item" :value="item"></el-option>
      </el-select>
      <el-select v-model="form.deposityOld" placeholder="请选择出货仓库" style="width:20%; margin-left: 10px">
        <el-option v-for="item in ['1号仓库','2号仓库','3号仓库','4号仓库','5号仓库']" :key="item" :label="item" :value="item"></el-option>
      </el-select>
      <el-select v-model="form.type" placeholder="请选择记录类型" style="width:20%; margin-left: 10px">
        <el-option v-for="item in ['出货单','进货单','调货单']" :key="item" :label="item" :value="item"></el-option>
      </el-select>
      
      <div class="block" style="margin-left: 10px">
        <el-date-picker
          v-model="form.applyTime"
          type="date"
          placeholder="选择日期"
          value-format="yyyy-MM-dd">
        </el-date-picker>
      </div>

      <el-input v-model="form.pname" placeholder="请输入产品名称" style="width:20%; margin-left: 10px"></el-input> 

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
    <el-table-column prop="deposityNew" label="进货仓库" width="90"> </el-table-column>
    <el-table-column prop="deposityOld" label="出货仓库" width="90"> </el-table-column>
    <el-table-column prop="state" label="当前状态" width="90"> </el-table-column>
    <el-table-column prop="applyId" label="申请人" width="90"> </el-table-column>
    <el-table-column prop="applyTime" label="申请时间" width="180" > </el-table-column>
    <el-table-column prop="reviewId" label="审核人" width="90"> </el-table-column>
    <el-table-column prop="reviewTime" label="审核时间" width="180"> </el-table-column>
    <el-table-column fixed="right" label="操作" >
      <template slot-scope="scope">
        <el-button  type="text" size="medium" @click="handleDetail(scope.row)">查看详情</el-button>
        <!-- <el-button type="text" size="medium" @click="del(scope.row.userid)">删除</el-button> -->
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

<!-- 详情弹出框 -->
<el-dialog title="详细信息" :visible.sync="detailFormVisible" width="40%">
    <el-form :model="form" :rules="rules" label-width="80px" style="padding-right:20px" ref="form" >
      <div style="margin:25px ; text-align:center;justify-content:center;display:flex" >
          <img v-if="form.picture" :src="form.picture" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </div>
        <el-form-item label="记录编号" >
            <el-input  v-model="form.recordId" disabled></el-input>
        </el-form-item>
        <el-form-item label="产品名称" >
            <el-input v-model="form.pname" disabled></el-input>
        </el-form-item>
        <el-form-item label="记录类型" >
            <el-input v-model="form.type" disabled></el-input>
        </el-form-item>
        <el-form-item label="出货仓库" >
            <el-input v-model="form.deposityOld" disabled></el-input>
        </el-form-item>
        <el-form-item label="进货仓库" >
            <el-input v-model="form.deposityNew" disabled></el-input>
        </el-form-item>
        <el-form-item label="数量" >
            <el-input v-model="form.quantity" disabled></el-input>
        </el-form-item>
        <el-form-item label="价格" >
            <el-input v-model="form.price" disabled></el-input>
        </el-form-item>
        <el-form-item label="状态" >
            <el-input v-model="form.state" disabled></el-input>
        </el-form-item>
        <el-form-item label="申请人" >
            <el-input v-model="form.applyId" disabled></el-input>
        </el-form-item>
        <el-form-item label="申请时间" >
            <el-input v-model="form.applyTime" disabled></el-input>
        </el-form-item>
        <el-form-item label="申请备注" >
            <el-input v-model="form.applyRemark" disabled></el-input>
        </el-form-item>
        <el-form-item label="审核人" >
            <el-input v-model="form.reviewId" disabled></el-input>
        </el-form-item>
        <el-form-item label="审核时间" >
            <el-input v-model="form.reviewTime" disabled></el-input>
        </el-form-item>
        <el-form-item label="审核意见" >
            <el-input v-model="form.reviewRemark" disabled></el-input>
        </el-form-item>
    </el-form>
    <!-- <div slot="footer" class="dialog-footer">
        <el-button @click="editFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
    </div> -->
</el-dialog>







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
        // pname: '',
        // recordId: '',
        total: 0,
        record_ids: [],
        user:JSON.parse(localStorage.getItem('user') || '{}'),
        // deposityNew: '',
        // deposityOld: '',
        // applyTime: '',
        // type: '',
        detailFormVisible: false,
        form:{
          recordId: '',
          pname: '',
          type: '',
          deposityNew: '',
          deposityOld: '',
          quantity: '',
          price: '',
          state: '',
          applyId: '',
          applyTime: '',
          applyRemark: '',
          reviewId: '',
          reviewTime: '',
          reviewRemark: '',

        },

      }
    },
    methods: {
      handleDetail(row){
        this.form=JSON.parse(JSON.stringify(row))//给form对象赋值，深拷贝
        this.detailFormVisible=true
      },
      handleAvatarSuccess(response){
        this.form.picture = response.data
        console.log(response.data)
      },
      reset(){
        this.form.pname=''
        this.form.deposityNew=''
        this.form.deposityOld=''
        this.form.applyTime=''
        this.form.type=''
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
            pname: this.form.pname,
            deposityNew:this.form.deposityNew,
            deposityOld:this.form.deposityOld,
            type:this.form.type,
            applyTime:this.form.applyTime
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
