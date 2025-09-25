    <template>
      <v-container>
        <v-card>
          <v-card-title>
            會員管理
          </v-card-title>
          <v-card-text>
            
            <!--
            :headers="headers", :items="users", :loading="isLoading" 這幾行是 Vue.js 的屬性綁定 (Attribute Binding) 語法。
            用來將 <script> 標籤中 data() 裡的資料，傳遞給 <template> 標籤中的 HTML 元素或組件。
            --> 
            <v-data-table
              :headers="headers"
              :items="users"
              :loading="isLoading"
              loading-text="正在載入會員資料..."
              no-data-text="沒有可用的會員資料"
              class="elevation-1"
            >
              <!-- 我們可以加上一個 chip 來美化角色顯示 -->
              <template v-slot:item-role="{ item }">
                <v-chip :color="item.role === 'ROLE_ADMIN' ? 'red' : 'green'" dark>
                  {{ item.role }}
                </v-chip>
              </template>
            </v-data-table>
          </v-card-text>
        </v-card>
      </v-container>
    </template>
    
    <script>
    import axios from 'axios';
    
    export default {
      name: 'UserManagement',
      data() {
        return {
          isLoading: true,
          headers: [
            { title: 'ID', key: 'id', align: 'start' },
            { title: '使用者名稱', key: 'username' },
            { title: '電子郵件', key: 'email' },
            { title: '角色', key: 'role' },
            { title: '啟用狀態', key: 'enabled' },
            // { title: '操作', key: 'actions', sortable: false }, // 之後會加入
          ],
          users: [],
        };
      },
      mounted() {
        this.fetchUsers();
      },
      methods: {
        fetchUsers() {
          this.isLoading = true;
          axios.get('/api/admin/users')
            .then(response => {
              console.log("成功獲取會員資料:", response.data);
              this.users = response.data;
            })
            .catch(error => {
              console.error('獲取會員資料時發生錯誤:', error);
              // 可以在這裡加入錯誤提示
            })
            .finally(() => {
              this.isLoading = false;
            });
        },
      },
    };
    </script>
    
