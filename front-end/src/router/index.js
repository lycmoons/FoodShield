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
            component: () => import('@/views/AdminView.vue'),
            children: [
                //食品信息管理界面
                {
                    path: 'foodinfomanage',
                    name: 'foodinfomanage',
                    component: () => import('@/views/admin/FoodInfoManagePage.vue'),
                    children: [
                        {
                            path: 'foodinfolist',
                            name: 'foodinfolist',
                            component: () => import('@/views/admin/foodinfo/FoodInfoListPage.vue'),
                        },
                        {
                            path: 'foodinfodetail',
                            name: 'foodinfodetail',
                            component: () => import('@/views/admin/foodinfo/FoodInfoDetailPage.vue'),
                        },
                        {
                            path: 'inputfoodinfo',
                            name: 'inputfoodinfo',
                            component: () => import('@/views/admin/foodinfo/InputFoodInfoPage.vue'),
                        },
                    ]
                },

                // 用户投诉受理界面
                {
                    path: 'complaintmanage',
                    name: 'complaintmanage',
                    component: () => import('@/views/admin/ComplaintManagePage.vue'),
                    children: [
                        {
                            path: 'complaintlist',
                            name: 'complaintlist',
                            component: () => import('@/views/admin/complaint/ComplaintListPage.vue'),
                        },
                        {
                            path: 'complaintacceptance',
                            name: 'complaintacceptance',
                            component: () => import('@/views/admin/complaint/ComplaintAcceptancePage.vue'),
                        },
                    ]
                },

                // 食品报告上传界面
                {
                    path: 'reportmanage',
                    name: 'reportmanage',
                    component: () => import('@/views/admin/ReportManagePage.vue'),
                    // children: [
                    //     {
                    //         path: 'reportlist',
                    //         name: 'reportlist',
                    //         component: () => import('@/views/admin/report/ReportListPage.vue'),
                    //     },
                    //     {
                    //         path: 'reportdetail',
                    //         name: 'reportdetail',
                    //         component: () => import('@/views/admin/report/ReportDetailPage.vue'),
                    //     },
                    //     {
                    //         path: 'createreport',
                    //         name: 'createreport',
                    //         component: () => import('@/views/admin/report/CreateReportPage.vue'),
                    //     },
                    // ]
                },

                // 推送新闻界面
                {
                    path: 'articlemanage',
                    name: 'articlemanage',
                    component: () => import('@/views/admin/ArticleManagePage.vue'),
                    children: [
                        {
                            path: 'articlelist',
                            name: 'articlelist',
                            component: () => import('@/views/admin/article/ArticleListPage.vue')
                        },
                        {
                            path: 'articledetail',
                            name: 'articledetail',
                            component: () => import('@/views/admin/article/ArticleDetailPage.vue')
                        },
                        {
                            path: 'createarticle',
                            name: 'createarticle',
                            component: () => import('@/views/admin/article/CreateArticlePage.vue'),
                        },
                    ]
                },

                // 推送知识界面
                {
                    path: 'knowledgemanage',
                    name: 'knowledgemanage',
                    component: () => import('@/views/admin/ArticleManagePage.vue'),
                    children: [
                        {
                            path: 'knowledgelist',
                            name: 'knowledgelist',
                            component: () => import('@/views/admin/knowledge/KnowledgeListPage.vue')
                        },
                        {
                            path: 'knowledgedetail',
                            name: 'knowledgedetail',
                            component: () => import('@/views/admin/knowledge/KnowledgeDetailPage.vue')
                        },
                        {
                            path: 'createknowledge',
                            name: 'createknowledge',
                            component: () => import('@/views/admin/knowledge/CreateKnowledgePage.vue'),
                        },
                    ]
                },

                // 管理员日志界面
                {
                    path: 'log',
                    name: 'admin-log',
                    component: () => import('@/views/admin/AdminLogPage.vue'),
                },

                // 个人信息
                {
                    path: 'profile',
                    name: 'profile',
                    component: () => import('@/views/admin/AdminProfileManagePage.vue'),
                    children: [
                        {
                            path: 'admin-profile',
                            name: 'admin-profile',
                            component: () => import('@/views/admin/adminprofile/AdminProfilePage.vue'),
                        },
                        {
                            path: 'admin-message',
                            name: 'admin-message',
                            component: () => import('@/views/admin/adminprofile/AdminMessagePage.vue'),
                        },
                    ]
                },
            ]
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

    // 在没登录的情况下访问用户界面或管理员界面，直接跳转到登录界面
    else if((to.fullPath.startsWith('/index') || to.fullPath.startsWith('/admin')) && token === null){
        next('/')  // 重定向
    }

    else {  // 正常情况下
        next()   // 无需重定向
    }

})

export default router

