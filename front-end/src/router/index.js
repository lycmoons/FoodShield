import {createRouter, createWebHistory} from "vue-router";
import {getRole, getToken} from "@/net/index.js";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path:'/',
            name: 'welcome',
            component: () => import('@/views/WelcomeView.vue'),
            children: [
                {
                    path: '',
                    name: 'welcome-login',
                    component: () => import('@/views/welcome/LoginPage.vue')
                },
                {
                    path: 'register',
                    name: 'welcome-register',
                    component: () => import('@/views/welcome/RegisterPage.vue')
                },
                {
                    path: 'reset',
                    name: 'welcome-reset',
                    component: () => import('@/views/welcome/ResetPage.vue')
                }
            ]
        },
        {
            path: '/index',
            name: 'index',
            component: () => import('@/views/IndexView.vue')
        },
        {
            path: '/admin',
            name: 'admin',
            component: () => import('@/views/AdminView.vue')
        }
    ]
})

// 配置路由守卫
router.beforeEach((to, from, next) => {
    const token = getToken()
    // 已经登录后不能再访问登录界面
    // 选择记住密码后，token被存储在localStorage中
    // 下次登录是可以获取的，故不能再访问登录界面，直接重定向到主界面，完成记住密码的功能
    if(to.name.startsWith('welcome-') && token !== null) {
        let role = getRole()
        if (role === 'user'){
            next('/index')  // 重定向
        }
        else if (role === 'admin'){
            next('/admin')
        }
    }

    else if((to.fullPath.startsWith('/index') || to.fullPath.startsWith('/admin')) && token === null){  // 没登录之前不能访问主界面
        next('/')  // 重定向
    }

    else {  // 正常情况下
        next()   // 无需重定向
    }

})

export default router