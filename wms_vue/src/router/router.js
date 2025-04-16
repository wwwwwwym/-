import Vue from "vue";
import VueRouter from "vue-router";
Vue.use(VueRouter);

//导入组件
import register from '@/components/user_register.vue'
import login from '@/components/user_login.vue'
import main from '@/components/main.vue'

import in_apply from '@/components/apply/in_apply.vue'
import move_apply from '@/components/apply/move_apply.vue'

import manager_in_search from '@/components/manager_in/search_in.vue'
import manager_in_view from '@/components/manager_in/view_in.vue'

// import manager_out_search from '@/components/submit.vue'
// import manager_out_view from '@/components/submit.vue'

// import manager_store_search from '@/components/submit.vue'
// import manager_store_view from '@/components/submit.vue'

import manager_personnel from '@/components/manager_personnel/manager_personnel.vue'

// import manager_other_addkind from '@/components/submit.vue'
// import manager_other_addhouse from '@/components/submit.vue'

// import myapplication from '@/components/submit.vue'
// import myinfo from '@/components/submit.vue'

const router = new VueRouter({// 生成一个路由实例
    routes : [ //路由表示的是url和组件的一一对应的关系
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
                    name:'search',
                    path:'/manager_in/search_in',
                    component:manager_in_search
                },
                {//2-2
                    name:'view',
                    path:'/manager_in/view_in',
                    component:manager_in_view
                },

                // {//3-1
                //     name:'search',
                //     path:'/manager_out/search',
                //     component:manager_out_search
                // },
                // {//3-2
                //     name:'view',
                //     path:'/manager_out/view',
                //     component:manager_out_view
                // },

                // {//4-1
                //     name:'search',
                //     path:'/manager_store/search',
                //     component:manager_store_search
                // },
                // {//4-2
                //     name:'view',
                //     path:'/manager_store/view',
                //     component:manager_store_view
                // },

                {//5
                    name:'manager_personnel',
                    path:'/manager_personnel',
                    component:manager_personnel
                },


                // {//6-1
                //     name:'addkind',
                //     path:'/manager_other/addkind',
                //     component:manager_other_addkind
                // },
                // {//6-2
                //     name:'addhouse',
                //     path:'/manager_other/addhouse',
                //     component:manager_other_addhouse
                // },

                // {//7
                //     name:'myapplication',
                //     path:'/myapplication',
                //     component:myapplication
                // },
                // {//8
                //     name:'myinfo',
                //     path:'/myinfo',
                //     component:myinfo
                // },




            ]
        },
        
        
    ],
        // linkActiveClass:mycss,
});
    export default router // 导出路由实例