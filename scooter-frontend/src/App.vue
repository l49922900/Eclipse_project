    <template>
      <!-- v-app 是 Vuetify 應用的根元件，提供了一個基礎的 App 結構 -->
      <v-app>
        <!-- v-main 是放置主要內容的地方 -->
        <v-main>
          <ScooterList />
        </v-main>
      </v-app>
    </template>

    <script>
    import ScooterList from './components/ScooterList.vue'

    export default {
      name: 'App',
      components: {
        ScooterList
      }
    }
    </script>

    <style>
    body {
      font-family: 'Roboto', sans-serif; /* Vuetify 推薦的字體 */
    }
    </style>
    