    <template>
      <v-app-bar app color="primary" dark>
        <v-toolbar-title>MyGO!! 租車</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- router-link 是 Vue Router 提供的元件，用來取代 <a> 標籤進行頁面導航 -->
        <!-- 'to' 屬性指定了目標路徑，對應在 router/index.js 中定義的 path -->
        <v-btn to="/" text>首頁</v-btn>
        <v-btn to="/admin/scooters" text>車輛管理</v-btn>
      </v-app-bar>
    </template>
    
    <script>
    export default {
      name: 'NavBar',
    };
    </script>
    
    <style scoped>
    .v-btn {
      margin: 0 8px;
    }
    </style>
    
