<template>
  <v-container>
    <v-card>
      <v-card-title class="d-flex justify-space-between align-center">
        <span>滑板車管理系統</span>
        <v-btn color="primary" @click="openAddDialog">新增車輛</v-btn>
      </v-card-title>
      <v-card-text>
        <v-row class="mb-4">
          <v-col cols="12" sm="6" md="3">
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

        <!-- 表格維持不變 -->
        <v-data-table
          :headers="headers"
          :items="scooters"
          :loading="isLoading"
          loading-text="正在載入資料..."
          no-data-text="沒有符合條件的資料"
          class="elevation-1"
        >
          <template v-slot:[`item.actions`]="{ item }">
            <v-icon size="small" class="me-2" @click="openEditDialog(item)">mdi-pencil</v-icon>
            <v-icon size="small" @click="openDeleteDialog(item)">mdi-delete</v-icon>
          </template>
        </v-data-table>
      </v-card-text>
    </v-card>

    <!-- 1. 使用我們建立的 ScooterForm 元件 -->
    <!-- :show="dialog" 將 dialog 的狀態傳給子元件 -->
    <!-- :title="dialogTitle" 將標題傳給子元件 -->
    <!-- :item="editedItem" 將要編輯的資料傳給子元件 -->
    <!-- @close="closeDialog" 監聽子元件觸發的 'close' 事件 -->
    <!-- @save="saveScooter" 監聽子元件觸發的 'save' 事件 -->
    <scooter-form
      :show="dialog"
      :title="dialogTitle"
      :item="editedItem"
      @close="closeDialog"
      @save="saveScooter"
    ></scooter-form>

    <!-- 2. 使用我們建立的 ConfirmDialog 元件 -->
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
import axios from 'axios';
// 3. 匯入新建立的子元件
import ScooterForm from '@/components/ScooterForm.vue';
import ConfirmDialog from '@/components/ConfirmDialog.vue';

export default {
  name: "ScooterManagement",
  //註冊子元件，這樣才能在 template 中使用
  components: {
    ScooterForm,
    ConfirmDialog,
  },
  data() {
    return {
      isLoading: true,
      dialog: false,       // 現在只需要一個 dialog 開關
      deleteDialog: false,
      dialogTitle: '',     // 用來動態設定 Dialog 的標題
      isEditMode: false,   // 用來判斷是新增還是編輯模式
      headers: [
        { title: '車牌號碼', key: 'licensePlate', align: 'start' },
        { title: '機車型號', key: 'model' },
        { title: 'CC數', key: 'cc' },
        { title: '車種', key: 'type' },
        { title: '日租費率', key: 'dailyRate' },
        { title: '狀態', key: 'status' },
        { title: '操作', key: 'actions', sortable: false },
      ],
      scooters: [],
      editedItem: {},      // 要傳給表單元件的物件
      itemToDelete: {},    // 要傳給確認框元件的物件
      filters: {
        type: null,
        cc: null,
        status: null,
        dailyRate: null,
      },
      debounceTimer: null,
    };
  },
  watch: {
    filters: {
      handler() {
        clearTimeout(this.debounceTimer);
        this.debounceTimer = setTimeout(() => {
          this.fetchScooters();
        }, 500);
      },
      deep: true,
    },
  },
  mounted() {
    this.fetchScooters();
  },
  methods: {
    fetchScooters() {
      this.isLoading = true;
      const activeFilters = Object.fromEntries(
        Object.entries(this.filters).filter(([, v]) => v != null && v !== '')
      );
      axios.get('/api/admin/getAllScooters', { params: activeFilters })
        .then(response => {
          this.scooters = response.data;
        })
        .catch(error => {
          console.error('獲取滑板車資料時發生錯誤:', error);
        })
        .finally(() => {
          this.isLoading = false;
        });
    },

    // 5. 簡化後的 Dialog 控制方法
    openAddDialog() {
      this.isEditMode = false;
      this.editedItem = {}; // 傳一個空物件給表單，代表是新增
      this.dialogTitle = '新增車輛';
      this.dialog = true;
    },
    openEditDialog(item) {
      this.isEditMode = true;
      this.editedItem = Object.assign({}, item); // 傳入該列的資料
      this.dialogTitle = '編輯車輛';
      this.dialog = true;
    },
    closeDialog() {
      this.dialog = false;
    },
    openDeleteDialog(item) {
      this.itemToDelete = Object.assign({}, item);
      this.deleteDialog = true;
    },
    closeDeleteDialog() {
      this.deleteDialog = false;
    },

    // 6. 統一的儲存方法，處理來自 ScooterForm 元件的 'save' 事件
    saveScooter(scooterData) {
      console.log('接收到儲存事件，資料:', scooterData);
      if (this.isEditMode) {
        // 更新
        axios.put(`/api/admin/scooters/${scooterData.scooterId}`, scooterData)
          .then(() => {
            console.log(`ID ${scooterData.scooterId} 更新成功`);
            this.fetchScooters(); // 重新整理列表
          })
          .catch(error => console.error('更新失敗:', error));
      } else {
        // 新增
        axios.post('/api/admin/scooters', scooterData)
          .then(() => {
            console.log('新增成功');
            this.fetchScooters(); // 重新整理列表
          })
          .catch(error => console.error('新增失敗:', error));
      }
      this.closeDialog(); // 關閉對話框
    },

    // 7. 刪除確認方法，處理來自 ConfirmDialog 元件的 'confirm' 事件
    deleteScooterConfirm() {
      console.log(`準備刪除 ID: ${this.itemToDelete.scooterId}`);
      axios.delete(`/api/admin/scooters/${this.itemToDelete.scooterId}`)
        .then(() => {
          console.log('刪除成功');
          this.fetchScooters(); // 重新整理列表
        })
        .catch(error => console.error('刪除失敗:', error));
      this.closeDeleteDialog(); // 關閉對話框
    },
  },
};
</script>

<style scoped>
.v-card-title {
  font-weight: bold;
}
</style>
