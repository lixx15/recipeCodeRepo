<!-- components/Header.vue -->
<template>
  <div class="header-container">
    <div class="logo"><h1>cookp@d</h1></div>
    <div class="nav-links">
      <template v-if="isLoggedIn">
        <span>こんにちは, {{ loggedInUser.userName }}さん</span>
        <router-link to="/cookp@d/topPage">レシピ一覧</router-link>
        <router-link to="/cookp@d/mypage">マイページ</router-link>
        <router-link to="/account/update">アカウント情報変更</router-link>
        <button @click="logout">ログアウト</button>
      </template>
      <template v-else>
        <router-link to="/cookp@d/login">ログイン</router-link>
        <router-link to="/account/register">新規登録</router-link>
      </template>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";

// ✅ State 管理
const isLoggedIn = ref(false);
const loggedInUser = ref({});

// ✅ Cookieを使ってセッション確認
const getCookie = (name) => {
  const match = document.cookie.match("(^|;)\\s*" + name + "\\s*=\\s*([^;]+)");
  return match ? match.pop() : "";
};

// ✅ 初回読み込み時にクッキー確認
onMounted(() => {
  const userName = getCookie("userName");
  if (userName) {
    loggedInUser.value.userName = userName;
    isLoggedIn.value = true;
  }
});

// ✅ ログアウト処理
const logout = async () => {
  try {
    await fetch("http://localhost:15151/recipe_cite/logout", {
      method: "POST",
      credentials: "include",
    });
    alert("ログアウトしました");
    isLoggedIn.value = false;
    document.cookie =
      "userName=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;"; // ✅ クッキー削除
    window.location.href = "/cookp@d/login";
  } catch (error) {
    console.error("ログアウトエラー:", error);
  }
};
</script>

<style scoped>
/* ヘッダー全体のスタイル */
.header-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 20px;
  background-color: #f8f9fa;
  border-bottom: 1px solid #ddd;
  font-family: Arial, sans-serif;
}

/* ロゴ部分 */
.logo a {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  text-decoration: none;
}

.logo a:hover {
  color: #007bff;
}

/* ナビゲーションリンク */
.nav-links {
  display: flex;
  align-items: center;
  gap: 15px;
}

.nav-links a {
  text-decoration: none;
  color: #007bff;
  font-size: 14px;
  transition: color 0.3s;
}

.nav-links a:hover {
  color: #0056b3;
}

.nav-links .welcome-message {
  margin-right: 15px;
  font-size: 14px;
  color: #555;
}

/* ボタン */
button {
  padding: 6px 12px;
  background-color: #dc3545;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s;
}

button:hover {
  background-color: #c82333;
}

button:active {
  transform: scale(0.98);
}

/* レスポンシブ対応 */
@media (max-width: 768px) {
  .header-container {
    flex-direction: column;
    align-items: flex-start;
  }

  .nav-links {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }
}
</style>
