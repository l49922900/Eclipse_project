import { createRouter, createWebHistory } from "vue-router";
import HomePage from "../views/HomePage.vue";
import ScooterManagement from "../views/ScooterManagement.vue";
import Login_page from "@/views/Login_page.vue";

//定義路由表
// 每個路由都是一個物件，包含 path (URL路徑) 和 component (要顯示的元件)
const routes = [
  {
    path: "/",
    name: "Home",
    component: HomePage,
  },
  {
    path: "/admin/scooters",
    name: "ScooterManagement",
    component: ScooterManagement,
  },
  {
    path: "/Login_page",
    name: "SLogin_page",
    component: Login_page,
  },
];

//建立 router 實例
const router = createRouter({
  history: createWebHistory(process.env.BASE_URL), // 使用 HTML5 History 模式
  routes, // 將我們定義的路由表傳入
});

export default router;
