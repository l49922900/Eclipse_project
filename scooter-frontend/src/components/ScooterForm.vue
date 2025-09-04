    <template>
      <!-- persistent 屬性讓使用者無法透過點擊外部來關閉 Dialog -->
      <v-dialog :model-value="show" persistent max-width="600px">
        <v-card>
          <v-card-title>
            <!-- 標題由 prop 傳入，可以是 "新增車輛" 或 "編輯車輛" -->
            <span class="text-h5">{{ title }}</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <!-- 表單欄位與 ScooterList 中的相同 -->
                <v-col cols="12">
                  <v-text-field label="車牌號碼*" v-model="formData.licensePlate" required></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="機車型號*" v-model="formData.model" required></v-text-field>
                </v-col>
                <v-col cols="12" sm="6">
                  <v-select label="CC數*" v-model="formData.cc" :items="['100', '125', '150', '200']" required></v-select>
                </v-col>
                <v-col cols="12" sm="6">
                   <v-select label="車種*" v-model="formData.type" :items="['scooter', 'manual']" required></v-select>
                </v-col>
                 <v-col cols="12" sm="6">
                  <v-select label="日租費率*" v-model="formData.dailyRate" :items="['300', '400', '500', '600']" required></v-select>
                </v-col>
                 <v-col cols="12" sm="6">
                  <v-select label="狀態*" v-model="formData.status" :items="['available', 'maintenance']" required></v-select>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <!-- 點擊取消時，觸發 'close' 事件 -->
            <v-btn color="blue-darken-1" variant="text" @click="$emit('close')">取消</v-btn>
            <!-- 點擊儲存時，觸發 'save' 事件，並將表單資料作為參數傳遞出去 -->
            <v-btn color="blue-darken-1" variant="text" @click="$emit('save', formData)">儲存</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </template>
    
    <script>
    export default {
      name: 'ScooterForm',
      // props 用來接收從父元件傳遞下來的資料
      props: {
        // 'show' 是一個布林值，用來控制 Dialog 的顯示與否
        show: {
          type: Boolean,
          default: false,
        },
        // 'title' 是 Dialog 的標題
        title: {
          type: String,
          required: true,
        },
        // 'item' 是要編輯的車輛物件，新增時會是空物件
        item: {
          type: Object,
          required: true,
        },
      },
      // emits 用來聲明這個元件會觸發哪些自訂事件
      emits: ['close', 'save'],
      data() {
        return {
          // formData 是表單內部的資料狀態
          // 我們不直接修改 prop 'item'，而是建立一個複本
          formData: {},
        };
      },
      // watch 用來監聽 prop 'item' 的變化
      watch: {
        // 當父元件傳入的 item 改變時 (例如點擊不同車輛的編輯按鈕)
        // 就更新內部的 formData
        item: {
          handler(newItem) {
            // 使用 Object.assign 複製一份，避免直接修改 props
            this.formData = Object.assign({}, newItem);
          },
          immediate: true, // immediate: true 讓 watch 在元件建立時就立刻執行一次
          deep: true,
        }
      }
    };
    </script>
    
