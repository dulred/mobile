import { createApp } from 'vue'
import { createPinia } from 'pinia'
import router from '@/router'
import App from '@/App.vue'
import CustomComponentPlugin from '@/plugins/customComponentPlugin'; // 导入自定义组件插件

// 自适应html标签px值->用于rem
import 'lib-flexible/flexible'
// 全局样式
import '@/assets/css/main.css'
// vant toast函数组件样式
import 'vant/es/toast/style';


// 初始化 Vue 实例
const app = createApp(App)
// 注册路由
app.use(router)

// 安装自定义组件插件
app.use(CustomComponentPlugin);

// 注册 store
app.use(createPinia())
// 挂载 Vue 实例
app.mount('#app')