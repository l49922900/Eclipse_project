    <template>
      <v-dialog :model-value="show" persistent max-width="500px">
        <v-card>
          <v-card-title>
            <span class="text-h5">編輯使用者</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12">
                  <v-text-field
                    label="使用者名稱"
                    :model-value="formData.username"
                    readonly
                    disabled
                  ></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-select
                    label="角色"
                    v-model="formData.role"
                    :items="['ROLE_USER', 'ROLE_ADMIN']"
                    required
                  ></v-select>
                </v-col>
                <v-col cols="12">
                  <v-switch
                    label="啟用帳號"
                    v-model="formData.enabled"
                    color="primary"
                  ></v-switch>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue-darken-1" variant="text" @click="$emit('close')">取消</v-btn>
            <v-btn color="blue-darken-1" variant="text" @click="$emit('save', formData)">儲存</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </template>
    
    <script>
    export default {
      name: 'UserForm',
      props: {
        show: {
          type: Boolean,
          default: false,
        },
        item: {
          type: Object,
          required: true,
        },
      },
      emits: ['close', 'save'],
      data() {
        return {
          formData: {},
        };
      },
      watch: {
        item: {
          handler(newItem) {
            this.formData = Object.assign({}, newItem);
          },
          immediate: true,
          deep: true,
        },
      },
    };
    </script>
    
