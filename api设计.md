# api设计



## 前言

后端响应的统一格式：

```js
{
    code:       // 状态码（成功为 200 ）
    data:       // 数据
    message:    // 简单说明
}
```

前端处理样式（以 axios 为例）：

```js
axios.get('/api/...').then(({data}) => {
    if(data.code === 200){   // 连接成功，且服务器处理成功
        ElMessage.success('请求成功')
        
        console.log(data.data)  // 数据
    }
    else{   // 连接成功，但服务器处理失败
        ElMessage.warning(data.message)  // 失败的说明
    }
}).catch(err => {   // 连接失败
    console.log(err)
    ElMessage.warning('出现了一些问题，请联系管理员')
})
```



## 登录



## 请求验证码



## 注册



## 重置密码

