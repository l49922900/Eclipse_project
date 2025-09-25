import { createRouter, createWebHistory } from "vue-router";
import HomePage from "../views/HomePage.vue";
import ScooterManagement from "../views/ScooterManagement.vue";
import LoginPage from "@/views/LoginPage.vue";
import UserManagement from "@/views/UserManagement.vue";
import { useAuthStore } from "../stores/auth";

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
    meta: { requiresAuth: true }, // 標記這個頁面需要登入
  },
  {
    path: "/LoginPage",
    name: "LoginPage",
    component: LoginPage,
  },
  {
    path: "/admin/UserManagement",
    name: "UserManagement",
    component: UserManagement,
    meta: { requiresAuth: true }, // 標記這個頁面需要登入
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});


router.beforeEach((to, from, next) => {
  // 在路由守衛中，不能在頂層使用 Pinia，但可以在函式內部取得 store
  const authStore = useAuthStore();
  const requiresAuth = to.matched.some((record) => record.meta.requiresAuth);

  console.log(
    `Navigating to: ${to.path}, Requires Auth: ${requiresAuth}, Is Authenticated: ${authStore.isAuthenticated}`
  );

  if (requiresAuth && !authStore.isAuthenticated) {
    // 如果頁面需要登入但使用者未登入，導向登入頁
    console.log("未登入，重新導向至 /LoginPage");
    next({ name: "LoginPage" }); // 使用 name 進行導向更安全
  } else {
    // 否則正常進入
    next();
  }
});

export default router;
