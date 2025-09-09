    <template>
      <v-app-bar app color="primary" dark>
        <v-toolbar-title>MyGO!! 租車</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- router-link 是 Vue Router 提供的元件，用來取代 <a> 標籤進行頁面導航 -->
        <!-- 'to' 屬性指定了目標路徑，對應在 router/index.js 中定義的 path -->
        <v-btn to="/" text>首頁</v-btn>
        <!-- v-if 會根據 authStore.isAuthenticated 的值 (true/false) 來決定是否渲染這個按鈕 -->
        <v-btn v-if="authStore.isAuthenticated" to="/admin/scooters" text>車輛管理</v-btn>

        <v-spacer></v-spacer>

        <!-- 如果使用者"未"登入，就顯示"登入"按鈕 -->
        <v-btn v-if="!authStore.isAuthenticated" to = "/login" text>登入</v-btn>


        <!-- 如果使用者"已"登入，就顯示"登出"按鈕 -->
        <!-- @click="logout" 會在按鈕被點擊時，呼叫我們在 script 中定義的 logout 方法 -->
         <v-btn v-if="authStore.isAuthenticated" @click="logout" text>登出</v-btn>

      </v-app-bar>
    </template>
    
    <script>

    import { useAuthStore } from '@/stores/auth';

    export default {
      name: 'NavBar',
    };
    </script>
    
    <style scoped>
    .v-btn {
      margin: 0 8px;
    }
    </style>
    
