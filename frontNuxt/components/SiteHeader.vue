<!-- components/Header.vue -->
<template>
  <div class="header-container">
    <div class="logo">cookp@d</div>
    <div class="nav-links">
      <template v-if="isLoggedIn">
        <span>こんにちは, {{ loggedInUser.userName }}さん</span>
        <button @click="logout">ログアウト</button>
      </template>
      <template v-else>
        <router-link to="/login/login">ログイン</router-link>
        <router-link to="/account/register">新規登録</router-link>
      </template>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

// ✅ State 管理
const isLoggedIn = ref(false)
const loggedInUser = ref({})

// ✅ Cookieを使ってセッション確認
const getCookie = (name) => {
const match = document.cookie.match('(^|;)\\s*' + name + '\\s*=\\s*([^;]+)')
return match ? match.pop() : ''
}

// ✅ 初回読み込み時にクッキー確認
onMounted(() => {
const userName = getCookie('userName')
if (userName) {
  loggedInUser.value.userName = userName
  isLoggedIn.value = true
}
})

// ✅ ログアウト処理
const logout = async () => {
try {
  await fetch('http://localhost:15151/recipe_cite/api/auth/logout', { 
    method: 'POST', 
    credentials: 'include' 
  })
  alert('ログアウトしました')
  isLoggedIn.value = false
  document.cookie = 'userName=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;' // ✅ クッキー削除
  window.location.href = 'login/login'
} catch (error) {
  console.error('ログアウトエラー:', error)
}
}
</script>

<style scoped>
.header-container {
  display: flex;
  justify-content: space-between;
  padding: 10px 20px;
  border-bottom: 1px solid #ddd;
}
.nav-links a {
  margin: 0 10px;
  text-decoration: none;
  color: blue;
}
button {
  margin-left: 10px;
  padding: 5px 10px;
  background-color: red;
  color: white;
  border: none;
  cursor: pointer;
}
</style>
