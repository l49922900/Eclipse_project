import { defineStore } from "pinia";
import axios from "axios";
import { useRouter } from "vue-router";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    token: localStorage.getItem("token") || null,
    user: null,
  }),

  getters: {
    isAuthenticated: (state) => !!state.token,
  },

  actions: {
    async login(credentials) {
      try {
        console.log("正在嘗試登入，使用者名稱:", credentials.username);
        const response = await axios.post("/api/auth/login", credentials);
        const token = response.data.token;

        if (token) {
          this.token = token;
          localStorage.setItem("token", token);
          // 設定 Axios 的預設 Header
          axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
          console.log("登入成功，取得 Token:", token);
          // 登入成功後跳轉
          this.router.push("/admin/scooters");
        }
      } catch (error) {
        console.error("登入失敗:", error);
        // 可以在這裡處理錯誤，例如顯示錯誤訊息
        throw error; // 將錯誤拋出，讓元件可以捕捉到
      }
    },

    logout() {
      console.log("執行登出...");
      this.token = null;
      this.user = null;
      localStorage.removeItem("token");
      // 移除 Axios 的預設 Header
      delete axios.defaults.headers.common["Authorization"];
      // 登出後跳轉回首頁
      this.router.push("/");
    },
  },
});
