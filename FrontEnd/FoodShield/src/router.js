import { createRouter, createWebHashHistory } from "vue-router";

import Login from "./components/Login/Login.vue";
import Register from "./components/Register/Register.vue";
import ResetPassword from "./components/ResetPassword/ResetPassword.vue";

const router = createRouter({
    history:createWebHashHistory(import.meta.env.BASE_URL),
    routes:[
        {
            path: "/",
            redirect: "/login",
        },
        {
            path: "/login",
            name: "login",
            component: Login,
        },
        {
            path: "/register",
            name: "register",
            component: Register,
        },
        {
            path: "/resetPassword",
            name: "resetPassword",
            component: ResetPassword,
        },
    ]
});

router.afterEach(() => {
    // 确保每次路由切换后滚动到顶部
    window.scrollTo(0, 0);
});


export default router;