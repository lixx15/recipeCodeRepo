<template>
  <div class="container">
  <div class="recipe-form">
    <h1>{{ isEditMode ? 'レシピを編集' : '新しいレシピを登録' }}</h1>
    <form @submit.prevent="submitRecipe">
      <div class="form-group">
        <label for="title">タイトル:</label>
        <input
          type="text"
          id="title"
          v-model="recipe.title"
          placeholder="レシピのタイトルを入力"
          required
        />
      </div>

      <div class="form-group">
        <label for="description">レシピの説明:</label>
        <textarea
          id="description"
          v-model="recipe.recipeDescription"
          placeholder="レシピの説明を入力"
          required
        ></textarea>
      </div>

      <div class="form-group">
        <label for="procedure">作り方:</label>
        <textarea
          id="procedure"
          v-model="recipe.procedureDescription"
          placeholder="作り方を入力"
          required
        ></textarea>
      </div>

      <div class="form-group">
        <label for="tags">タグ:</label>
        <div class="tags-input">
          <input
              type="text"
              v-model="newTag"
              @keydown.enter.prevent="addTag"
              placeholder="タグを入力してEnter"
            />
          <ul>
            <li v-for="(tag, index) in recipe.tags" :key="index">
              {{ tag.name }}
              <button type="button" @click="removeTag(index)">×</button>
            </li>
          </ul>
        </div>
      </div>

      <div class="form-group">
        <label for="ingredients">材料:</label>
        <div class="ingredients-input">
          <div v-for="(ingredient, index) in recipe.ingredients" :key="index">
            <input
              type="text"
              v-model="ingredient.name"
              placeholder="材料名"
              required
            />
            <input
              type="text"
              v-model="ingredient.quantity"
              placeholder="数量"
              required
            />
            <button type="button" @click="removeIngredient(index)">×</button>
          </div>
          <button type="button" @click="addIngredient">材料を追加</button>
        </div>
      </div>

      <button type="submit">{{ isEditMode ? 'レシピを更新' : 'レシピを登録' }}</button>
    </form>
  </div>
</div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();
const route = useRoute();

const userInfo = ref(null);
const isEditMode = ref(false); // 編集モードかどうか
const newTag = ref('');

const recipe = ref({
  title: '',
  recipeDescription: '',
  procedureDescription: '',
  tags: [],
  ingredients: [],
  userId: '',
});

onMounted(async () => {
  await fetchUserInfo();
  if (route.query.recipeId) {
    isEditMode.value = true;
    await fetchRecipe(route.query.recipeId);
  }
});

async function fetchUserInfo() {
  try {
    const response = await axios.get('http://localhost:15151/recipe_cite/session', {
      withCredentials: true,
    });

    if (response.data) {
      userInfo.value = response.data;
      recipe.value.userId = response.data.userId;
    } else {
      userInfo.value = null;
    }
  } catch (error) {
    console.warn('セッション情報の取得に失敗しました:', error);
    userInfo.value = null;
  }
}

async function fetchRecipe(recipeId) {
  try {
    const { data } = await axios.get('http://localhost:15151/recipe_cite/getRecipeDetails', {
      params: {
        recipe_id: recipeId,
        user_id: userInfo.value?.userId,
      },
      withCredentials: true,
    });

    recipe.value = data;
  } catch (error) {
    console.error('レシピの取得に失敗しました:', error);
    alert('レシピの取得に失敗しました。');
  }
}

function addTag() {
  if (newTag.value.trim() !== '') {
    recipe.value.tags.push({ name: newTag.value.trim() });
    newTag.value = '';
 
  }
}

function removeTag(index) {
  recipe.value.tags.splice(index, 1);
}

function addIngredient() {
  recipe.value.ingredients.push({ name: '', quantity: '' });
}

function removeIngredient(index) {
  recipe.value.ingredients.splice(index, 1);
}

async function submitRecipe() {
  try {
    await fetchUserInfo();
    if (isEditMode.value) {
      await axios.put('http://localhost:15151/recipe_cite/update-recipe', recipe.value);
      alert('レシピが更新されました！');
    } else {
      await axios.post('http://localhost:15151/recipe_cite/add-recipe-with-detail', recipe.value);
      alert('レシピが登録されました！');
    }
    router.push('/cookp@d/topPage'); // レシピ一覧ページにリダイレクト
  } catch (error) {
    console.error('レシピの登録・更新中にエラーが発生しました:', error);
    alert('レシピの登録・更新に失敗しました。');
  }
}

function resetForm() {
  recipe.value = {
    title: '',
    recipeDescription: '',
    procedureDescription: '',
    tags: [],
    ingredients: [],
    userId: userInfo.value?.userId || '',
  };
}
</script>
  
<style scoped>
  .container {
    padding: 20px;
  }
  .recipe-form {
    margin-bottom: 20px;
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #fff;
  }
  
  .form-group {
    margin-bottom: 16px;
  }
  
  input,
  textarea {
    width: 100%;
    padding: 8px;
    margin-top: 5px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 14px;
  }
  
  input:focus,
  textarea:focus {
    border-color: #007bff;
  }
  
  textarea {
    height: 100px;
  }
  
  .tags-input ul {
    display: flex;
    flex-wrap: wrap;
    list-style: none;
    padding: 0;
    margin: 5px 0 0;
  }
  
  .tags-input li {
    margin-right: 8px;
    background-color: #007bff;
    color: white;
    padding: 5px 10px;
    border-radius: 20px;
    display: flex;
    align-items: center;
  }
  
  .tags-input button {
    background-color: transparent;
    border: none;
    color: white;
    margin-left: 5px;
    cursor: pointer;
  }
  
  .ingredients-input > div {
    display: flex;
    gap: 10px;
    margin-bottom: 10px;
  }
  
  .ingredients-input input {
    width: 45%;
  }
  
  .ingredients-input button {
    padding: 5px 12px;
    background-color: #007bff;
    border: none;
    border-radius: 5px;
    color: white;
    font-size: 14px;
    cursor: pointer;
    transition: background-color 0.2s, transform 0.1s;
  }
  
  .ingredients-input button:hover {
    background-color: #0056b3;
  }
  
  .ingredients-input button:active {
    transform: scale(0.98);
  }
  
  button[type="submit"] {
    padding: 10px 20px;
    background-color: #007bff;
    border: none;
    border-radius: 5px;
    color: white;
    font-size: 16px;
    cursor: pointer;
    width: 100%;
    transition: background-color 0.3s, transform 0.2s;
  }
  
  button[type="submit"]:hover {
    background-color: #0056b3;
  }
  
  button[type="submit"]:active {
    transform: scale(0.98);
  }
  </style>