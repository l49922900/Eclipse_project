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


    <!-- 使用 ScooterForm 元件 -->
    <scooter-form
      :show="dialog"
      :title="dialogTitle"
      :item="editedItem"
      @close="closeDialog"
      @save="saveScooter"
    ></scooter-form>

    <!-- 使用 ConfirmDialog 元件 -->
    <confirm-dialog
      :show="deleteDialog"
      title="確認刪除"
      :message="`確定要刪除車牌為 ${itemToDelete.licensePlate} 的車輛嗎？此操作無法復原。`"
      @cancel="closeDeleteDialog"
      @confirm="deleteScooterConfirm"
    ></confirm-dialog>
   
  </v-container>
</template>

<script>
import axios from "axios"; //這是一個用於發送 HTTP 請求（例如向後端 API 獲取資料）的常用工具
import ScooterForm from '../components/ScooterForm.vue';
import ConfirmDialog from '../components/ConfirmDialog.vue';
export default {
  name: "ScooterManagement",
        components: {
        ScooterForm,
        ConfirmDialog,
      },
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

      filters: { //用一個物件來存放所有的篩選條件
        type: null,
        cc: null,
        status: null,
        dailyRate: null,
      },

      debounceTimer: null, //用於 Debounce (防抖) 的計時器
    };
  },

  //使用 watch 來監聽 filters 物件的變化
  watch: {
    filters: {
      handler() {
        // Debounce 邏輯：
        // 當 filters 改變時，先清除上一個計時器
        clearTimeout(this.debounceTimer);
        // 然後設定一個新的計時器，500 毫秒後才執行 fetchScooters
        // 如果使用者在這 500 毫秒內又改變了篩選條件，上一個計時器就會被清除，重新計時
        // 這樣可以避免使用者快速操作時，發送大量的 API 請求
        this.debounceTimer = setTimeout(() => {
          this.fetchScooters();
        }, 500); // 延遲 500 毫秒
      },
      deep: true, // deep: true 表示要深度監聽物件內部屬性的變化
    },
  },


  mounted() {
    // Vue 元件的一個生命週期鉤子函式。當元件被「掛載」到網頁上時，mounted 裡的程式碼會被自動執行一次
    console.log("ScooterList 元件已掛載，準備獲取資料...");
    this.fetchScooters();
  },
  methods: {
    fetchScooters() {
      this.isLoading = true; // 開始請求前，顯示 loading
      console.log('Fetching scooters with filters:', this.filters);
      
      const activeFilters = Object.fromEntries(
        Object.entries(this.filters).filter(([, v]) => v != null && v !== '')
      );

      axios.get('/api/scooters', { params: activeFilters }) // axios 會自動將 params 物件轉換成 URL 查詢字串
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
      this.isEditMode = false;
      this.editedItem = {};
      this.dialogTitle = '新增車輛';
      this.dialog = true;
    },

    openEditDialog(item) {
      this.editedItem = Object.assign({}, item); // 複製一份物件，避免直接修改原始資料
      this.editDialog = true;
    },

    closeDialog() {
      this.dialog = false;
    },

    openDeleteDialog(item) {
      this.itemToDelete = item;
      this.deleteDialog = true;
    },
    closeDeleteDialog() {
      this.deleteDialog = false;
    },

    saveScooter(scooterData) {
      console.log('接收到儲存事件，資料:', scooterData);
      const promise = this.isEditMode
        ? axios.put(`/api/scooters/${scooterData.scooterId}`, scooterData)
        : axios.post('/api/scooters', scooterData);

      promise.then(() => {
        console.log('儲存成功');
        this.fetchScooters();
      }).catch(error => {
        console.error('儲存失敗:', error);
      }).finally(() => {
        this.closeDialog();
      });
    },
    

    deleteScooterConfirm() {
      console.log(`準備刪除 ID: ${this.itemToDelete.scooterId}`);
      axios.delete(`/api/scooters/${this.itemToDelete.scooterId}`)
        .then(() => {
          console.log('刪除成功');
          this.fetchScooters();
        })
        .catch(error => {
          console.error('刪除失敗:', error);
        })
        .finally(() => {
          this.closeDeleteDialog();
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
