<template>
    <div class="Body">
        <div class="container">
            <div class="image-container"></div>
            <div class="form-container">
                <h1>注册</h1>
                <div class="form-group">
                    <p>用户名</p>
                    <input type="text" v-model="username">
                </div>
                <div class="form-group">
                    <p>密码</p>
                    <input type="password" v-model="password">
                </div>
                <div class="form-group">
                    <p>邮箱</p>
                    <input type="email" v-model="email">
                </div>
                <div class="form-group">
                    <p>验证码</p>
                    <input type="code" v-model="code">
                </div>
                <br>
                <button @click="registerHandle">注册</button>
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
            username: "",
            password: "",
            email: "",
            code: "",

            router: useRouter(),
            dialog: false,
        }
    },

    methods: {
        // 点击登录按键
        registerHandle() {
            this.registerHandleReq(this.username, this.password, this.email, this.code, this.callback);
        },

        callback(response){
            // TODO: 跳转回登陆界面
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