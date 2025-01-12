<template>
  <div class="login-container">
    <!-- タイトル -->
    <h1>おかえりなさい！</h1>

    <!-- ログインフォーム -->
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="email">メールアドレス：</label>
        <input type="email" id="email" v-model="loginData.email" required />
      </div>

      <div class="form-group">
        <label for="password">パスワード：</label>
        <input type="password" id="password" v-model="loginData.password" required />
      </div>

      <!-- ログインボタン -->
      <button type="submit" class="login-button">ログイン</button>
    </form>

    <!-- 登録リンク -->
    <p class="register-link">
      アカウントを持っていない？ <a href="account/register">新規登録</a>
    </p>
  </div>
</template>

<script setup>
import { ref } from 'vue'

// ユーザー入力データを格納するオブジェクト
const loginData = ref({
  email: '',
  password: ''
})
// ✅ Cookie保存のためのヘルパー関数
const setCookie = (name, value, days) => {
  const expires = new Date()
  expires.setTime(expires.getTime() + days * 24 * 60 * 60 * 1000)
  document.cookie = `${name}=${value};expires=${expires.toUTCString()};path=/`
}
// ログインボタンのクリックイベント
const handleLogin = async () => {
  try {
    // バックエンドのAPIにPOSTリクエストを送信
    const response = await fetch('http://localhost:15151/recipe_cite/api/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',  // セッションを有効化するため
      body: JSON.stringify(loginData.value)
    })

    // レスポンスの成功・失敗判定
    if (response.ok) {
      const responseData = await response.json()
      setCookie('userName', responseData.userName, 7) // クッキーに保存
      alert('ログイン成功しました！')
      
      window.location.href = '/'
    } else {
      alert('ログイン失敗しました')
    }
  } catch (error) {
    console.error('エラー：', error)
    alert('ログイン時にエラーが発生しました')
  }
}
</script>

<style scoped>
/* ログインフォームの全体レイアウト設定 */
.login-container {
  max-width: 350px; /* フォームの最大幅を350pxに制限 */
  margin: 100px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  text-align: center;
  background-color: #fff;
}

/* タイトルのデザイン */
h1 {
  margin-bottom: 20px;
  font-size: 24px;
  font-weight: bold;
}

/* フォーム入力フィールドデザイン */
.form-group {
  margin-bottom: 15px;
  text-align: left;
}

input {
  width: 100%; /* 入力フィールドをコンテナの幅いっぱいに広げる */
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 8px;
  box-sizing: border-box;
}

/* ログインボタンのデザイン */
.login-button {
  width: 100%;
  padding: 12px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  margin-top: 10px;
  font-size: 16px;
}

.login-button:hover {
  background-color: #0056b3;
}

/* 登録リンクのデザイン */
.register-link {
  margin-top: 20px;
}

.register-link a {
  color: #007bff;
  text-decoration: none;
}

.register-link a:hover {
  text-decoration: underline;
}
</style>
