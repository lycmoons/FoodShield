import {createRouter, createWebHistory} from "vue-router";
import {getRole, getToken} from "@/net/index.js";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        // 欢迎界面
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

        // 用户界面
        {
            path: '/index',
            name: 'index',
            component: () => import('@/views/IndexView.vue'),
            children: [
                // 首页
                {
                    path: '',
                    name: 'home',
                    component: () => import('@/views/index/HomePage.vue')
                },

                // 留言板
                {
                    path: 'post',
                    name: 'post',
                    component: () => import('@/views/index/PostPage.vue')
                },

                // 食品溯源
                {
                    path: 'trace',
                    name: 'trace',
                    component: () => import('@/views/index/FoodTracePage.vue')
                },

                // 食品报告
                {
                    path: 'report',
                    name: 'report',
                    component: () => import('@/views/index/FoodReportPage.vue')
                },

                // 投诉
                {
                    path: 'complaint',
                    name: 'complaint',
                    component: () => import('@/views/index/ComplaintPage.vue'),
                    children: [
                        // 我的投诉
                        {
                            path: '',
                            name: 'my-complaint',
                            component: () => import('@/views/index/complaint/AllComplaint.vue')
                        },

                        // 新建投诉
                        {
                            path: 'new-complaint',
                            name: 'new-complaint',
                            component: () => import('@/views/index/complaint/NewComplaint.vue')
                        }
                    ]
                },

                // 我的
                {
                    path: 'account',
                    name: 'account',
                    component: () => import('@/views/index/AccountPage.vue'),
                    children: [
                        // 我的账号
                        {
                            path: '',
                            name: 'my-account',
                            component: () => import('@/views/index/account/PersonalInfoPage.vue')
                        },

                        // 我的消息
                        {
                            path: 'my-message',
                            name: 'my-message',
                            component: () => import('@/views/index/account/PersonalMessagePage.vue')
                        }
                    ]
                }
            ]
        },

        // 管理员界面
        {
            path: '/admin',
            name: 'admin',
            component: () => import('@/views/AdminView.vue')
        },

        // 新闻的详细显示界面
        {
            path: '/news/:id/:title/:content/:photo_url/:date',
            name: 'news',
            component: () => import('@/views/NewsView.vue')
        },

        // 知识的详细显示界面
        {
            path: '/knowledge/:id/:category/:title/:content/:photo_url/:date',
            name: 'knowledge',
            component: () => import('@/views/KnowledgeView.vue')
        },

        // 帖子的详细显示界面
        {
            path: '/postDetail/:id/:title/:username/:date/:content/:photo_url',
            name: 'postDetail',
            component: () => import('@/views/PostView.vue')
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