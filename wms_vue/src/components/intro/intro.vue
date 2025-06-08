<template>
 <div >
  <!-- 个人信息 -->
   <div style="margin-top:15px ;">
      <el-row >
        <el-tooltip content="点击修改个人信息" placement="top" effect="light" >
          <el-card class="application-card" shadow="hover" style="text-align:left;height:150px" @click.native="toinfo">
              <!-- 卡片头部 -->
              <div slot="header" class="card-header" style="margin:10px ; display:flex;">
              <h3 style="margin:0px;font-size:15px;margin-left:20px">账户基本信息</h3>
              </div>
              <el-col :span="2">
                <div style="margin-top:15px;float:right">
                  <el-avatar :size="50" icon="el-icon-user-solid" ></el-avatar>
                </div>
              </el-col>
              <el-col :span="3" class="info-item">
                <div class="info-value">{{user.username}}</div>
                <div class="info-label">{{user.telephone}}</div>
              </el-col>

              <el-col style="width:20px" class="divider-item"><el-divider direction="vertical"></el-divider></el-col>

              <el-col :span="3" class="info-item">
                <div class="info-value">{{user.role}}</div>
                <div class="info-label">角色</div>
              </el-col>

              <el-col style="width:20px" class="divider-item"><el-divider direction="vertical"></el-divider></el-col>

              <el-col :span="3" class="info-item">
                <div class="info-value">{{user.userid}}</div>
                <div class="info-label">工号</div>
              </el-col>

              <el-col style="width:20px" class="divider-item"><el-divider direction="vertical"></el-divider></el-col>

              <el-col :span="3" class="info-item">
                <div class="info-value">{{user.time}}</div>
                <div class="info-label">入职时间</div>
              </el-col>

              <el-col style="width:20px" class="divider-item"><el-divider direction="vertical" style="width:100%"></el-divider></el-col>

              <el-col :span="3" class="info-item">
                <div class="info-value">{{ applications.length }}</div>
                <div class="info-label">待通过申请数</div>
              </el-col>

              <el-col style="width:20px" class="divider-item"><el-divider direction="vertical" style="width:100%" v-if="user.role!='普通员工'" ></el-divider></el-col>

              <el-col :span="3" class="info-item" v-if="user.role!='普通员工'" >
                <div class="info-value">{{ pendings.length }}</div>
                <div class="info-label">待审核申请数</div>
              </el-col>
          </el-card>
        </el-tooltip>
      </el-row>
    </div>
  
<div>
  <el-row :gutter="10">
    <el-col :span="12" style="height: 580px;width: 100%">
      <!-- 通知公告 -->
    <div style="display:flex ; margin:20px 0">
      <el-card style="width:100%">
        <div style="margin-bottom: 25px;font-size: 18px; font-weight: bold">近期公告</div>
        <!-- <el-timeline v-if="notices && notices.length > 0" style="padding: 0px">
          <el-timeline-item v-for="item in notices" :key="item.recordId" style="padding: 0px">
            <el-card>
              <p>关于{{item.pname}}的{{item.type}}申请</p>
              <h4>{{item.state}},审核意见：{{item.reviewRemark}}</h4>
              <p>{{item.reviewId}} 提交于 {{item.reviewTime}}</p>
            </el-card>
          </el-timeline-item>
        </el-timeline> -->

        <div v-if="notices && notices.length > 0" style="display: flex; gap: 20px;">
          <!-- 左侧时间线 (始终显示前3条) -->
          <div style="flex: 1">
            <el-timeline style="padding: 0">
              <el-timeline-item 
                v-for="item in notices.slice(0, 3)" 
                :key="item.recordId" 
                style="padding: 0"
              >
                <el-card style="margin-bottom: 10px;">
                  <p>关于{{item.pname}}的{{item.type}}申请</p>
                  <h4>{{item.state}},审核意见：{{item.reviewRemark}}</h4>
                  <p>{{item.reviewId}} 提交于 {{item.reviewTime}}</p>
                </el-card>
              </el-timeline-item>
            </el-timeline>
          
          </div>
          
          <!-- 右侧区域 (根据角色显示不同内容) -->
          <div style="flex: 1">
            <!-- 管理员显示库存预警 -->
            <template v-if="role !='普通员工'">
              <!-- <div style="margin-bottom: 15px; font-weight: bold">库存预警</div> -->
              <el-timeline style="padding: 0">
                <el-timeline-item 
                  v-for="alert in inventoryAlerts" 
                  :key="alert.stockid" 
                  style="padding: 0"
                >
                  <el-card style="margin-bottom: 10px;" :class="{'warning-card': alert.level === 'high', 'notice-card': alert.level === 'medium'}">
                    <p>{{alert.deposity}}的{{alert.pname}}库存不足</p>
                    <h4>当前库存: {{alert.quantity}},库存预警值：{{alert.threshold}}</h4>
                    <p>最后更新时间: {{alert.stocktime}}</p>
                  </el-card>
                </el-timeline-item>
              </el-timeline>
              
              <!-- 库存预警空状态 -->
              <div v-if="inventoryAlerts.length === 0" style="text-align: center; padding: 40px 0; color: #999;">
                <i class="el-icon-success" style="font-size: 48px; margin-bottom: 16px;"></i>
                <p>库存充足，无预警信息</p>
              </div>
            </template>
            
            <!-- 普通员工显示后3条申请 -->
            <template v-else>
              <el-timeline style="padding: 0">
                <el-timeline-item 
                  v-for="item in notices.slice(3, 6)" 
                  :key="item.recordId" 
                  style="padding: 0"
                >
                  <el-card style="margin-bottom: 10px;">
                    <p>关于{{item.pname}}的{{item.type}}申请</p>
                    <h4>{{item.state}},审核意见：{{item.reviewRemark}}</h4>
                    <p>{{item.reviewId}} 提交于 {{item.reviewTime}}</p>
                  </el-card>
                </el-timeline-item>
              </el-timeline>
              
              <!-- 当后3条不足时的提示 -->
              <div v-if="notices.length <= 3" style="text-align: center; padding: 40px 0; color: #999;">
                <i class="el-icon-document" style="font-size: 48px; margin-bottom: 16px;"></i>
                <p>暂无更多申请记录</p>
              </div>
            </template>
          </div>
        </div>

        <!-- 整个通知公告为空的状态 -->
        <div v-else style="text-align: center; padding: 40px 0; color: #999;">
          <i class="el-icon-document" style="font-size: 48px; margin-bottom: 16px;"></i>
          <p>暂无公告</p>
        </div>
      </el-card>
    </div>
    </el-col>
  </el-row>

</div>

 </div>
</template>

<script>

 export default {
    name:'app',
    data () {
       return {
         user:JSON.parse(localStorage.getItem('user') || '{}'),
         notices:[],
         applications: [],
         pendings: [],

         entries: [
          { name: '入库申请', icon: 'fas fa-inbox', iconClass: 'in-storage' },
          { name: '出库申请', icon: 'fas fa-truck-loading', iconClass: 'out-storage' },
          { name: '转移申请', icon: 'fas fa-exchange-alt', iconClass: 'transfer' },
          { name: '入库可视化', icon: 'fas fa-chart-line', iconClass: 'in-visual' },
          { name: '出库可视化', icon: 'fas fa-chart-bar', iconClass: 'out-visual' },
          { name: '库存可视化', icon: 'fas fa-boxes', iconClass: 'stock-visual' },
          { name: '我的任务', icon: 'fas fa-tasks', iconClass: 'my-task' },
          { name: '我的申请', icon: 'fas fa-file-alt', iconClass: 'my-request' }
        ],
        inventoryAlerts:[],
      

       }
    },
    components: {

    },
    created(){
      this.loadNotices(),
      this.loadpendings(),
      this.loadapplications(),
      this.loadAlerts()
    },
    methods: { 
      toinfo(){
        this.$router.push({path:'/myinfo'})
      },
      loadNotices(){
        this.$request.get('/recordIn/notice').then(res => {
          this.notices=res.data
        })
      },
      loadAlerts(){
        this.$request.get('/stock/stockalert').then(res => {
          console.log("库存预警：", res.data);
          this.inventoryAlerts=res.data
        })
      },
      loadapplications(){
          this.$request.get('/recordIn/myPendingData').then(res =>{
            console.log("当前卡片的数据：", res.data);
            if(res.code==0)
            {
                this.applications=res.data;
            }
        })
        },
        loadpendings(){
        this.$request.get('/recordIn/allPendingData').then(res =>{
          if(res.code==0)
          {
              this.pendings=res.data;
          }
      })
      },

    }
 }
</script>

<style scoped>
 ::v-deep .el-card__header{
  padding: 0px;
}
 ::v-deep .card-header{
  margin:20px;
 }
 ::v-deep .el-card__body{
  padding:10px;
 }
 ::v-deep .el-icon-user-solid{
  margin-top: 10px;
  font-size: 25px;
 }
 .el-divider--vertical{
  margin-top:20px;
  height: 50px;
 }
 .info-item {
      flex: 1;
      min-width: 0;
      padding: 0 20px;

  }
        
  .info-value {
      font-size: 18px;
      font-weight: bold;
      height: 30px;
      line-height: 30px;
      margin-top: 15px;
      display: flex;
      align-items: center;
  }
        
  .info-label {
      font-size: 13px;
      color: #909399;
      height: 20px;
      line-height: 20px;
      margin-top:5px
  }

        
</style>
