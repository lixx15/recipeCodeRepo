import axios from 'axios'

export default defineNuxtPlugin(nuxtApp => {
    const axiosInstance = axios.create({
        baseURL: 'http://localhost:15151',  // サーバーのベースURL
        withCredentials: true,             // クッキーを含める設定
    })

    // Nuxt アプリケーションに axios インスタンスを提供
    nuxtApp.provide('axios', axiosInstance)
})