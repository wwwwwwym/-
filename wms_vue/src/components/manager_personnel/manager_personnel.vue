<template>
 <div>
  
  <div style="display:flex;">
<!-- 查询组件 -->

      <el-input v-model="username" placeholder="请输入用户名" style="width:20%; margin-left: 10px"></el-input>
      <el-input v-model="userid" placeholder="请输入工号" style="width:20%; margin-left: 10px"></el-input>  
      <el-button type="primary" style="margin-left: 40px" @click="load(1)"><i class="el-icon-search"></i> 查询</el-button>
      <el-button type="primary" style="margin-left: 10px" @click="reset"><i class="el-icon-refresh-right"></i> 重置</el-button>
  </div>
  
  <div style="margin-top: 20px;margin-bottom: 10px">
    <el-button type="primary" plain @click="handleAdd">新增</el-button>
    <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    <el-button type="primary" plain @click="exportData">批量导出</el-button>
    <el-upload action="http://localhost:9000/user/import" :headers="{token:user.token}" :on-success="handleImport" style="display:inline-block ;margin-left: 10px" :show-file-list="false">
      <el-button type="primary" plain>批量导入</el-button>
    </el-upload>
  </div>
  

<!-- 表单区域 -->
  <el-table :data="tableData" stripe border style="width: 100%" 
    :cell-style="{textAlign:'center'}" :header-cell-style="{textAlign:'center',backgroundColor:'aliceblue',color:'#666'}"
    @selection-change="handleSelectionChange">
    <!-- 多选 -->
    <el-table-column type="selection" width="55" align="center"> </el-table-column>
    <el-table-column prop="userid" label="用户id"> </el-table-column>
    <el-table-column prop="username" label="用户名称" > </el-table-column>
    <el-table-column prop="role" label="角色" > </el-table-column>
    <!-- <el-table-column prop="deposity" label="负责仓库" > </el-table-column> -->
    <el-table-column prop="telephone" label="电话号码" > </el-table-column>
    <el-table-column prop="time" label="入职时间" :formatter="formatTime"> </el-table-column>
    <el-table-column fixed="right" label="操作" width="180" >
      <template v-slot="scope">
        <el-button  type="text" size="medium" @click="handleEdit(scope.row)">编辑</el-button>
        <el-button type="text" size="medium" @click="del(scope.row.userid)">删除</el-button>
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
<el-dialog title="新增人员" :visible.sync="addFormVisible" width="30%">
    <el-form :model="form" :rules="rules" label-width="80px" style="padding-right:20px" ref="form" >
        <el-form-item label="工号" prop="userid">
            <el-input placeholder="请输入工号" v-model="form.userid" ></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="username">
            <el-input placeholder="请输入用户名" v-model="form.username" ></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="telephone">
            <el-input placeholder="请输入电话" v-model="form.telephone"></el-input>
        </el-form-item>
        <!-- <el-form-item label="角色" prop="role">
            <el-input placeholder="请输入角色" v-model="form.role" ></el-input>
        </el-form-item> -->
        <el-form-item label="用户角色" prop="role">
            <el-select v-model="form.role" placeholder="请选择角色" style="width:100%">
              <el-option v-for="item in ['普通员工','管理员','超级管理员']" :key="item" :label="item" :value="item"></el-option>
            </el-select>
        </el-form-item>
        
        <!-- <el-form-item label="负责仓库" prop="deposity">
            <el-input placeholder="请输入负责仓库" v-model="form.deposity" ></el-input>
        </el-form-item> -->
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="addFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="add">确 定</el-button>
    </div>
</el-dialog>

<!-- 编辑弹出框 -->
<el-dialog title="编辑信息" :visible.sync="editFormVisible" width="30%">
    <el-form :model="form" :rules="rules" label-width="80px" style="padding-right:20px" ref="form" >
        <el-form-item label="工号" >
            <el-input placeholder="请输入工号" v-model="form.userid" disabled></el-input>
        </el-form-item>
        <el-form-item label="用户名" >
            <el-input placeholder="请输入用户名" v-model="form.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="telephone">
            <el-input placeholder="请输入电话" v-model="form.telephone"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="role">
            <el-input placeholder="请输入角色" v-model="form.role" ></el-input>
        </el-form-item>
        <!-- <el-form-item label="负责仓库" prop="deposity">
            <el-input placeholder="请输入负责仓库" v-model="form.deposity" ></el-input>
        </el-form-item> -->
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
    this.load()
  },
    data() {

      var checkNull = (rule, value, callback) => {
        if (value === '' || value === null || value === undefined) {
          return callback(new Error('不能为空'));
        }
        return callback();
      };
      var checkId = (rule, value, callback) => {
        const num = Number(value);
        if (isNaN(num)) { // 检查是否为有效数字
          return callback(new Error('请输入数字值')); // 返回并终止
        }

        if (!Number.isInteger(num)) { // 检查是否为整数
          return callback(new Error('必须为整数')); // 返回并终止
        }

        if (value.length !== 4) { // 检查原始输入长度
          return callback(new Error('必须是4位工号')); // 返回并终止
        }
        return callback();
      };


      return {
        value1: '',
        tableData: [],
        pageNum: 1,//当前页码
        pageSize: 10,//每页个数
        username: '',
        userid: '',
        
        total: 0,
        deposity: '',
        editFormVisible:false,
        addFormVisible:false,
        userids: [],
        user:JSON.parse(localStorage.getItem('user') || '{}'),

        form:{
          userid: '',
          username: '',
          telephone: '',
          // deposityValue: '',
          // deposity: '',
        },
        deposityValue:'',
        rules: {
          username: [
            {required: true, validator: checkNull, trigger: 'blur' }
          ],
          role: [
            {required: true, validator: checkNull, trigger: 'blur' }
          ],
          // deposity: [
          //   {required: true, validator: checkNull, trigger: 'blur' }
          // ],
          userid: [
            {required: true, validator: checkId, trigger: 'blur' }
          ],
          telephone: [
            { required: true, message: '请输入电话号码', trigger: 'change' },
            { min: 11, max: 11, message: '长度为11个字符', trigger: 'change' }
          ],
        },

      }
    },
    methods: {
      handleImport(res){
        if(res.code===0){
          this.$message.success("导入成功");
          this.load(1)
        }else{
          this.$message.error(res.msg);
        }
      },
      exportData(){//批量导出
      if(!this.userids.length){//没有选择行的时候，全部导出，或者根据搜索条件查询到的数据全部导出
        window.open('http://localhost:9000/user/export?token='+ this.user.token 
        + "&username="+this.username + "&deposity="+this.deposity + "&userid="+this.userid)
      }else{
        let useridsStr = this.userids.join(',')
        window.open('http://localhost:9000/user/export?token='+ this.user.token + "&userids="+useridsStr)//传了选中的行
      }

      },
      delBatch(){
        if(!this.userids.length)
        {
          this.$message.warning("请选择要删除的数据");
          return
        }
        this.$confirm('您确认批量删除这些数据吗?', '确认删除', {type:"warning"}).then(() => {
          this.$request.delete('/user/delete/batch',{
            data:this.userids
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
      handleSelectionChange(rows){   
        this.userids=rows.map(v=>v.userid)//把对象数组变成数字数组
      },
      del(userid){
        this.$confirm('您确认删除吗?', '确认删除', {type:"warning"}).then(() => {
          this.$request.delete('/user/delete/'+ userid).then(res =>{
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
                    this.$request.put('user/update',this.form).then(res => {
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
                    this.$request.post('user/add',this.form).then(res => {
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
      reset(){
        this.username=''
        this.userid=''
        // this.deposity=''
        this.load(this.pageNum)
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
            role: this.user.role,
            // deposity:this.deposity
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

    },
  }
</script>

<style scoped>
.title{
  text-align:left;
}
 
</style>
