// customComponentPlugin.js
import CustomComponent from '@/views/Test.vue'; // 导入自定义组件

export default {
  install(app) {
    app.component('custom', CustomComponent); // 全局注册自定义组件
  }
}