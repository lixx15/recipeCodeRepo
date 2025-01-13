<template>
    <nav>
      <ul>
        <li><nuxt-link to="/">Home</nuxt-link></li>
        <li><nuxt-link to="/recipes">レシピ</nuxt-link></li>
        <li><nuxt-link to="/tags">タッグ検索</nuxt-link></li>
        <li><nuxt-link to="/my-page">マイページ</nuxt-link></li>
      </ul>
      <div v-if="userInfo">
        welcome, {{ userInfo.name }}!
      </div>
      <button v-else @click="login">
        Login
      </button>
    </nav>
  </template>
  
  <script>
  export default {
    methods: {
      login() {
        // login
      }
    }
  }
  </script>