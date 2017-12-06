<script>
let AUTH_URL = 'http://localhost:8009/auth/oauth/token'
var token = ''
var refreshToken = null
var user
var refreshTimer = null

function setRefreshTimer (seconds) {
  if (refreshTimer != null) {
    window.clearTimout(refreshTimer)
  }
  refreshTimer = window.setTimeout(seconds, function () {
    // 刷新token的代码
    var postBody = 'refresh_token=' + escape(refreshToken) + 'grant_type=refresh_token'
    var postOptions = {'headers': {'Content-Type': 'application/x-www-form-urlencoded'}}
    this.$http.post(AUTH_URL, postBody, postOptions).then((response) => {
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
  })
}

function login (userName, password) {
  // login
  var postBody = 'grant_type=password&client_id=rs1&username=' + escape(this.$data.username) + '&password=' + escape(this.$data.password)
  var postOptions = {'headers': {'Content-Type': 'application/x-www-form-urlencoded'}}
  this.$http.post(this.GLOBAL.AUTH_URL, postBody, postOptions).then((response) => {
    // 响应成功回调
    // {"access_token":"96c41aee-a108-4e43-afec-ce7d9f3064be","token_type":"bearer","refresh_token":"dc7cbe78-46e7-428f-875c-3bff3b92ee22","expires_in":43199,"scope":"user_info"}
    var json = response.data
    parseToken(json)
    // TODO: 设置timeout，处理token过期自动刷新
    // 自动返回首页
    this.$router.push({name: 'Home', params: { from: 'login' }})
  }, (response) => {
    // 响应错误回调
    this.$data.msg = (response.status + ' --- ' + response.body + '---' + response.text())
  })
}

function parseToken (tokenJson) {
  // parse token and set the refresh timeout
  console.log(tokenJson)
  token = tokenJson['access_token']
  refreshToken = tokenJson['refresh_token']
  user = {name: tokenJson['name'], id: tokenJson['id']}
  var seconds = tokenJson['expires_in'] - 600
  setRefreshTimer(seconds)
}

export default{
  AUTH_URL,
  token,
  refreshToken,
  user,
  login
}
</script>
