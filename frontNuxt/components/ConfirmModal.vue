<template>
  <div class="modal-overlay" @click.self="close">
    <div class="modal-content">
      <h2>以下の通りでよろしいでしょうか。</h2>
      <div class="signup-form">
        <label>ユーザー名</label>
        <input
          type="text"
          v-model="name"
          placeholder="ユーザー名を入力してください"
        />

        <label>メールアドレス</label>
        <input
          type="email"
          v-model="email"
          placeholder="メールアドレスを入力してください"
        />

        <label>パスワード</label>
        <input
          type="password"
          v-model="password"
          placeholder="パスワードを入力してください"
        />

        <div class="btn-container">
          <button @click="register">登録する</button>
          <button @click="close">閉じる</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["isOpen"],
  data() {
    return {
      name: "",
      email: "",
      password: "",
      image: null,
    };
  },
  methods: {
    handleImageUpload(event) {
      this.image = event.target.files[0];
    },
    async register() {
      const formData = new FormData();
      formData.append("name", this.name);
      formData.append("email", this.email);
      formData.append("password", this.password);
      formData.append("image", this.image);

      try {
        await this.$emit("register", formData);
        this.close();
      } catch (error) {
        console.error("登録エラー:", error);
        alert("登録に失敗しました");
      }
    },
    close() {
      this.$emit("close");
      this.clearForm();
    },
    clearForm() {
      this.name = "";
      this.email = "";
      this.password = "";
      this.image = null;
    },
  },
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 5px;
  width: 400px;
  height: auto;
}
.signup-form label {
  margin-top: 10px;
  display: block;
}
.signup-form input {
  width: 300px;
  padding: 10px;
  margin: 10px;
}
.btn-container {
  text-align: center;
}
.btn-container button {
  margin: 0 20px;
}
</style>