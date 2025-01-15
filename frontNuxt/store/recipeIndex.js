// store/index.js
export const state = () => ({
    currentRecipe: null
  });
  
  export const mutations = {
    setCurrentRecipe(state, recipe) {
      state.currentRecipe = recipe;
    }
  };