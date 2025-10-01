const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,

  pluginOptions: {
    vuetify: {
      // https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vuetify-loader
    },
  },
  devServer: {
    proxy: {
      "/api": {
        target: "http://localhost:8086", // 確保這是您 Spring Boot 後端的正確端口
        changeOrigin: true,
      },
    },
  },
});
