# api设计



## 前言

后端响应的统一格式：

```json
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

- **前端请求**

```js
POST 请求
url = '/api/auth/login'
headers = {
    'Content-Type': 'application/x-www-form-urlencoded'
}
data = {
    username:   // 可以使用用户名，也可以是邮箱
    password:   // 用户的密码
}
```

- **后端成功响应**

```json
{
    code: 200,
    data: {
        token:      // 用户令牌，后续请求需要添加在请求头中
        expire:     // 令牌的过期时间，类型 Date
        username:   // 用户名，不是邮箱
        role:       // 角色
    },
    message: "请求成功"
}
```

- **后端失败响应**

```json
{
    code: 400,   // 不一定、反正不是200
    data: null,
    message:  // 此处是具体的失败原因，用于弹窗给用户看
}
```



## 退出登录

- **前端请求**

```js
GET 请求
url = '/api/auth/logout'
token = '...'   // 登录时获取的 token
headers = {
    'Authorization': `Bearer ${token}`   // 'Bearer ' 是规范的前缀
}
```

- **后端成功响应**

```json
{
    code: 200,
    data: null,
    message: "请求成功"
}
```

- **后端失败响应**

```json
{
    code: 400,   // 不一定、反正不是200
    data: null,
    message:  // 此处是具体的失败原因，用于弹窗给用户看
}
```



## 请求邮箱验证码

- **前端请求**

```js
POST 请求
url = '/api/email/ask-code'
headers = {
    'Content-Type': 'application/json'
}
data = {
    email:     // 用户邮箱
    type:      // 请求验证码的类型 'register' || 'reset'
}
```

- **后端成功响应**

```json
{
    code: 200,
    data: null,  // 验证码是发送到用户邮箱的，这里不需要数据
    message: "请求成功"
}
```

- **后端失败响应**

```json
{
    code: 400,   // 不一定、反正不是200
    data: null,
    message:  // 此处是具体的失败原因，用于弹窗给用户看
}
```



## 注册

- **前端请求**

```js
POST 请求
url = '/api/account/register'
headers = {
    'Content-Type': 'application/json'
}
data = {
    username:   // 用户名
    password:   // 密码
    email:      // 邮箱
    code:       // 验证码
}
```

- **后端成功响应**

```json
{
    code: 200,
    data: null,
    message: "请求成功"
}
```

- **后端失败响应**

```json
{
    code: 400,   // 不一定、反正不是200
    data: null,
    message:  // 此处是具体的失败原因，用于弹窗给用户看
}
```



## 重置密码

- **前端请求**

```js
POST 请求
url = '/api/account/reset-password'
headers = {
    'Content-Type': 'application/json'
}
data = {
    password:   // 新密码
    email:      // 邮箱
    code:       // 验证码
}
```

- **后端成功响应**

```json
{
    code: 200,
    data: null,
    message: "请求成功"
}
```

- **后端失败响应**

```json
{
    code: 400,   // 不一定、反正不是200
    data: null,
    message:  // 此处是具体的失败原因，用于弹窗给用户看
}
```

