<template>
 <div >
   <el-row :gutter="10">
      <el-col :span="12">
         <el-card>
            <div id="line" style="width: 100%;height: 400px">

            </div>
         </el-card>
      </el-col>

      <el-col :span="12">
         <el-card>
            <div>
               
            </div>
         </el-card>
      </el-col>
   </el-row>

 </div>
</template>

<script>
import * as echarts from 'echarts';

const lineoption = {
   title:{
      text: '货流统计'
   },
   tooltip:{
      trigger:'axis'
   },
  xAxis: {
    type: 'category',
    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: [150, 230, 224, 218, 135, 147, 260],
      type: 'line'
    }
  ]
};

 export default {
    name:'app',
    data () {
       return {

       }
    },
    components: {

    },
    methods: { 

    },
    mounted () {//等待页面全部元素加载完再初始化，不然 会报错
      let lineDom = document.getElementById('line');
      let lineChart = echarts.init(lineDom);
      lineChart.setOption(lineoption);
    }
 }
</script>

<style scoped>

 
</style>
