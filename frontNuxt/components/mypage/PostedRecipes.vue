<template>
    <div class="posted-recipes">
    <ul v-if="recipes && recipes.length">
        <li v-for="recipe in recipes" :key="recipe.id" class="recipe-item">
          <nuxt-link
            :to="{
              name: 'cookp@d-recipeOverview',
              query: { recipeId: recipe.recipe_id, userId: userId },
            }"
          >
            <strong>{{ recipe.title }}</strong>
          </nuxt-link>
          <p>Description: {{ recipe.recipe_description }}</p>
          <p>Procedure: {{ recipe.procedure_description }}</p>
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
      userId: {
        required: true
      },
    },
  };
  </script>
  
  <style scoped>
  nav {
    margin-bottom: 20px;
    padding: 10px;
    background-color: #f4f4f4;
    border: 1px solid #ccc;
    border-radius: 5px;
  }
  
  nav ul {
    list-style-type: none;
    padding: 0;
    margin: 0;
    display: flex;
    gap: 10px;
    align-items: center;
  }
  
  nav li {
    display: flex;
    align-items: center;
  }
  
  input[type="text"] {
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 5px;
    outline: none;
    font-size: 14px;
    width: 200px;
    transition: border-color 0.2s;
  }
  
  input[type="text"]:focus {
    border-color: #007bff;
  }
  
  button {
    margin-left: 15px;
    padding: 8px 12px;
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
  
  div {
    margin-top: 20px;
  }
  
  h2 {
    margin-bottom: 16px;
    color: #333;
  }
  
  ul {
    list-style-type: none;
    padding: 0;
    margin: 0;
  }
  
  li {
    margin-bottom: 16px;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
    background-color: #fff;
    transition: box-shadow 0.2s;
  }
  
  li:hover {
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }
  
  a {
    color: #007bff;
    text-decoration: none;
    font-weight: bold;
  }
  
  a:hover {
    text-decoration: underline;
  }
  
  p {
    margin: 4px 0;
    font-size: 14px;
    color: #555;
  }
  </style>
  