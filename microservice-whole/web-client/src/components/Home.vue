<template>
  <div class="hello">
    <h1>Home</h1>
    <div v-if="user">
      <h3>Welcome {{ user.name }} #{{ user.id }}</h3>
      <div class="token">
        <h4>Your Token:</h4>
        <div>{{token}}</div>
      </div>
      <ul>
        <li><a href="javascript:;" v-on:click="queryOrder()">查询订单信息</a></li>
        <li><a href="javascript:;" v-on:click="queryInventories()">查看商品列表</a></li>
      </ul>
    </div>
    <div v-else>
      <button v-on:click="gotoLogin">你尚未登录，请登录</button>
    </div>
    
    <div class="data" v-if="inventories != null && tabIndex==1">
      <h2>商品信息</h2>
      <div v-for="item in inventories">
        <div class="item">
          {{item.name}}
        </div>
      </div>
    </div>
    <div class="data" v-if="order != null && tabIndex==2">
      <h2>订单信息</h2>
      <p><span>ID:</span>{{ order.id }}</p>
      <p><span>地址:</span>{{ order.consigneeAddress.province }} {{ order.consigneeAddress.city }} {{ order.consigneeAddress.district }} {{ order.consigneeAddress.address }}</p>
    </div>


    <ul class="bottomLinks">
      <li><router-link :to="{ name: 'Login', params: { id: 123 }}" >Login</router-link></li>
      <li v-on:click="gotoLogin">跳转到Login(Javascript 跳转的例子)</li>
      <li v-on:click="gotoFooAfter2Seconds">跳转到Foo(显示Loading)</li>
      <li v-on:click="showMessage">显示Toast Message</li>
    </ul>

  </div>
</template>

<script>
export default {
  name: 'Home',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      token: this.$auth.getToken(),
      user: this.$auth.getUser(),
      inventories: null,
      order: null,
      tabIndex: 1
    }
  },
  created () {
    // 组件加载完毕会调用此函数
    console.log('created called')
    // CommonUtil.setTitle('首页')
    // this.$store.commit('setShowBottomMenu', true) // 显示底部菜单
    // this.$store.commit('setTab', 'home') // 设置底部高亮菜单为setting
  },
  methods: {
    gotoLogin () {
      // JavaScript跳转到Login页面，并传递参数id=457；除了router.push外还可以router.replace
      this.$router.push({name: 'Login', params: { from: 'home' }})
    },
    gotoHome () {
      this.$router.push({ path: '/' })
    },
    gotoFooAfter2Seconds () {
      // 这是一个显示loading的例子，故意延时了2s，以便看清loading
      // this.$store.commit('setShowLoadding', true) // 显示Loading
      // window.setTimeout(this.gotoFoo, 2000)
    },
    showMessage () {
      // this.$store.commit('showToast', 'Hello world. hello again, Hello, again and again.')
    },
    queryInventories () {
      // Authorization': 'Bearer ' + token， Auth模块拦截器内设置Authorization头了，这里不用设了
      // var postOptions = {'headers': {'Authorization': 'Bearer ' + this.$auth.getToken()}}
      this.$http.get('http://localhost:8012/stockservice/inventories').then((response) => {
        var json = response.data
        console.log(json)
        this.$data.inventories = json
        this.$data.tabIndex = 1
      }, (response) => {
        // 响应错误回调
        this.$data.msg = (response.status + ' --- ' + response.body + '---' + response.text())
      })
    },
    queryOrder () {
      // Authorization': 'Bearer ' + token
      // var postOptions = {'headers': {'Authorization': 'Bearer ' + this.$auth.getToken()}}
      this.$http.get('http://localhost:8011/orderservice/orders/2').then((response) => {
        var json = response.data
        console.log(json)
        this.$data.order = json
        this.$data.tabIndex = 2
      }, (response) => {
        // 响应错误回调
        this.$data.msg = (response.status + ' --- ' + response.body + '---' + response.text())
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {font-weight: normal;}
ul {list-style-type: none; padding: 0;}
li {display: inline-block; margin: 0 10px;}
a { color: #42b983; cursor:pointer;}

div.token{font-size:10px; border:1px solid #DEDEDE; margin: 5px 50px; word-break:break-all; text-align: left;}
div.token h4{font-size:12px; background-color:#DEDEDE; margin:0px; padding:5px; text-align:left;}

div.data{margin: 5px 50px; text-align:left;}
div.data h2{text-align:left;}

.bottomLinks{margin-top:50px;}
</style>
