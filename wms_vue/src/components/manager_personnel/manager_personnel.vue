<template>
 <div>
  
  <div style="margin-top:20px; margin-bottom: 30px; display:flex;">
      <!-- <div class='title' style="margin-right: 50px;font-size:25px;">入库信息查询</div> -->
      <!-- <el-select style="margin-left: 10px;" v-model="value" filterable placeholder="请选择进货仓库">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select> -->
<!-- 查询组件 -->
      <el-select style="margin-left: 10px;" v-model="value" filterable placeholder="请选择出货仓库">
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
  

<!-- 表单区域 -->
  <el-table :data="tableData" stripe border style="width: 100%" 
    :cell-style="{textAlign:'center'}" :header-cell-style="{textAlign:'center'}">
    <el-table-column prop="userid" label="用户id"> </el-table-column>
    <el-table-column prop="username" label="用户名称" > </el-table-column>
    <el-table-column prop="roleid" label="角色id" > </el-table-column>
    <el-table-column prop="time" label="入职时间" > </el-table-column>
    <el-table-column fixed="right" label="操作" width="180" >
      <template v-slot="scope">
        <el-button @click="handleClick(scope.row)" type="text" size="medium">查看</el-button>
        <el-button type="text" size="medium">编辑</el-button>
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
          label: '黄金糕'
        }, {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        }, {
          value: '选项4',
          label: '龙须面'
        }, {
          value: '选项5',
          label: '北京烤鸭'
        }],
        value: ''
      }
    },
    methods: {
      load(){
        request.get('/user/allUserData').then(res => {
          if(res.code == 0){
            this.tableData = res.data; 
          }
          else{
            this.$notify.error(res.msg);
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
