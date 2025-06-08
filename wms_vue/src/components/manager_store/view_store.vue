<template>
 <div >
   <el-row :gutter="10">
      <el-col :span="12">
         <el-card>
            <div id="bar" style="width: 100%;height: 400px"></div>
         </el-card>
      </el-col>

      <el-col :span="12">
         <el-card>
            <div>
               <div id="pie" style="width: 100%;height: 400px"></div>
            </div>
         </el-card>
      </el-col>
   </el-row>

 </div>
</template>

<script>
import * as echarts from 'echarts';

const baroption = {
  title: {text: '库存数量及金额统计', left:'center'},
  legend: {left:'left',data: ['数量', '金额']},
  tooltip: {
    trigger: 'axis',
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: [
    {
      type: 'category',
      data: [],
      axisTick: {
        alignWithLabel: true
      }
    }
  ],
  yAxis: [
    {
      type: 'value'
    }
  ],
  series: [
    {
      name: '数量',
      type: 'bar',
      barWidth: '30%',
      data: []
    },
    {
      name: '金额',
      type: 'bar',
      barWidth: '30%',
      data: []
    }
  ]
};


const pieoption = {
  title: {
    text: '产品库存量占比',
    left: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: '产品库存量',
      type: 'pie',
      radius: '50%',
      data: [],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
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
      let barDom = document.getElementById('bar');
      let barChart = echarts.init(barDom);
      barChart.setOption(baroption);
      let pieDom = document.getElementById('pie');
      let pieChart = echarts.init(pieDom);
      pieChart.setOption(pieoption);
      
      this.$request.get('/storecharts').then(res =>{
        baroption.xAxis[0].data = res.data?.bar?.map(v => v.name)||[]
        baroption.series[0].data = res.data?.bar?.map(v => v.value)||[]
        baroption.series[1].data = res.data?.bar?.map(v => v.price)||[]
        barChart.setOption(baroption);

        pieoption.series[0].data = res.data?.bar||[]
        pieChart.setOption(pieoption);
      })

    }
 }
</script>

<style scoped>

 
</style>