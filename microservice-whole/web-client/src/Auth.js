/** OpenAuth JWT client **/
let AUTH_URL = 'http://localhost:8009/auth/oauth/token'
let CLIENT_ID = 'rs1'

var token = null
var refreshToken = null
var user
var refreshTimer = null
var vm

function setRefreshTimer (seconds) {
  if (refreshTimer != null) {
    window.clearTimeout(refreshTimer)
  }
  refreshTimer = window.setTimeout(function () {
    // 刷新token的代码
    console.log('将要开始刷新Token, old token=' + token)
    var postBody = 'refresh_token=' + escape(refreshToken) + '&grant_type=refresh_token&client_id=' + CLIENT_ID
    var postOptions = {'headers': {'Content-Type': 'application/x-www-form-urlencoded'}}
    vm.http.post(AUTH_URL, postBody, postOptions).then((response) => {
      // 获取到token
      var json = response.data
      parseToken(json)
    }, (response) => {
      // 响应错误回调
      token = null
      refreshToken = null
      user = null
      refreshTimer = null
    })
  }, seconds)
  console.log('Timeout was set after ' + seconds + 'ms.')
}

function login (username, password, successCallback, errorCallback) {
  // login
  var postBody = 'grant_type=password&client_id=' + CLIENT_ID + '&username=' + escape(username) + '&password=' + escape(password)
  var postOptions = {'headers': {'Content-Type': 'application/x-www-form-urlencoded'}}
  vm.http.post(AUTH_URL, postBody, postOptions).then((response) => {
    // 响应成功回调
    // {"access_token":"96c41aee-a108-4e43-afec-ce7d9f3064be","token_type":"bearer","refresh_token":"dc7cbe78-46e7-428f-875c-3bff3b92ee22","expires_in":43199,"scope":"user_info"}
    var json = response.data
    parseToken(json)
    // 自动返回首页
    successCallback(response)
  }, (response) => {
    // 响应错误回调
    errorCallback(response)
  })
}

function parseToken (tokenJson) {
  // parse token and set the refresh timeout
  console.log(tokenJson)
  token = tokenJson['access_token']
  refreshToken = tokenJson['refresh_token']
  user = {name: tokenJson['name'], id: tokenJson['user_id']}
  console.log('user is ' + user.name + ', global: ' + vm.$auth)
  // 设置timeout，处理token过期自动刷新
  var seconds = tokenJson['expires_in'] - 300
  setRefreshTimer(seconds * 1000)
  console.log('刷新token设置：' + seconds + '秒后')
}

function getUser () {
  return user
}

function getToken () {
  return token
}

const Auth = {}
Auth.install = function (Vue, options) {
  console.log('Auth plug-in was called.')
  vm = Vue
  Vue.prototype.$auth = {
    login,
    getUser,
    getToken
  }

  // 设置vue-resource全局拦截器； 如果有token，则传递token给服务端
  vm.http.interceptors.push((request, next) => {
    if (token != null) {
      request.headers.set('Authorization', 'Bearer ' + token)
      console.log('set headers Authorization: ' + token)
      console.log(request.headers)
    }
    next((response) => {
      console.log(response.status)
      return response
    })
  })
}

export default Auth
