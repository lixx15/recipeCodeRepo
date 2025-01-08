<template>
  <!-- 【修正】共通ヘッダーが必要 -->
  <!-- 【修正】確認モーダルが必要 -->
  <div class="update-container">
    <h2>アカウント更新</h2>
    <form @submit.prevent="updateUser" class="update-form">
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
      <button type="submit">更新</button>
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
  name: "UpdateAccount",
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
    // ユーザー更新処理
    async updateUser() {
      try {
        const response = await axios.post(
          "http://localhost:15151/recipe_cite/update", // 修正: URLをバックエンドの更新エンドポイントに変更
          {
            userId: this.userId,
            userName: this.userName,
            email: this.email,
            password: this.password,
          },
          // withCredentialsを指定してセッション情報を保持する
          { withCredentials: true }
        );

        if (response.status === 200) {
          // 【修正】更新成功時に任意のページに遷移する(現在は/home)
          console.log("更新成功");
          this.$router.push("/account/home");
        }
      } catch (error) {
        console.error("更新エラー:", error);
        alert("アカウント情報の更新に失敗しました。");
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
          // userIdのみ再度取得
          this.sessionUser = response.data;
          this.userId = this.sessionUser.userId;
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
.update-container {
  /* 【修正】横幅のmarginを調整 */
  /* 【修正】全体の色コード調整 */
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