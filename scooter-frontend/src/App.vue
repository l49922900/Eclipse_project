    <template>
      <v-app>
        <!-- 1. 載入導覽列元件 -->
        <nav-bar />
    
        <!-- 2. v-main 是 Vuetify 的主要內容區 -->
        <v-main>
          <!-- Vue Router 會根據目前的 URL，將對應的元件渲染在這個位置 -->
          <router-view />
        </v-main>
      </v-app>
    </template>
    
    <script>
    // 4. 匯入導覽列元件
    import NavBar from './components/NavBar.vue';
    
    export default {
      name: 'App',
      // 5. 註冊導覽列元件
      components: {
        NavBar,
      },
    };
    </script>