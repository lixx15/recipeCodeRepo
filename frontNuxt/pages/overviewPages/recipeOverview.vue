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
      <div>
        <input type="text" v-model="newComment" placeholder="Add a comment">
        <button @click="submitComment">Submit Comment</button>
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
  const newComment = ref('');
  const favoriteText = ref('お気に入り');
  
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
        },
        withCredentials: true  // 添加 withCredentials
      });
      recipe.value = data;
      updateFavoriteText();
    } catch (error) {
      console.error('get recipe details failed:', error);
    }
  }
  
  function updateFavoriteText() {
    favoriteText.value = recipe.value.collection_info ? 'Unfavorite' : 'Favorite';
  }
  
  function formatDateForMySQL() {
    const date = new Date();
    const formattedDate = date.toISOString().slice(0, 19).replace('T', ' ');
    return formattedDate;
  }
  
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