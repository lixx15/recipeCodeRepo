<template>
  <div class="register-container">
    <h2>アカウント登録</h2>
    <form @submit.prevent="registerUser" class="register-form">
      <form-input
        label="ユーザー名"
        type="text"
        placeholder="ユーザー名を入力してください"
        v-model="user.name"
      />
      <form-input
        label="メールアドレス"
        type="text"
        placeholder="メールアドレスを入力してください"
        v-model="user.email"
      />
      <form-input
        label="パスワード"
        type="password"
        placeholder="パスワードを入力してください"
        v-model="user.password"
      />
      <button type="submit">送信</button>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import FormInput from "../../components/FormInput.vue";

// ルーター初期化
const router = useRouter();

// ユーザー情報
const user = ref({
  id: "",
  name: "",
  email: "",
  password: "",
});

// ユーザー登録処理
const registerUser = async () => {
  try {
    const response = await axios.post(
      "http://localhost:15151/recipe_cite/register",
      {
        userName: user.value.name,
        email: user.value.email,
        password: user.value.password,
      },
      { withCredentials: true }
    );

    if (response.status === 200) {
      // セッションを更新するため、再度ログインさせる
      alert("アカウント情報の登録に成功しました");
      document.cookie =
        "userName=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;"; // ✅ クッキー削除
      router.push("/cookp@d/login");
    }
  } catch (error) {
    console.error(error);
    alert("アカウント登録に失敗しました。再度お試しください。");
  }
};
</script>

<style scoped>
/* 登録フォームの全体レイアウト */
.register-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  background-color: #fff;
  font-family: Arial, sans-serif;
}

/* タイトルのデザイン */
h2 {
  margin-bottom: 20px;
  font-size: 22px;
  font-weight: bold;
  color: #333;
  text-align: center;
}

/* フォーム全体のデザイン */
.register-form {
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

/* ボタンのスタイル */
button {
  padding: 12px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #0056b3;
}

button:active {
  background-color: #004085;
  transform: scale(0.98);
}

/* レスポンシブ対応 */
@media (max-width: 768px) {
  .register-container {
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
