import Vue from "vue";
import VueRouter from "vue-router";
Vue.use(VueRouter);

//导入组件
import register from '@/components/user_register.vue'
import login from '@/components/user_login.vue'
import main from '@/components/main.vue'
import intro from '@/components/intro/intro.vue'
import error from '@/components/Auth/403.vue'
import nofound from '@/components/Auth/404.vue'
import newPassword from '@/components/myinfo/newPassword.vue'

import in_apply from '@/components/apply/in_apply.vue'
import move_apply from '@/components/apply/move_apply.vue'

import manager_in_search from '@/components/manager_in/search_in.vue'
import manager_in_view from '@/components/manager_in/view_in.vue'

import manager_out_search from '@/components/manager_out/search_out.vue'
import manager_out_view from '@/components/manager_out/view_out.vue'

import manager_store_search from '@/components/manager_store/search_store.vue'
import manager_store_view from '@/components/manager_store/view_store.vue'

import manager_personnel from '@/components/manager_personnel/manager_personnel.vue'

import addDeposity from '@/components/manager_other/addDeposity.vue'

// import myapplication from '@/components/submit.vue'
import myinfo from '@/components/myinfo/myinfo.vue'

const router = new VueRouter({// 生成一个路由实例
    routes : [ //路由表示的是url和组件的一一对应的关系info
        {//为路径设置重定向
            path:"/",
            redirect: "/register"
        },
//注册路由
        {
            name:'register',
            path:'/register',
            component:register
        },
//登录路由
        {
            name:'login',
            path:'/login',
            component:login
        },
//主页面路由
        {
            name:'main',
            path:'/main',
            component:main,
            children:[//侧边菜单栏子路由

                {
                    name:'newPassword',
                    path:'/newPassword',
                    component:newPassword
                },

                {
                    name:'403',
                    path:'/403',
                    component:error
                },

                {
                    name:'intro',
                    path:'/intro',
                    component:intro
                },

                {//1-1
                    name:'in_apply', // 给当前路由组件定义的名字
                    path:'/apply/in_apply', // url路径
                    component:in_apply, // 组件名称
                },
                {//1-2
                    name:'move_apply',
                    path:'/apply/move_apply',
                    component:move_apply
                },

                {//2-1
                    name:'search_in',
                    path:'/manager_in/search_in',
                    component:manager_in_search
                },
                {//2-2
                    name:'view_in',
                    path:'/manager_in/view_in',
                    component:manager_in_view
                },

                {//3-1
                    name:'search_out',
                    path:'/manager_out/search_out',
                    component:manager_out_search
                },
                {//3-2
                    name:'view_out',
                    path:'/manager_out/view_out',
                    component:manager_out_view
                },

                {//4-1
                    name:'search_store',
                    path:'/manager_store/search_store',
                    component:manager_store_search
                },
                {//4-2
                    name:'view_store',
                    path:'/manager_store/view_store',
                    component:manager_store_view
                },

                {//5
                    name:'manager_personnel',
                    path:'/manager_personnel',
                    component:manager_personnel
                },

                {//6-2
                    name:'addDeposity',
                    path:'/addDeposity',
                    component:addDeposity
                },

                // {//7
                //     name:'myapplication',
                //     path:'/myapplication',
                //     component:myapplication
                // },
                {//8
                    name:'myinfo',
                    path:'/myinfo',
                    component:myinfo
                },

            ]
        },
        {//404 nofound
            name:'404',
            path:'/*',
            component:nofound
        },
        
    ]
        // linkActiveClass:mycss,
});

router.beforeEach((to,from,next)=>{//路由守卫
    // console.log(to,from,next)
    let user=JSON.parse(localStorage.getItem('user') || '{}');//获取当前用户信息
    let adminPaths=['/manager_out/view_out','/manager_in/view_in','/manager_personnel','/manager_other','/manager_store/view']
    if(user.roleid==1 && adminPaths.includes(to.path)){//如果当前用户不是管理员，当前到达路径是管理员才有权限访问的路径，这个时候就让用户去到去到没有权限的页面
        next('/403')
    }
    else{
        next()//放行
    }
});
    export default router // 导出路由实例