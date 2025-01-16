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
      // セッションを更新するため、再度ログインさせる
      alert("アカウント情報の更新に成功しました");
      router.push("/login/login");
    }
  } catch (error) {
    console.error(error);
    alert("アカウント情報の更新に失敗しました。");
  }
};
</script>

<style scoped>
.update-container {
  max-width: 1000px;
  margin: 0 auto;
}
.update-container h2 {
  margin: 20px 0;
}
.update-form {
  text-align: center;
}
.update-form button {
  margin: 20px;
  padding: 15px 30px;
  border: none;
}
</style>