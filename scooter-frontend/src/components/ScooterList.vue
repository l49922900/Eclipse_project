<template>
      <!-- v-container 提供了一個有邊距的容器，讓內容不會貼著螢幕邊緣 -->
      <v-container>
        <!-- v-card 是一個卡片元件，讓我們的內容區塊看起來更立體 -->
        <v-card>
          <v-card-title>
            滑板車管理系統
          </v-card-title>
          <v-card-text>
            <!-- v-data-table 是 Vuetify 強大的表格元件 -->
            <v-data-table
              :headers="headers"
              :items="scooters"
              :loading="isLoading"
              loading-text="正在載入資料..."
              no-data-text="沒有可用的資料"
              class="elevation-1"
            >
            </v-data-table>
          </v-card-text>
        </v-card>
      </v-container>
    </template>

    <script>
        import axios from 'axios';
        export default{
            name: "ScooterList",
            data(){
                isLoading:true,
                headers:[
                    { title: '車牌號碼', value: 'licensePlate', align: 'start' },
                    { title: '機車型號', value: 'model' },
                    {title:'引擎排氣量',value:'cc'},
                    { title: '狀態', value: 'status' },
                    { title: '電量 (%)', value: 'batteryLevel' },
                    { title: '位置', value: 'location' },
                ],
                scooters:[]
            }
        }

    </script>