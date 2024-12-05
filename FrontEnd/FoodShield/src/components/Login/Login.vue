<template>
    <div class="Body">
        <div class="container">
            <div class="image-container"></div>
            <div class="form-container">
                <h1>登录</h1>
                <div class="form-group">
                    <p>用户名/邮箱</p>
                    <input type="text" v-model="username">
                </div>
                <div class="form-group">
                    <p>密码</p>
                    <input ref="passwordInput" type="password" class="password-input" v-model="password">
                    <!-- <button @click="togglePassword" class="password-button">显示/隐藏密码</button> -->
                </div>
                <br>
                <button @click="loginHandle">登录</button>
                <br>
                <div class="options">
                    <label>
                        <input type="checkbox" v-model="rememberPassword">记住密码
                    </label>
                    <router-link to="/resetPassword">
                        <a href="#" style="text-decoration: none;" >忘记密码？</a>
                    </router-link>
                   
                </div>
                <br>
                <div class="divider-container">
                    <div class="divider-line"></div>
                    <span class="divider-text">没有账号</span>
                    <div class="divider-line"></div>
                </div>
                <br>
                <div class="register-link">
                    <router-link to="/register">
                        <button>注册</button>
                    </router-link>
                </div>
                 
                <!--<p class="terms">
                    点击“登录”即表示您同意<a href="#">条款</a>。有关我们如何处理您的个人数据的更多信息，请参阅我们的<a href="#">隐私政策</a>。
                </p> -->
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
            rememberPassword: false,
            router: useRouter(),
            dialog: false,
        }
    },

    methods: {
        // 点击登录按键
        loginHandle() {
            if (this.username === "" || this.password === "") {
                alert("账号与密码不能为空！");
                return;
            }
            loginReq(this.username, this.password, this.callback);
        },

        callback(data){
            // TODO: 判断身份并跳转
        },

        togglePassword() {
            const input = this.$refs.passwordInput;
            input.type = input.type === "password" ? "text" : "password";
        },
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
    position: relative;
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

.terms a {
    color: #007bff;
    text-decoration: none;
}

.options {
    display: flex;
    justify-content: space-between;
    margin: 10px 0;
}

.divider-container {
    display: flex;
    align-items: center;
    justify-content: center;
}

.divider-line {
    flex: 1;
    border-top: 1px solid #ddd;
    margin: 0 10px;
}

.divider-text {
    margin: 0 10px;
}

.register-link {
    margin-top: 20px;
    text-align: center;
}

.register-link a {
    color: #76c893;
    text-decoration: none;
}


</style>