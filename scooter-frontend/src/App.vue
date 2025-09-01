    <template>
      <!--Vuetify 應用的根元件。所有的 Vuetify 元件都必須包含在 v-app 裡面，它負責提供應用程式的基礎樣式和結構，例如設定字體、背景顏色和佈局等。-->
      <v-app>
        <!-- v-main 是放置主要內容的地方 -->
        <v-main>
          <ScooterList /><!--自訂的子元件-->
        </v-main>
      </v-app>
    </template>

    <script>
    import ScooterList from './components/ScooterList.vue'

    export default {
      name: 'App', //App 元件自己不做任何具體的「工作」，它就像一個經理，負責把不同的部門（子元件）組織起來，並安排它們在正確的位置上協同運作。
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
    