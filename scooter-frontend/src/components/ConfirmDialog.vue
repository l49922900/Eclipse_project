<template>
  <v-dialog :model-value="show" persistent max-width="450px">
    <v-card>
      <v-card-title class="text-h5 font-weight-bold">{{ title }}</v-card-title>
      <v-card-text>
        {{ message }}
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="grey-darken-1" variant="text" @click="$emit('cancel')">取消</v-btn>
        <v-btn color="red-darken-1" variant="text" @click="$emit('confirm')">確認</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: 'ConfirmDialog',
  // props 用來接收從父元件傳遞下來的資料
  props: {
    show: {
      type: Boolean,
      default: false,
    },
    title: {
      type: String,
      required: true,
    },
    message: {
      type: String,
      required: true,
    },
  },
  // emits 聲明此元件會觸發哪些自訂事件
  emits: ['cancel', 'confirm'],
};
</script>