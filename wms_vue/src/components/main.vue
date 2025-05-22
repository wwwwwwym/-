<template>
<!-- 首页 -->
 <div id='app'>
    <el-container style="height: 100vh">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246);overflow: hidden;min-height: 100vh">
        <div style="height:70px;display: flex;justify-content: center;align-items: center">
          <img src="@/assets/logo.png" alt="" style="width: 10%">
          <span style="margin-left: 10px">仓储管理系统</span>
        </div>
        
        <el-menu class="el-menu" :default-active="this.$route.path" router :unique-opened="true" >
          <el-menu-item index="/intro"><i class="el-icon-menu"></i>首页</el-menu-item>
          <el-submenu index="/apply">
            <template slot="title"><i class="el-icon-message"></i>申请提交</template>
            <el-menu-item index="/apply/in_apply"><i class="el-icon-document"></i>出入库申请</el-menu-item>
            <el-menu-item index="/apply/move_apply"><i class="el-icon-sort"></i>库存转移申请</el-menu-item>
          </el-submenu>
          <!-- <el-menu-item index="/apply">
            <template slot="title"><i class="el-icon-message"></i>申请提交</template>
          </el-menu-item> -->

          <el-submenu index="/manager_record">
            <template slot="title"><i class="el-icon-bottom-right"></i>货流管理</template>
            <el-menu-item index="/manager_record/search_record"><i class="el-icon-search"></i>货流记录查询</el-menu-item>
            <el-menu-item index="/manager_record/view_record" v-if="user.role!='普通员工'"><i class="el-icon-pie-chart"></i>记录统计</el-menu-item>
          </el-submenu>
          <el-submenu index="manager_store">
            <template slot="title"><i class="el-icon-shopping-cart-2"></i>库存管理</template>
            <el-menu-item index="/manager_store/search_store"><i class="el-icon-search"></i>库存查询</el-menu-item>
            <el-menu-item index="/manager_store/view_store" v-if="user.role!='普通员工'"><i class="el-icon-pie-chart"></i>库存可视化</el-menu-item>
          </el-submenu>
          <el-menu-item index="/manager_personnel" v-if="user.role!='普通员工'">
            <template slot="title"><i class="el-icon-user"></i>人员管理</template>
          </el-menu-item>
          <el-menu-item index="/manager_deposity" v-if="user.role!='普通员工'">
            <template slot="title"><i class="el-icon-house"></i>仓库管理</template>
          </el-menu-item>

          <el-submenu index="application">
            <template slot="title"><i class="el-icon-finished"></i>我的申请</template>
            <el-menu-item index="/application/myapplication"><i class="el-icon-search"></i>我提交的申请</el-menu-item>
            <el-menu-item index="/application/mypending" v-if="user.role!='普通员工'"><i class="el-icon-pie-chart"></i>我审核的申请</el-menu-item>
          </el-submenu>
          <el-menu-item index="/myinfo">
            <template slot="title"><i class="el-icon-user"></i>个人中心</template>
          </el-menu-item>
          <el-menu-item index="/logs_info"  v-if="user.role=='超管'">
            <template slot="title" ><i class="el-icon-finished"></i>系统日志</template>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="text-align: right; font-size: 12px; height: 70px">
          <el-dropdown>
            <i class="el-icon-setting" style="margin-right: 15px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
              <el-dropdown-item @click.native="$router.push('/newPassword')">修改密码</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <span style="font-size: 15px">{{user.username}}</span>
        </el-header>

        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
 </div>
</template>


<script>

 export default {
    name:'app',
    data () {
      return {
        user:JSON.parse(localStorage.getItem('user') || '{}')
      }
    },
    components: {

    },
    methods: { 
      logout(){
        localStorage.removeItem('user');
        this.$router.push('/login')
      }
      
    }
 }
</script>

<style scoped>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
  text-align: left;
}
 
</style>
