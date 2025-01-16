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
      router.push("/login/login");
    }
  } catch (error) {
    console.error(error);
    alert("アカウント登録に失敗しました。再度お試しください。");
  }
};
</script>

<style scoped>
.register-container {
  max-width: 1000px;
  margin: 0 auto;
}
.register-container h2 {
  margin: 20px 0;
}
.register-form {
  text-align: center;
}
.register-form button {
  margin: 20px;
  padding: 15px 30px;
  border: none;
}
</style>