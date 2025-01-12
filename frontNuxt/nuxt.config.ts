export default defineNuxtConfig({
  devtools: { enabled: true }, 
  runtimeConfig: {
    public: {
      apiBase: 'http://localhost:15151/recipe_cite' 
    }
  },
  compatibilityDate: '2024-11-01',
  pages: true,   
  app: {
    head: {
      title: 'My Recipe App',
      meta: [{ name: 'description', content: 'A Nuxt3 Recipe Application' }]
    }
  }
})
