<template>
  <!-- v-container 提供了一個有邊距的容器，讓內容不會貼著螢幕邊緣 -->
  <v-container>
    <!-- v-card 是一個卡片元件，讓我們的內容區塊看起來更立體 -->
    <v-card>
      <v-card-title> 摩托車租賃系統 </v-card-title>
      <v-card-text>

        <v-row class="mb-4">
          <v-col cols="12" sm="6" md="3">
            
            
            <!--items="['', 'scooter', 'manual']": 設定這個選單中可選的項目。第一個空字串 '' 通常用來代表「全部」或「不篩選」的選項。
                label="車型": 當沒有選擇任何項目時，顯示在輸入框內的提示文字。
                clearable: 在選單右側增加一個 "x" 小圖示，讓使用者可以一鍵清除目前的選擇。
                density="compact": 讓元件的垂直高度變小，使介面看起來更緊湊。-->
            <v-select
              v-model="filters.type"
              :items="['', 'scooter', 'manual']"
              label="車型"
              clearable
              density="compact"
            ></v-select>
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-select
              v-model="filters.cc"
              :items="['', '100', '125', '150', '200']"
              label="CC數"
              clearable
              density="compact"
            ></v-select>
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-select
              v-model="filters.status"
              :items="['', 'available', 'maintenance']"
              label="車輛狀態"
              clearable
              density="compact"
            ></v-select>
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-select
              v-model="filters.dailyRate"
              :items="['', '300', '400', '500', '600']"
              label="日租價格"
              clearable
              density="compact"
            ></v-select>
          </v-col>
        </v-row>
        
        
        
        <!-- 
            v-data-table 是 Vuetify 強大的表格元件
            :headers="headers":這是一個動態綁定（:是簡寫）屬性，它將 data 屬性中的 headers 陣列傳遞給表格，用來定義表格的欄位名稱。 
            :items="scooters"：將 data 屬性中的 scooters 陣列傳遞給表格，這是要顯示的實際資料
            :loading="isLoading"：將 data 屬性中的 isLoading 布林值傳遞給表格。當 isLoading 為 true 時，表格會自動顯示一個載入中的動畫。
            loading-text="正在載入資料..." 和 no-data-text="沒有可用的資料"：這些屬性讓你可以自訂在不同情況下顯示的提示文字
            -->
        <v-data-table
          :headers="headers"
          :items="scooters"
          :loading="isLoading"
          loading-text="正在載入資料..."
          no-data-text="沒有可用的資料"
          class="elevation-1"
        >
          <template  v-slot:[`item.actions`]="{ item }">
            <!--針對 headers 中 key 為 actions 的那一欄，定義每一列要顯示的內容。{ item } 提供了該列的完整資料物件，讓你可以在這個模板中使用。-->
            <v-icon size="small" class="me-2" @click="openEditDialog(item)">
              mdi-pencil
            </v-icon>
            <v-icon size="small" @click="openDeleteDialog(item)">
              mdi-delete
            </v-icon>
          </template>
        </v-data-table>
      </v-card-text>
    </v-card>

    <v-dialog v-model="addDialog" persistent max-width="600px">
      <!--:v-model="addDialog" 將對話框的開關狀態與 data 裡的 addDialog 變數雙向綁定。當 addDialog 為 true 時，對話框會顯示；為 false 時則隱藏。-->
      <!--v-model:用於實現雙向資料綁定-->
      <v-card>
        <v-card-title>
          <span class="text-h5">新增車輛</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  label="車牌號碼*"
                  v-model="newScooter.licensePlate"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  label="機車型號*"
                  v-model="newScooter.model"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6">
                <v-select
                  :items="['100', '125', '150', '200']"
                  label="引擎排氣量*"
                  v-model="newScooter.cc"
                  required
                ></v-select>
              </v-col>
              <v-col cols="12" sm="6">
                <v-select
                  :items="['scooter', 'manual']"
                  label="車種*"
                  v-model="newScooter.type"
                  required
                ></v-select>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  label="日租費率*"
                  v-model.number="newScooter.dailyRate"
                  type="number"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  label="車況描述"
                  v-model="newScooter.conditionNote"
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  label="上次保養日期"
                  v-model="newScooter.lastMaintenanceDate"
                  type="date"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
          <small>*表示必填欄位</small>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue-darken-1" variant="text" @click="closeAddDialog"
            >取消</v-btn
          >
          <v-btn color="blue-darken-1" variant="text" @click="addScooter"
            >儲存</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="deleteDialog" max-width="500px">
      <v-card>
        <v-card-title class="text-h5">確定要刪除這輛車嗎？</v-card-title>
        <v-card-text>
          車牌號碼:{{ itemToDelete.licensePlate }}<br />
          <!--{{ }} 是 Vue 的模板語法，用來顯示資料-->
          此操作無法復原。
        </v-card-text>
        <v-card-actions
          ><!--<v-card-actions>：這是一個放置按鈕的區域，通常位於卡片的底部。-->
          <v-spacer></v-spacer>
          <v-btn color="blue-darken-1" variant="text" @click="closeDeleteDialog"
            >取消</v-btn
          >
          <v-btn
            color="red-darken-1"
            variant="text"
            @click="deleteScooterConfirm"
            >確認刪除</v-btn
          >
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import axios from "axios"; //這是一個用於發送 HTTP 請求（例如向後端 API 獲取資料）的常用工具
export default {
  name: "ScooterList",
  data() {
    //返回一個物件，其中包含了元件的響應式資料
    return {
      //data 必須是一個函式 (function)，並且這個函式要 return 一個物件。這是為了確保每個元件實例都有自己獨立的資料複本，不會互相干擾
      isLoading: true,
      addDialog: false, //用於控制對話框的顯示與否
      editDialog: false,
      deleteDialog: false,
      headers: [
        { title: "車牌號碼", key: "licensePlate", align: "start" },
        { title: "機車型號", key: "model" },
        { title: "引擎排氣量", key: "cc" },
        { title: "車種", key: "type" },
        { title: "日租費率", key: "dailyRate" },
        { title: "車況描述", key: "conditionNote" },
        { title: "上次保養日期", key: "lastMaintenanceDate" },
        //{title:'車輛圖片',key:'addImageFile'}
        { title: '操作', key: 'actions', sortable: false },
      ],
      scooters: [],
      newScooter: {
        licensePlate: "",
        model: "",
        cc: null,
        type: "",
        dailyRate: null,
        conditionNote: "",
        lastMaintenanceDate: null,
      },
      editedItem: {}, //用於儲存正在編輯的車輛資料
      itemToDelete: {}, //用於儲存準備刪除的車輛資料
    };
  },
  mounted() {
    // Vue 元件的一個生命週期鉤子函式。當元件被「掛載」到網頁上時，mounted 裡的程式碼會被自動執行一次
    console.log("ScooterList 元件已掛載，準備獲取資料...");
    this.fetchScooters();
  },
  methods: {
    fetchScooters() {
      this.isLoading = true; // 開始請求前，顯示 loading
      axios
        .get("/api/scooters")
        .then((response) => {
          //當請求成功時執行。它將後端返回的資料 (response.data) 賦值給 this.scooters，從而觸發表格更新。
          console.log("成功獲取資料:", response.data);
          this.scooters = response.data;
        })
        .catch((error) => {
          console.error("獲取滑板車資料時發生錯誤:", error);
        })
        .finally(() => {
          this.isLoading = false; // 無論成功或失敗，請求結束後都隱藏 loading
        });
    },
    openAddDialog() {
      this.addDialog = true;
    },
    closeAddDialog() {
      this.addDialog = false;
      // 清空表單
      this.newScooter = {
        licensePlate: "",
        model: "",
        cc: null,
        type: "",
        dailyRate: null,
        conditionNote: "",
        lastMaintenanceDate: null,
      };
    },
    addScooter() {
      // 發送 POST 請求到後端
      axios
        .post("/api/scooters", this.newScooter)
        .then((response) => {
          console.log("新增成功:", response.data);
          // 重新獲取一次列表，或直接將新資料推進陣列
          this.fetchScooters(); //如果請求成功，會呼叫 fetchScooters() 重新整理表格，並呼叫 closeAddDialog() 關閉對話框。
          this.closeAddDialog(); // 關閉 Dialog
        })
        .catch((error) => {
          console.error("新增滑板車時發生錯誤:", error);
          // 可以在這裡加入錯誤提示給使用者
        });
    },
    openEditDialog(item) {
      this.editedItem = Object.assign({}, item); // 複製一份物件，避免直接修改原始資料
      this.editDialog = true;
    },
    closeEditDialog() {
      this.editDialog = false;
    },
    updateScooter() {
      axios
        .put(`/api/scooter/${this.editedItem.scooterId}`, this.editedItem)
        .then((response) => {
          console.log("更新成功:", response.data);
          this.fetchScooters();
          this.closeEditDialog();
        })
        .catch((error) => {
          console.error("更新滑板車時發生錯誤:", error);
        });
    },
    openDeleteDialog(item) {
      this.itemToDelete = item;
      this.deleteDialog = true;
    },
    closeDeleteDialog() {
      this.deleteDialog = false;
    },
    deleteScooterConfirm() {
      axios
        .delete(`/api/scooters/${this.itemToDelete.scooterId}`)
        .then(() => {
          console.log("刪除成功");
          this.fetchScooters();
          this.closeDeleteDialog();
        })
        .catch((error) => {
          console.error("刪除滑板車時發生錯誤:", error);
        });
    },
  },
};
</script>

<style scoped>
.v-card-title {
  font-weight: bold;
}
</style>
