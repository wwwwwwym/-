<template>
<!-- 首页 -->
 <div id='app'>
    <el-container style="height: 100vh">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246);overflow: hidden;min-height: 100vh">
        <div style="height:70px;display: flex;justify-content: center;align-items: center">
          <img src="@/assets/logo.png" alt="" style="width: 10%">
          <span style="margin-left: 10px">仓库管理系统</span>
        </div>
        
        <el-menu class="el-menu" :default-active="this.$route.path" router :unique-opened="true" >
          <el-menu-item index="/intro"><i class="el-icon-menu"></i>首页</el-menu-item>
          <el-submenu index="/apply">
            <template slot="title"><i class="el-icon-message"></i>申请提交</template>
            <el-menu-item index="/apply/in_apply"><i class="el-icon-document"></i>出入库申请</el-menu-item>
            <el-menu-item index="/apply/move_apply"><i class="el-icon-sort"></i>库存转移申请</el-menu-item>
          </el-submenu>

          <el-submenu index="/manager_in">
            <template slot="title"><i class="el-icon-bottom-right"></i>入库管理</template>
            <el-menu-item index="/manager_in/search_in"><i class="el-icon-search"></i>入库查询</el-menu-item>
            <el-menu-item index="/manager_in/view_in" v-if="user.roleid!=1"><i class="el-icon-pie-chart"></i>入库可视化</el-menu-item>
          </el-submenu>
          <el-submenu index="/manager_out">
            <template slot="title"><i class="el-icon-top-left"></i>出库管理</template>
            <el-menu-item index="/manager_out/search_out"><i class="el-icon-search"></i>出库查询</el-menu-item>
            <el-menu-item index="/manager_out/view_out" v-if="user.roleid!=1"><i class="el-icon-pie-chart"></i>出库可视化</el-menu-item>
          </el-submenu>
          <el-submenu index="manager_store">
            <template slot="title"><i class="el-icon-shopping-cart-2"></i>库存管理</template>
            <el-menu-item index="manager_store/search"><i class="el-icon-search"></i>库存查询</el-menu-item>
            <el-menu-item index="manager_store/view" v-if="user.roleid!=1"><i class="el-icon-pie-chart"></i>库存可视化</el-menu-item>
          </el-submenu>
          <el-menu-item index="/manager_personnel" v-if="user.roleid!=1">
            <template slot="title"><i class="el-icon-user"></i>人员管理</template>
          </el-menu-item>
          <el-submenu index="/manager_other" v-if="user.roleid!=1">
            <template slot="title"><i class="el-icon-edit"></i>其他管理</template>
            <el-menu-item index="/manager_other/addkind"><i class="el-icon-plus"></i>种类添加</el-menu-item>
            <el-menu-item index="/manager_other/addhouse"><i class="el-icon-house"></i>创建仓库</el-menu-item>
          </el-submenu>

          <el-menu-item index="/myapplication">
            <template slot="title"><i class="el-icon-finished"></i>我的申请</template>
          </el-menu-item>
          <el-menu-item index="myinfo">
            <template slot="title"><i class="el-icon-user"></i>个人中心</template>
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
