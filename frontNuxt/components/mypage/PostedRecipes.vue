<template>
    <div class="posted-recipes">
    <ul v-if="recipes && recipes.length">
        <li v-for="recipe in recipes" :key="recipe.id" class="recipe-item">
        <a v-bind:href="`/recipe/${recipe.recipe_id}`"><h3>{{ recipe.title }}</h3></a>
        <p>{{ recipe.recipeDescription }}</p>
        <p>コメント数: {{ recipe.commentCount }}</p>
        <p>お気に入り数: {{ recipe.favoriteCount }}</p>
        </li>
    </ul>
    <p v-else>レシピはありません。</p>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      recipes: {
        type: Array,
        required: true,
      },
    },
  };
  </script>
  
  <style scoped>
  .posted-recipes {
    margin-bottom: 20px;
  }
  .recipe-item {
    border: 1px solid #ccc;
    padding: 10px;
    margin-bottom: 10px;
  }
  </style>
  