import { createApp } from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'
import router from './router' // 匯入我們的 router 設定

loadFonts()

createApp(App)
  .use(vuetify)
  .use(router) // 告訴 Vue App 使用 router
  .mount("#app");
