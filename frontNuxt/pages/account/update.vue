<template>
  <div class="update-container">
    <h2>アカウント更新</h2>
    <form @submit.prevent="updateUser" class="update-form">
      <form-input
        label="ユーザー名"
        type="text"
        :placeholder="loggedInUser.name"
        v-model="user.name"
      />
      <form-input
        label="メールアドレス"
        type="text"
        :placeholder="loggedInUser.email"
        v-model="user.email"
      />
      <form-input
        label="パスワード"
        type="password"
        :placeholder="loggedInUser.password"
        v-model="user.password"
      />
      <button type="submit">更新</button>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import FormInput from "../../components/FormInput.vue";
import { getSettion } from "../../store/getSettion";

// セッションからユーザー・ログイン情報を取得
const { isLoggedIn, loggedInUser } = getSettion();

// ルーター初期化
const router = useRouter();

// 
const getCookie = (name) => {
  const match = document.cookie.match("(^|;)\\s*" + name + "\\s*=\\s*([^;]+)");
  return match ? match.pop() : "";
};

// ユーザー情報
const user = ref({
  id: "",
  name: "",
  email: "",
  password: "",
});

// セッションで取得したユーザー情報をそのまま設定
onMounted(() => {
  if (loggedInUser.value) {
    user.value.id = loggedInUser.value.id;
    user.value.name = loggedInUser.value.name;
    user.value.email = loggedInUser.value.email;
    user.value.password = loggedInUser.value.password;
  }
});

// ユーザー情報を更新
const updateUser = async () => {
  console.log(user);
  try {
    const response = await axios.post(
      "http://localhost:15151/recipe_cite/update",
      {
        userId: user.value.id,
        userName: user.value.name,
        email: user.value.email,
        password: user.value.password,
      },
      { withCredentials: true }
    );

    if (response.status === 200) {
      // セッションを更新するため、一度ログアウトさせ再度ログインさせる
      alert("アカウント情報の更新に成功しました");

      
      await fetch("http://localhost:15151/recipe_cite/logout", {
        method: "POST",
        credentials: "include",
      });
      alert("ログアウトします");
      document.cookie =
        "userName=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;"; // ✅ クッキー削除
      window.location.href = "/cookp@d/login";
        router.push("/cookp@d/login");
      }
  } catch (error) {
    console.error(error);
    alert("アカウント情報の更新に失敗しました。");
  }
};
</script>

<style scoped>
/* アカウント更新フォームの全体レイアウト */
.update-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  background-color: #fff;
  font-family: Arial, sans-serif;
  text-align: center;
}

/* タイトルのデザイン */
h2 {
  margin-bottom: 20px;
  font-size: 22px;
  font-weight: bold;
  color: #333;
}

/* フォーム全体のデザイン */
.update-form {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

/* 入力フィールドのスタイル */
input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 8px;
  box-sizing: border-box;
  font-size: 14px;
}

/* プレースホルダーのスタイル */
input::placeholder {
  color: #aaa;
}

/* ボタンのスタイル */
button {
  padding: 12px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #218838;
}

button:active {
  background-color: #1e7e34;
  transform: scale(0.98);
}

/* レスポンシブ対応 */
@media (max-width: 768px) {
  .update-container {
    margin: 30px auto;
    padding: 15px;
  }

  h2 {
    font-size: 20px;
  }

  button {
    font-size: 14px;
    padding: 10px;
  }
}
</style>
