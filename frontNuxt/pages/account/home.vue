<template>
  <!-- 【修正】共通ヘッダーが必要 -->
  <div class="home-container">
    <h2>アカウントページ</h2>
    <!-- 【修正】セッション確認用なので消去する必要がある -->
    <div v-if="sessionUser">
      <h3>現在のセッション情報</h3>
      <p>ユーザー名: {{ sessionUser.userName }}</p>
      <p>メールアドレス: {{ sessionUser.email }}</p>
    </div>
    <div v-else>
      <h3>セッションがありません</h3>
    </div>
    <router-link to="/account/register">アカウント登録</router-link>
    <router-link to="/account/update">アカウント変更</router-link>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "home",
  data() {
    return {
      // セッションのユーザー情報
      sessionUser: null,
    };
  },
  mounted() {
    // ページ読み込み時にセッションを取得
    this.fetchSessionUser();
  },
  methods: {
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
.home-container {
  /* 【修正】横幅のmarginを調整 */
  /* 【修正】全体の色コード調整 */
  max-width: 1000px;
  margin: 0 auto;
}
.home-container h2 {
  margin: 20px 0;
}
</style>