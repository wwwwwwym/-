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
      <el-input v-model="deposity" placeholder="请输入现存仓库名称" style="width:20%; margin-left: 10px"></el-input>
      
      <div class="block" style="margin-left: 10px">
        <el-date-picker
          v-model="value1"
          type="date"
          placeholder="选择日期">
        </el-date-picker>
      </div>

      <el-input v-model="pname" placeholder="请输入产品名称" style="width:20%; margin-left: 10px"></el-input> 

      <el-button type="primary" style="margin-left: 40px" @click="load(1)"><i class="el-icon-search"></i> 查询</el-button>
      <el-button type="primary" style="margin-left: 10px" @click="reset"><i class="el-icon-refresh-right"></i> 重置</el-button>
  </div>


  <div style="margin-top: 20px;margin-bottom: 10px">
    <el-button type="primary" plain @click="handleAdd">新增</el-button>
    <el-button type="danger" plain @click="delBatch" v-if="user.roleid!=1">批量删除</el-button>
    <el-button type="primary" plain @click="exportData">批量导出</el-button>
    <el-upload action="http://localhost:9000/stock/import" :headers="{token:user.token}" :on-success="handleImport" style="display:inline-block ;margin-left: 10px" :show-file-list="false">
      <el-button type="primary" plain>批量导入</el-button>
    </el-upload>
  </div>
  

<!-- 表单区域 -->
  <el-table :data="tableData" stripe border style="width: 100%" 
    :cell-style="{textAlign:'center'}" :header-cell-style="{textAlign:'center',backgroundColor:'aliceblue',color:'#666'}"
    @selection-change="handleSelectionChange">
    <!-- 多选 -->
    <el-table-column type="selection" width="60" align="center"> </el-table-column>
    <el-table-column prop="stockid" label="记录编号" width="90"> </el-table-column>
    <el-table-column prop="pname" label="产品名称" > </el-table-column>
    <el-table-column prop="code" label="产品条码" > </el-table-column>
    <el-table-column prop="deposity" label="现存仓库" width="90"> </el-table-column>
    <el-table-column prop="oldDeposity" label="原仓库" width="90"> </el-table-column>
    <el-table-column prop="quantity" label="数量" width="90"> </el-table-column>
    <el-table-column prop="price" label="价格" width="90"> </el-table-column>
    <el-table-column prop="stocktime" label="入库时间" width="180" :formatter="formatTime"> </el-table-column>
    <el-table-column fixed="right" label="操作" width="">
      <template slot-scope="scope">
        <el-button  type="text" size="medium" @click="handleEdit(scope.row)">编辑</el-button>
        <el-button type="text" size="medium" @click="del(scope.row.stockid)" v-if="user.roleid!=1">删除</el-button>
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
<el-dialog title="新增产品" :visible.sync="addFormVisible" width="30%">
    <el-form :model="form" :rules="rules" label-width="80px" style="padding-right:20px" ref="form" >
        <el-form-item label="现存仓库" prop="deposity">
            <el-input placeholder="请输入现存仓库" v-model="form.deposity" ></el-input>
        </el-form-item>
        <el-form-item label="产品名称" prop="pname">
            <el-input placeholder="请输入产品名" v-model="form.pname" ></el-input>
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
            <el-input placeholder="请输入产品库存数量" v-model.number="form.quantity" type="number" min="0"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price">
            <el-input placeholder="请输入产品单价" v-model.number="form.price" ></el-input>
        </el-form-item>
        <el-form-item label="产品条码" prop="code">
            <el-input placeholder="请输入产品条码" v-model="form.code" ></el-input>
        </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="addFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="add">确 定</el-button>
    </div>
</el-dialog>

<!-- 编辑弹出框 -->
<el-dialog title="编辑信息" :visible.sync="editFormVisible" width="30%">
    <el-form :model="form" :rules="rules" label-width="80px" style="padding-right:20px" ref="form" >
        <el-form-item label="现存仓库" prop="deposity">
            <el-input placeholder="请输入现存仓库" v-model="form.deposity" ></el-input>
        </el-form-item>
        <el-form-item label="产品名称" prop="pname">
            <el-input placeholder="请输入产品名" v-model="form.pname" ></el-input>
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
            <el-input placeholder="请输入产品库存数量" v-model="form.quantity"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price">
            <el-input placeholder="请输入产品单价" v-model="form.price" ></el-input>
        </el-form-item>
        <el-form-item label="产品条码" prop="code">
            <el-input placeholder="请输入产品条码" v-model="form.code" ></el-input>
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
import dayjs from 'dayjs'
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
        pname: '',
        stockid: '',
        total: 0,
        deposity: '',
        stockids: [],
        user:JSON.parse(localStorage.getItem('user') || '{}'),
        form:{
          deposity: '',
          pname: '',
          quantity: '',
          price: '',
          code: '',
        },
        editFormVisible:false,
        addFormVisible:false,


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
          deposity: [
            { required: true, message: '请输入仓库', trigger: 'blur' }
          ],
        },





      }
    },
    methods: {
        delBatch(){
        if(!this.stockids.length)
        {
          this.$message.warning("请选择要删除的数据");
          return
        }
        this.$confirm('您确认批量删除这些数据吗?', '确认删除', {type:"warning"}).then(() => {
          this.$request.delete('/stock/delete/batch',{
            data:this.stockids
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
        del(stockid){
        this.$confirm('您确认删除吗?', '确认删除', {type:"warning"}).then(() => {
          this.$request.delete('/stock/delete/'+ stockid).then(res =>{
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
        this.pname=''
        this.deposity=''
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
      if(!this.stockids.length){//没有选择行的时候，全部导出，或者根据搜索条件查询到的数据全部导出
        window.open('http://localhost:9000/stock/export?token='+ this.user.token + "&pname="+this.pname + "&deposity="+this.deposity )
      }else{
        let stockidsStr = this.stockids.join(',')
        window.open('http://localhost:9000/stock/export?token='+ this.user.token + "&stockids="+stockidsStr)//传了选中的行
      }
      },
      handleSelectionChange(rows){   
        this.stockids=rows.map(v=>v.stockid)//把对象数组变成数字数组,v.recordId与前端获取的row的字段名一致，刚开始写recoord_id一直不成功
      },
      load(pageNum){//分页查询
      if(pageNum)
      {
        this.pageNum=pageNum
      }
        request.get('/stock/selectByPage', {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            pname: this.pname,
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
                    this.$request.put('stock/update',this.form).then(res => {
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
                    this.$request.post('stock/add',this.form).then(res => {
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
