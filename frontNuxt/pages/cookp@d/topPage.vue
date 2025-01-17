<template>
  <div>
    <nav>
      <ul>
        <li>
          <input type="text" v-model="recipeQuery" placeholder="レシピ検索" />
          <button @click="searchRecipes">検索</button>
        </li>
        <li>
          <input type="text" v-model="tagQuery" placeholder="タグ検索" />
          <button @click="searchByTag">検索</button>
        </li>
      </ul>
    </nav>
    <div>
      <a href="/recipe/input" class="post">レシピ投稿</a>
      <h2>おすすめレシピ</h2>
      <ul>
        <!-- フォールレシピ展示  -->
        <li v-for="recipe in recipes" :key="recipe.id">
          <nuxt-link
            :to="{
              name: 'cookp@d-recipeOverview',
              query: { recipeId: recipe.id, userId: userInfo.userId },
            }"
          >
            <strong>{{ recipe.title }}</strong>
          </nuxt-link>
          <p>レシピ説明: {{ recipe.recipe_description }}</p>
          <p>材料: {{ recipe.procedure_description }}</p>
        </li>
      </ul>
    </div>
  </div>
</template>


<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

const recipeQuery = ref("");
const tagQuery = ref("");
const recipes = ref();
const userInfo = ref(); //userInfo get by http://localhost:15151/recipe_cite/session

const router = useRouter();

async function fetchUserInfo() {
  try {
    const response = await axios.get(
      "http://localhost:15151/recipe_cite/session",
      { withCredentials: true }
    );
    //console.error(response.data)
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
    const response = await axios.get(
      "http://localhost:15151/recipe_cite/getRecipeAll"
    );
    recipes.value = response.data;
  } catch (error) {
    console.error("Error fetching recipes:", error);
  }
}

async function searchRecipes() {
  try {
    const response = await axios.get(
      `http://localhost:15151/recipe_cite/findRecipeByTitle?title=${encodeURIComponent(
        recipeQuery.value
      )}`
    );
    recipes.value = response.data; // update recipes
  } catch (error) {
    console.error("Error searching recipes:", error);
  }
}

async function searchByTag() {
  try {
    let params = new URLSearchParams();
    tagQuery.value.split(",").forEach((tag) => {
      params.append("tags", tag.trim());
    });
    const response = await axios.get(
      `http://localhost:15151/recipe_cite/findRecipeByTag?${params.toString()}`
    );
    recipes.value = response.data; // update recipes
  } catch (error) {
    console.error("Error searching by tags:", error);
  }
}

function login() {
  router.push("/account/home");
}

onMounted(() => {
  fetchUserInfo();
  fetchRecipes();
});
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

.post {
  text-align: center;
  padding: 6px 12px;
  background-color: rgb(0, 153, 255);
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s;
}

.post:hover {
  background-color: rgb(0, 110, 255);
}

.post:active {
  transform: scale(0.98);
}
</style>