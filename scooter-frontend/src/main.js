import { createApp } from "vue";
import App from "./App.vue";
import vuetify from "./plugins/vuetify";
import { loadFonts } from "./plugins/webfontloader";
import router from "./router"; // 保證這裡 import 的 router 是單一來源
import { createPinia } from "pinia";
import axios from "axios";

loadFonts();

const app = createApp(App);
const pinia = createPinia();

// 把 router 注入到 pinia plugin（若你需要 this.router）
pinia.use(({ store }) => {
  store.router = router;
});

// 一定要先 use Pinia，再 use Router（避免路由守衛使用到未啟動的 Pinia）
app.use(vuetify);
app.use(pinia);
app.use(router);

// 暫時暴露於 window 便於 debug（可在 debug 解完後移除）
window.__APP__ = app;
window.__ROUTER__ = router;

app.mount("#app");

// 設置 axios 初始 header（如果有 token）
const token = localStorage.getItem("token");
if (token) {
  axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
  console.log("App Initialized: Token found and set in Axios headers.");
}

console.log("App mounted with router:", !!window.__ROUTER__);