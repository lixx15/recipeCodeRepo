<template>
    <div>
      <h1>{{ recipe.title }}</h1>
      <p>{{ recipe.description }}</p>
      <div>
        <strong>Tags:</strong>
        <ul>
          <li v-for="tag in recipe.tags" :key="tag.name">{{ tag.name }}</li>
        </ul>
      </div>
      <div>
        <strong>Ingredients:</strong>
        <ul>
          <li v-for="ingredient in recipe.ingredients" :key="ingredient.name">
            {{ ingredient.name }}: {{ ingredient.quantity }}
          </li>
        </ul>
      </div>
      <div>
        <strong>Comments:</strong>
        <ul>
          <li v-for="comment in recipe.comments" :key="comment.post_datetime">
            {{ comment.content }} ({{ comment.post_datetime }})
          </li>
        </ul>
      </div>
      <button @click="toggleFavorite">{{ favoriteText }}</button>
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
  const favoriteText = ref('お気に入り');
  
  // 访问路由参数
  const route = useRoute();
  
  onMounted(async () => {
    await fetchRecipeDetails();
  });
  
  async function fetchRecipeDetails() {
    try {
      const { data } = await axios.get(`http://localhost:15151/recipe_cite/getRecipeDetails`, {
        params: {
          recipe_id: route.query.recipeId,
          user_id: route.query.userId
        }
      });
      recipe.value = data;
      favoriteText.value = recipe.value.collection_info ? 'Unfavorite' : 'Favorite';
    } catch (error) {
      console.error('get recipe detials failed:', error);
    }
  }
  
  function toggleFavorite() {
    recipe.value.collection_info = !recipe.value.collection_info;
    favoriteText.value = recipe.value.collection_info ? 'Unfavorite' : 'Favorite';
    // 理想情况下，这里应该更新服务器上的状态
  }
  </script>