<template>
  <!-- 【修正】共通ヘッダーが必要 -->
  <!-- 【修正】確認モーダルが必要 -->
  <div class="register-container">
    <h2>アカウント登録</h2>
    <form @submit.prevent="registerUser" class="register-form">
      <form-input
        label="ユーザー名"
        type="text"
        placeholder="ユーザー名を入力してください"
        v-model="userName"
      />
      <form-input
        label="メールアドレス"
        type="text"
        placeholder="メールアドレスを入力してください"
        v-model="email"
      />
      <form-input
        label="パスワード"
        type="password"
        placeholder="パスワードを入力してください"
        v-model="password"
      />
      <button type="submit">送信</button>
    </form>
    <!-- 【修正】セッション確認用なので消去する必要がある -->
    <div v-if="sessionUser">
      <h3>現在のセッション情報</h3>
      <p>ユーザー名: {{ sessionUser.userName }}</p>
      <p>メールアドレス: {{ sessionUser.email }}</p>
    </div>
    <div v-else>
      <h3>セッションがありません</h3>
    </div>
  </div>
</template>

<script>
import FormInput from "../../components/FormInput.vue";
import axios from "axios";

export default {
  name: "register",
  components: {
    FormInput,
  },
  data() {
    return {
      // セッションのユーザー情報
      userId: "",
      userName: "",
      email: "",
      password: "",
      sessionUser: null,
    };
  },
  mounted() {
    // ページ読み込み時にセッションを取得
    this.fetchSessionUser();
  },
  methods: {
    // ユーザー登録処理
    async registerUser() {
      try {
        const response = await axios.post(
          "http://localhost:15151/recipe_cite/register",
          {
            userName: this.userName,
            email: this.email,
            password: this.password,
          },
          // withCredentialsを指定してセッション情報を保持する
          { withCredentials: true }
        );

        if (response.status === 200) {
          // 【修正】登録成功時に任意のページに遷移する(現在は/home)
          console.log("登録成功");
          this.$router.push("/account/home");
        }
      } catch (error) {
        console.error("登録エラー:", error);
        alert("アカウント登録に失敗しました。再度お試しください。");
      }
    },
    // セッション取得処理
    async fetchSessionUser() {
      try {
        const response = await axios.get(
          "http://localhost:15151/recipe_cite/session",
          { withCredentials: true }
        );
        if (response.data) {
          this.sessionUser = response.data;
        } else {
          this.sessionUser = null;
        }
      } catch (error) {
        console.warn("セッション情報の取得に失敗しました:", error);
        this.sessionUser = null;
      }
    },
  },
};
</script>

<style scoped>
.register-container {
  /* 【修正】横幅のmarginを調整 */
  /* 【修正】全体の色コード調整 */
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