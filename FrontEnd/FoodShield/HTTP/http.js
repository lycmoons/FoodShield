// 登录请求
import axios from 'axios';
import { ElMessage } from 'element-plus';

export function loginReq(username, password, callback) {
    const url = 'http://127.0.0.1:8000/api/auth/login';  // 完整的 URL
    const headers = {
        'Content-Type': 'application/x-www-form-urlencoded'
    }

    let data = {
        username: username,
        password: password
    };
    
    axios.post(url).then(({data}) => {
        if(data.code === 200){   // 连接成功，且服务器处理成功
            ElMessage.success('请求成功')
            callback(data.data)
            console.log(data.data)  // 数据
        }
        else{   // 连接成功，但服务器处理失败
            ElMessage.warning(data.message)  // 失败的说明
        }
    }).catch(err => {   // 连接失败
        console.log(err)
        ElMessage.warning('出现了一些问题，请联系管理员')
    })
}

export function registerReq(username, password, email, code, callback){
    const url = 'http://127.0.0.1:8000/api/account/register';  // 完整的 URL
    const headers = {
        'Content-Type': 'application/x-www-form-urlencoded'
    }

    let data = {
        username: username,  // 用户名
        password: password,  // 密码
        email: email,        // 邮箱
        code: code          // 验证码
    };
    
    axios.post(url).then(({data}) => {
        if(data.code === 200){   // 连接成功，且服务器处理成功
            ElMessage.success('请求成功')
            callback(data.data)
            console.log(data.data)  // 数据
        }
        else{   // 连接成功，但服务器处理失败
            ElMessage.warning(data.message)  // 失败的说明
        }
    }).catch(err => {   // 连接失败
        console.log(err)
        ElMessage.warning('出现了一些问题，请联系管理员')
    })
}