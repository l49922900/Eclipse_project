    <template>
      <v-container class="fill-height" fluid>
        <v-row align="center" justify="center">
          <v-col cols="12" sm="8" md="4">
            <v-card class="elevation-12">
              <v-toolbar color="primary" dark flat>
                <v-toolbar-title>登入</v-toolbar-title>
              </v-toolbar>
              <v-card-text>
                <v-form @submit.prevent="handleLogin">
                  <v-text-field
                    label="使用者名稱"
                    name="username"
                    prepend-icon="mdi-account"
                    type="text"
                    v-model="username"
                  ></v-text-field>
                  <v-text-field
                    id="password"
                    label="密碼"
                    name="password"
                    prepend-icon="mdi-lock"
                    type="password"
                    v-model="password"
                  ></v-text-field>
                   <v-alert v-if="error" type="error" dense>{{ error }}</v-alert>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" @click="handleLogin" :loading="loading">登入</v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </template>
    
    <script>
    import { useAuthStore } from '@/stores/auth';
    
    export default {
      name: 'Login_page',
      data() {
        return {
          username: '',
          password: '',
          loading: false,
          error: null,
        };
      },
      methods: {
        async handleLogin() {
          this.loading = true;
          this.error = null;
          const authStore = useAuthStore();
          try {
            await authStore.login({
              username: this.username,
              password: this.password,
            });
          } catch (err) {
            this.error = '帳號或密碼錯誤，請再試一次。';
          } finally {
            this.loading = false;
          }
        },
      },
    };
    </script>
    
