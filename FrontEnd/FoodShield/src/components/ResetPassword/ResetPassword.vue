<template>
    <div class="Body">
        <div class="container">
            <div class="image-container"></div>
            <div class="form-container">
                <h1>密码重置</h1>
                <div class="form-group">
                    <p>邮箱</p>
                    <input type="email" v-model="email">
                </div>
                <div class="form-group">
                    <p>新密码</p>
                    <input type="password" v-model="password">
                </div>
                <div class="form-group">
                    <p>再次输入新密码</p>
                    <input type="twice_password" v-model="twice_password">
                </div>
                <div class="form-group">
                    <p>验证码</p>
                    <input type="code" v-model="code">
                </div>
                <br>
                <button @click="resetPWDHandle">注册</button>
            </div>
        </div>
    </div>
</template>


<script>
import { loginReq } from "../../../HTTP/http";
import { useRouter } from "vue-router";

export default {
    data() {
        return {
            password: "",
            twice_password: "",
            email: "",
            code: "",

            router: useRouter(),
            dialog: false,
        }
    },

    methods: {
        // 点击登录按键
        resetPWDHandle() {
            if(this.password !== this.twice_password) {
                alert("两次密码输入不同")
            } else {
                this.resetPWDHandle(this.password, this.email, this.code, this.callback);
            }
        },

        callback(response){
            // 跳转到管理员页面
            console.log("跳转到管理员页面");
            this.router.push("/admin");
        }
    }
}

</script>


<style scoped>
.Body {
    font-family: Arial, sans-serif;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    margin: 0;
    background-color: #f2f2f2;
}

.container {
    display: flex;
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    max-width: 800px;
    width: 100%;
}

.image-container {
    flex: 1;
    background: url("../../../public/favicon.ico") no-repeat center center;
    background-size: cover;
}

.form-container {
    flex: 1;
    padding: 40px;
}

h1 {
    margin: 0 0 20px;
    font-size: 24px;
}

.form-group {
    margin-bottom: 20px;
}

.form-group p {
    display: block;
    margin-bottom: 5px;
}

.form-group input {
    width: calc(100% - 20px);
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
}

.form-container button {
    width: 100%;
    padding: 10px;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 5px;
    font-size: 16px;
    cursor: pointer;
}

.form-container button:hover {
    background-color: #0056b3;
}

.terms {
    font-size: 12px;
    color: #777;
}
</style>