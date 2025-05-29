<template>
 <div >
   <el-row :gutter="10">
      <el-col :span="12">
         <el-card>
            <div id="line" style="width: 100%;height: 400px"></div>
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
  title: {
    text: 'Stacked Line',
    left:'center'
  },
  tooltip: {
    trigger: 'axis'
  },
//   legend: {
//    //  data: ['1号仓库', '2号仓库', '3号仓库', '4号仓库', '5号仓库'],
//     left: 'left',
//     orient: 'vertical'
//   },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  toolbox: {
    feature: {
      saveAsImage: {}
    }
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
   //  data: ["2018-01-01", "2018-01-02", "2018-01-03", "2018-01-04", "2018-01-05", "2018-01-06", "2018-01-07"]
   data:[]
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '1号仓库',
      type: 'line',
      stack: 'Total',
      data: [120, 132, 101, 134, 90, 230, 210]
    },
    {
      name: '2号仓库',
      type: 'line',
      stack: 'Total',
      data: [220, 182, 191, 234, 290, 330, 310]
    },
    {
      name: '3号仓库',
      type: 'line',
      stack: 'Total',
      data: [150, 232, 201, 154, 190, 330, 410]
    },
    {
      name: '4号仓库',
      type: 'line',
      stack: 'Total',
      data: [320, 332, 301, 334, 390, 330, 320]
    },
    {
      name: '5号仓库',
      type: 'line',
      stack: 'Total',
      data: [820, 932, 901, 934, 1290, 1330, 1320]
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

      this.$request.get('/recordIn/charts').then(res =>{
         lineoption.xAxis.data = res.data?.map(v=>v.date) || [];
         console.log(res.data)
         // option.series[0].data = res.data?.map(v=>v.value) || [];

         lineChart.setOption(lineoption);
      })
    }
 }
</script>

<style scoped>

 
</style>
