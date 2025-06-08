<template>
 <div >
   <el-row :gutter="10" class="el-row">
      <el-col :span="12">
         <el-card>
            <div id="line1" style="width: 100%;height: 400px"></div>
         </el-card>
      </el-col>

      <el-col :span="12">
         <el-card>
            <div>
               <div id="line2" style="width: 100%;height: 400px"></div>
            </div>
         </el-card>
      </el-col>
   </el-row>

   <el-row :gutter="10" class="el-row">
      <el-col :span="12">
         <el-card>
            <div id="pie1" style="width: 100%;height: 400px"></div>
         </el-card>
      </el-col>

      <el-col :span="12">
         <el-card>
            <div>
               <div id="pie2" style="width: 100%;height: 400px"></div>
            </div>
         </el-card>
      </el-col>
   </el-row>

 </div>
</template>

<script>
import * as echarts from 'echarts';

const lineoption1 = {
  title: {text: '出库金额统计', left:'center'},
  legend: {data: ['1号仓库', '2号仓库', '3号仓库', '4号仓库', '5号仓库'],top:'bottom'},
  tooltip: {trigger: 'axis'},
  xAxis: {type: 'category',data: []},
  yAxis: {type: 'value'},
  series: [
    {name: '1号仓库',type: 'line',data: [],smooth: true},
    {name: '2号仓库',type: 'line',data: [],smooth: true},
    {name: '3号仓库',type: 'line',data: [],smooth: true},
    {name: '4号仓库',type: 'line',data: [],smooth: true},
    {name: '5号仓库',type: 'line',data: [],smooth: true}
  ]
};

const lineoption2 = {
  title: {text: '入库金额统计', left:'center'},
  legend: {data: ['1号仓库', '2号仓库', '3号仓库', '4号仓库', '5号仓库'],top:'bottom'},
  tooltip: {trigger: 'axis'},
  xAxis: {type: 'category',data: []},
  yAxis: {type: 'value'},
  series: [
    {name: '1号仓库',type: 'line',data: [],smooth: true},
    {name: '2号仓库',type: 'line',data: [],smooth: true},
    {name: '3号仓库',type: 'line',data: [],smooth: true},
    {name: '4号仓库',type: 'line',data: [],smooth: true},
    {name: '5号仓库',type: 'line',data: [],smooth: true}
  ]
};

const pieoption1 = {
  title: {
    text: '产品出库数量占比',
    left: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left',
    formatter: function(name) {
      const item = pieoption1.series[0].data.find(d => d.name === name);
      return item && item.value > 0 ? name : '';
    }
  },
  series: [
    {
      name: '产品出库数量',
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


const pieoption2 = {
  title: {
    text: '产品入库数量占比',
    left: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left',
    formatter: function(name) {
      const item = pieoption2.series[0].data.find(d => d.name === name);
      return item && item.value > 0 ? name : '';
    }
  },
  series: [
    {
      name: '产品入库数量',
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
      let lineDom1 = document.getElementById('line1');
      let lineChart1 = echarts.init(lineDom1);
      lineChart1.setOption(lineoption1);
      this.$request.get('/linechartsIn').then(res =>{
         lineoption1.xAxis.data = res.data?.line1?.map(v=>v.date) || [];
        // 更新每个仓库的数据
        lineoption1.series.forEach((series, index) => {
            // 字段名：warehouse1, warehouse2...
            const field = `warehouse${index + 1}`;
            series.data = res.data.map(v => v[field] || 0);
        });
        // 重新渲染图表
        lineChart1.setOption(lineoption1);
    }).catch(error => {
        console.error('获取图表数据失败:', error);
    })


      let lineDom2 = document.getElementById('line2');
      let lineChart2 = echarts.init(lineDom2);
      lineChart2.setOption(lineoption2);
      this.$request.get('/linechartsOut').then(res =>{
         lineoption2.xAxis.data = res.data?.line2?.map(v=>v.date) || [];
        // 更新每个仓库的数据
        lineoption2.series.forEach((series, index) => {
            // 字段名：warehouse1, warehouse2...
            const field = `warehouse${index + 1}`;
            series.data = res.data.map(v => v[field] || 0);
        });
        // 重新渲染图表
        lineChart2.setOption(lineoption2);
    }).catch(error => {
        console.error('获取图表数据失败:', error);
    })

      let pieDom1 = document.getElementById('pie1');
      let piehart1 = echarts.init(pieDom1);
      piehart1.setOption(pieoption1);

      let pieDom2 = document.getElementById('pie2');
      let piehart2 = echarts.init(pieDom2);
      piehart2.setOption(pieoption2);
      this.$request.get('/piecharts').then(res =>  {
        // pieoption.series[0].data = res.data?.pie||[]
        // piehart.setOption(pieoption);
        const filteredData = (res.data?.pie1 || []).filter(item => item.value > 0);
        pieoption1.series[0].data = filteredData;
        // 更新图例数据（确保图例同步更新）
        pieoption1.legend.data = filteredData.map(item => item.name);
        piehart1.setOption(pieoption1);


        const filteredData2 = (res.data?.pie2 || []).filter(item => item.value > 0);
        pieoption2.series[0].data = filteredData2;
        // 更新图例数据（确保图例同步更新）
        pieoption2.legend.data = filteredData2.map(item => item.name);
        piehart2.setOption(pieoption2);

      })


    }
 }
</script>

<style scoped>
.el-row {
margin-top: 20px;
margin-bottom: 20px;
}
 
</style>
