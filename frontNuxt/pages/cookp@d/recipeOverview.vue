<template>
  <div class="container">
    <div class="recipe-details">
      <!-- noew recipe's detiails  -->
      <h1>{{ recipe.title }}</h1>
      <p>{{ recipe.description }}</p>
      <div>
        <!-- タッグ情報  -->
        <strong>タグ:</strong>
        <ul>
          <li v-for="tag in recipe.tags" :key="tag.name">{{ tag.name }}</li>
        </ul>
      </div>
      <div>
        <!-- 食材情報  -->
        <strong>材料:</strong>
        <ul>
          <li v-for="ingredient in recipe.ingredients" :key="ingredient.name">
            {{ ingredient.name }}: {{ ingredient.quantity }}
          </li>
        </ul>
      </div>
      <div>
        <!-- コメント欄と投稿インプット -->
        <strong>投稿されたコメント:</strong>
        <ul>
          <li v-for="comment in recipe.comments" :key="comment.post_datetime">
            {{ comment.content }} ({{ comment.post_datetime }})
          </li>
        </ul>
      </div>
      <div>
        <strong>コメントの投稿:</strong>
        <input type="text" v-model="newComment" placeholder="Add a comment">
        <button @click="submitComment">投稿</button>
      </div>
      <button @click="toggleFavorite">{{ favoriteText }}</button>
    </div>
    <div class="recipe-recommendations">
      <!-- 関連レシピ -->
      <h2>関連レシピ</h2>
      <ul>
        <li v-for="similar in similarRecipes" :key="similar.id">
          <a :href="`/cookp@d/recipeOverview?recipeId=${similar.recipe_id}&userId=${route.query.userId}`" target="_blank"> {{ similar.title }} </a>
          <p>{{ similar.description }}</p>
        </li>
      </ul>
    </div>
  </div>
</template>

  
  <script setup>
  import { ref, onMounted } from 'vue';
  import axios from 'axios';
  import { useRoute } from 'vue-router';
  
  const recipe = ref({
    title: '',
    description: '',
    tags: [],
    ingredients: [],
    comments: []
  });
  const newComment = ref('');
  const favoriteText = ref('お気に入り');
  const similarRecipes = ref([]);
  const route = useRoute();
  
  onMounted(async () => {
    await fetchRecipeDetails();   //get recipe details,include comment and collection
    await fetchSimilarRecipes();  //get similarly recommended recipes
  });
  
  async function fetchRecipeDetails() {
    try {
      const { data } = await axios.get(`http://localhost:15151/recipe_cite/getRecipeDetails`, {
        params: {
          recipe_id: route.query.recipeId,
          user_id: route.query.userId
        },
        withCredentials: true  //  withCredentials
      });
      recipe.value = data;
      updateFavoriteText();
    } catch (error) {
      console.error('get recipe details failed:', error);
    }
  }

  async function fetchSimilarRecipes() {
    try {
      const tagsString = recipe.value.tags.map(tag => tag.name).join(','); 
      const { data } = await axios.get(`http://localhost:15151/recipe_cite/getSimilarRecipes`, {
        params: {
          tags: tagsString  // params : change list to string
        },
        withCredentials: true
      });
      similarRecipes.value = data;
    } catch (error) {
      console.error('Error fetching similar recipes:', error);
    }
  }
  
  // お気に入り状態チェック
  function updateFavoriteText() {
    favoriteText.value = recipe.value.collection_info ? 'お気に入り解除' : 'お気に入り';
  }
  
  // nuxt Date format ->  sqldatabase Date format
  function formatDateForMySQL() {
    const date = new Date();
    const formattedDate = date.toISOString().slice(0, 19).replace('T', ' ');
    return formattedDate;
  }
  
  // バックエンドにお気に入り状態変更
  async function toggleFavorite() {
    try {
      const response = await axios.get('http://localhost:15151/recipe_cite/recipeCollected', {
        params: {
          datetime: formatDateForMySQL(), 
          recipe_id: route.query.recipeId
        },
        withCredentials: true  
      });
      console.error(response.data)
      if(response.data === 2) {
        alert("please login first");
      } else if (response.status === 200) {
        recipe.value.collection_info = !recipe.value.collection_info;
        updateFavoriteText();
      }
    } catch (error) {
      console.error('Error toggling favorite:', error);
    }
  }
  
  // コメント投稿
  async function submitComment() {
    try {
      const response = await axios.post('http://localhost:15151/recipe_cite/subbmitComment', {
        content: newComment.value,
        post_datetime: formatDateForMySQL(),
        recipe_id: recipe.value.id
      }, {
        withCredentials: true 
      });
      if (response.status === 200) {
        recipe.value.comments.push({
          content: newComment.value,
          post_datetime: new Date().toISOString()
        });
        newComment.value = '';  // Clear the input after successful submission
      }
    } catch (error) {
      console.error('Error submitting comment:', error);
    }
  }
  </script>
<style scoped>
.container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  padding: 20px;
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 10px;
}

.recipe-details {
  flex: 2;
  padding: 16px;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.recipe-details h1 {
  margin-bottom: 16px;
  font-size: 24px;
  color: #333;
}

.recipe-details p {
  margin-bottom: 10px;
  font-size: 14px;
  color: #555;
}

.recipe-details strong {
  display: block;
  margin-top: 16px;
  margin-bottom: 8px;
  font-size: 16px;
  color: #333;
}

ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

ul li {
  margin-bottom: 8px;
  padding: 8px;
  background-color: #f4f4f4;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 14px;
  color: #555;
}

input[type="text"] {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  width: calc(100% - 100px);
  margin-right: 8px;
  font-size: 14px;
}

button {
  margin: 15px;
  padding: 8px 16px;
  background-color: #007bff;
  border: none;
  border-radius: 5px;
  color: white;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.2s, transform 0.1s;
}

button:hover {
  background-color: #0056b3;
}

button:active {
  transform: scale(0.98);
}

.recipe-recommendations {
  flex: 1;
  padding: 16px;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.recipe-recommendations h2 {
  margin-bottom: 16px;
  font-size: 20px;
  color: #333;
}

.recipe-recommendations ul li a {
  color: #007bff;
  text-decoration: none;
  font-weight: bold;
}

.recipe-recommendations ul li a:hover {
  text-decoration: underline;
}

.recipe-recommendations ul li p {
  margin-top: 4px;
  font-size: 14px;
  color: #555;
}
</style>