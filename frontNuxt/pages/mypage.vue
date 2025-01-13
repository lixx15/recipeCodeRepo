<template>
  <div class="mypage">
    <!-- Header -->
    <h1>マイページ</h1>
    <div v-if="mypageData" class="content">
      <div class="left-side">
        <div class="user-info">
          <h2>ユーザー情報</h2>
          <p><strong>名前:</strong> {{ mypageData.userName }}</p>
          <p><strong>Email:</strong> {{ mypageData.email }}</p>
          <a href="/update">
            <p>アカウント変更</p>
          </a>
        </div>
        <Timeline :timeline="mypageData.timeLineItem" />
      </div>
      <main>
        <div class="menu">
          <button 
            :class="{ active: activeTab === 'posted' }" 
            @click="activeTab = 'posted'">
            投稿したレシピ
          </button>
          <button 
            :class="{ active: activeTab === 'favorite' }" 
            @click="activeTab = 'favorite'">
            お気に入りレシピ
          </button>
        </div>
        <section v-if="activeTab === 'posted'" class="posted-recipes">
          <h2>投稿したレシピ</h2>
          <PostedRecipes :recipes="mypageData.postedRecipes" />
        </section>
        <section v-if="activeTab === 'favorite'" class="favorite-recipes">
          <h2>お気に入りレシピ</h2>
          <PostedRecipes :recipes="mypageData.favoriteRecipes" />
        </section>
      </main>
    </div>
    <p v-else>読み込み中...</p>
  </div>
</template>

<script>
import PostedRecipes from '~/components/mypage/PostedRecipes.vue';
import Timeline from '~/components/mypage/TimeLine.vue';
import axios from 'axios';

export default {
  components: {
    PostedRecipes,
    Timeline,
  },
  data() {
    return {
      mypageData: null, // APIから取得するマイページデータ
      activeTab: 'posted', // デフォルトは「投稿したレシピ」
    };
  },
  async mounted() {
    try {
      // APIリクエストを直接埋め込み
      const response = await axios.get('http://localhost:15151/recipe_cite/mypage', { withCredentials: true });
      this.mypageData = response.data; // MypageDtoをデータに格納
    } catch (error) {
      console.error('Failed to fetch mypage data:', error);
      this.mypageData = null; // エラー時はnullを設定
    }
  },
};
</script>

<style scoped>
.mypage {
  padding: 20px;
}

.content {
  display: flex;
  justify-content: space-between;
}

.left-side {
  width: 30%;
}

.center {
  width: 60%;
}

.user-info {
  margin-bottom: 20px;
}

main {
  flex: 1;
  padding: 16px;
  background-color: #fff;
  overflow-y: auto;
}

.menu {
  display: flex;
  margin-bottom: 16px;
}

.menu button {
  padding: 8px 16px;
  border: 1px solid #ccc;
  background-color: #f4f4f4;
  cursor: pointer;
  transition: background-color 0.2s;
}

.menu button.active {
  background-color: #007bff;
  color: white;
}

.menu button:not(:last-child) {
  margin-right: 8px;
}

.menu button:hover {
  background-color: #e0e0e0;
}
</style>
