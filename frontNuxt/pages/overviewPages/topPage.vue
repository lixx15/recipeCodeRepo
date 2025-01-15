<template>
  <div>
    <nav>
      <ul>
        <li><nuxt-link to="/">Home</nuxt-link></li>
        <li>
          <input type="text" v-model="recipeQuery" placeholder="レシピ検索">
          <button @click="searchRecipes">検索</button>
        </li>
        <li>
          <input type="text" v-model="tagQuery" placeholder="タグ検索">
          <button @click="searchByTag">検索</button>
        </li>
        <li><nuxt-link to="/my-page">マイページ</nuxt-link></li>
      </ul>
      <div v-if="userInfo">
        welcome, {{ userInfo.name }}!
      </div>
      <button v-else @click="login">
        Login
      </button>
    </nav>
    <div>
      <h2>Featured Recipes</h2>
      <ul>
        <li v-for="recipe in recipes" :key="recipe.id">
          <nuxt-link :to="{ name: 'overviewPages-recipeOverview', query: {recipeId: recipe.id, userId: userInfo.userId} }">
            <strong>{{ recipe.title }}</strong>
          </nuxt-link>
          <p>Description: {{ recipe.recipe_description }}</p>
          <p>Procedure: {{ recipe.procedure_description }}</p>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const recipeQuery = ref('')
const tagQuery = ref('')
const recipes = ref()
const userInfo = ref()  //userInfo get by http://localhost:15151/recipe_cite/session

const router = useRouter()

async function fetchUserInfo() {
  try {
        const response = await axios.get(
          "http://localhost:15151/recipe_cite/session",
          { withCredentials: true }
        );
        console.error(response.data)
        if (response.data) {
          userInfo.value = response.data;
        } else {
          userInfo = null;
        }
      } catch (error) {
        console.warn("セッション情報の取得に失敗しました:", error);
        userInfo.value = null;
      }
}

async function fetchRecipes() {
  try {
    const response = await axios.get('http://localhost:15151/recipe_cite/getRecipeAll');
    recipes.value = response.data;
  } catch (error) {
    console.error('Error fetching recipes:', error);
  }
}

async function searchRecipes() {
  try {
    const response = await axios.get(`http://localhost:15151/recipe_cite/findRecipeByTitle?title=${encodeURIComponent(recipeQuery.value)}`);
    recipes.value = response.data;  // update recipes
  } catch (error) {
    console.error('Error searching recipes:', error);
  }
}

async function searchByTag() {
  try {
    let params = new URLSearchParams();
    tagQuery.value.split(',').forEach(tag => {
      params.append('tags', tag.trim());
    });
    const response = await axios.get(`http://localhost:15151/recipe_cite/findRecipeByTag?${params.toString()}`);
    recipes.value = response.data;  // update recipes
  } catch (error) {
    console.error('Error searching by tags:', error);
  }
}

function login() {
  router.push('/account/home');
}

onMounted(() => {
  fetchUserInfo();  
  fetchRecipes();
});
</script>

<style scoped>
nav ul {
  list-style-type: none;
  padding: 0;
}
nav li {
  display: inline-block;
  margin-right: 10px;
}
input, button {
  margin: 5px;
}
</style>