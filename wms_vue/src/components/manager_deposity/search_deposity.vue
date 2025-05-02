<template>
 <div>
  
  <div style="margin-top:20px; margin-bottom: 30px; display:flex;">
      <el-input v-model="dname" placeholder="请输入仓库名称" style="width:20%; margin-left: 10px"></el-input>
      <el-input v-model="address" placeholder="请输入仓库地址" style="width:20%; margin-left: 10px"></el-input>
      <el-button type="primary" style="margin-left: 40px" @click="load(1)"><i class="el-icon-search"></i> 查询</el-button>
      <el-button type="primary" style="margin-left: 10px" @click="reset"><i class="el-icon-refresh-right"></i> 重置</el-button>
  </div>


  <div style="margin-top: 20px;margin-bottom: 10px">
    <el-button type="primary" plain @click="handleAdd">新增</el-button>
    <el-button type="danger" plain @click="delBatch" v-if="user.roleid!=1">批量删除</el-button>
    <el-button type="primary" plain @click="exportData">批量导出</el-button>
    <el-upload action="http://localhost:9000/deposity/import" :headers="{token:user.token}" :on-success="handleImport" style="display:inline-block ;margin-left: 10px" :show-file-list="false">
      <el-button type="primary" plain>批量导入</el-button>
    </el-upload>
  </div>
  

<!-- 表单区域 -->
  <el-table :data="tableData" stripe border style="width: 100%" 
    :cell-style="{textAlign:'center'}" :header-cell-style="{textAlign:'center',backgroundColor:'aliceblue',color:'#666'}"
    @selection-change="handleSelectionChange">
    <!-- 多选 -->
    <el-table-column type="selection" width="60" align="center"> </el-table-column>
    <el-table-column prop="id" label="记录编号" > </el-table-column>
    <el-table-column prop="dname" label="仓库名称" > </el-table-column>
    <el-table-column prop="address" label="仓库地址" > </el-table-column>
    <el-table-column prop="introduce" label="仓库简介" > </el-table-column>
    <el-table-column fixed="right" label="操作" width="180">
      <template slot-scope="scope">
         <el-button  type="text" size="medium" @click="handleEdit(scope.row)">编辑</el-button>
        <el-button type="text" size="medium" @click="del(scope.row.id)">删除</el-button>
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


  <!-- 新增弹出框 -->
<el-dialog title="新增仓库" :visible.sync="addFormVisible" width="30%">
    <el-form :model="form" :rules="rules" label-width="80px" style="padding:20px" ref="form" >
        <el-form-item label="仓库名称" prop="dname">
            <el-input placeholder="请输入仓库名称" v-model="form.dname" ></el-input>
        </el-form-item>
        <el-form-item label="仓库地址" prop="address">
            <el-input placeholder="请输入仓库地址" v-model="form.address" ></el-input>
        </el-form-item>
        <el-form-item label="仓库简介" prop="introduce">
            <el-input placeholder="请输入仓库简介" v-model.number="form.introduce"></el-input>
        </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="addFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="add">确 定</el-button>
    </div>
</el-dialog>

<!-- 编辑弹出框 -->
<el-dialog title="编辑信息" :visible.sync="editFormVisible" width="30%">
    <el-form :model="form" :rules="rules" label-width="80px" style="padding:20px" ref="form" >
        <el-form-item label="仓库名称" prop="dname">
            <el-input placeholder="请输入仓库名称" v-model="form.dname" ></el-input>
        </el-form-item>
        <el-form-item label="仓库地址" prop="address">
            <el-input placeholder="请输入仓库地址" v-model="form.address" ></el-input>
        </el-form-item>
        <el-form-item label="仓库简介" prop="introduce">
            <el-input placeholder="请输入仓库简介" v-model.number="form.introduce"></el-input>
        </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="editFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
    </div>
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
        pageSize: 5,//每页个数
        dname: '',
        id: '',
        total: 0,
        ids: [],
        user:JSON.parse(localStorage.getItem('user') || '{}'),
        address: '',
        introduce: '', 
        form:{
          dname: '',
          address: '',
          introduce: '',
        },
        addFormVisible:false,
        editFormVisible:false,
  

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
          this.$request.delete('/deposity/delete/batch',{
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
          this.$request.delete('/deposity/delete/'+ id).then(res =>{
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
        this.dname=''
        this.address=''
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
      if(!this.ids.length){//没有选择行的时候，全部导出，或者根据搜索条件查询到的数据全部导出
        window.open('http://localhost:9000/deposity/export?token='+ this.user.token + "&dname="+this.dname + "&address="+this.address )
      }else{
        let idsStr = this.ids.join(',')
        window.open('http://localhost:9000/deposity/export?token='+ this.user.token + "&ids="+idsStr)//传了选中的行
      }
      },
      handleSelectionChange(rows){   
        this.ids=rows.map(v=>v.id)//把对象数组变成数字数组,v.recordId与前端获取的row的字段名一致，刚开始写recoord_id一直不成功
      },
      load(pageNum){//分页查询
      if(pageNum)
      {
        this.pageNum=pageNum
      }
        request.get('/deposity/selectByPage', {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            dname: this.dname,
            address: this.address,
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
    handleEdit(row){//编辑
      this.form=JSON.parse(JSON.stringify(row))//给form对象赋值，深拷贝
      this.editFormVisible=true
      },
      handleAdd(){//新增
        this.form={}
        this.addFormVisible=true

      },

    update(){
         this.$refs.form.validate((valid) => {
                if(valid){
                    this.$request.put('deposity/update',this.form).then(res => {
                      if(res.code===0)
                      {
                        this.$message.success("保存成功");
                        this.editFormVisible=false
                        this.load(1)
                      }else{
                        this.$message.error(res.msg);
                      }
                    })
                }
            })
      },
      add(){
         this.$refs.form.validate((valid) => {
                if(valid){
                    this.$request.post('deposity/add',this.form).then(res => {
                      if(res.code===0)
                      {
                        this.$message.success("保存成功");
                        this.addFormVisible=false
                        this.load(1)
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
.title{
  text-align:left;
}
 
</style>
