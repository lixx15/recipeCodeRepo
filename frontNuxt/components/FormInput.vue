<!-- フォームの入力フィールドを制御 -->
<template>
  <div class="form-input">
    <label class="form-label">{{ label }}</label>
    <input
      :id="label"
      :type="type"
      :placeholder="placeholder"
      class="form-control"
      v-model="inputValue"
    />
  </div>
</template>

<script>
export default {
  name: "FormInput",
  props: {
    // 入力フィールドのラベル
    label: {
      type: String,
      required: true,
    },
    // 入力フィールドのタイプ
    type: {
      type: String,
      default: "text",
    },
    // 入力フィールドのプレースホルダー
    placeholder: {
      type: String,
      default: "",
    },
    // 入力フィールドの入力内容を扱う
    modelValue: {
      type: [String, Number],
      default: "",
    },
  },
  computed: {
    inputValue: {
      get() {
        return this.modelValue;
      },
      set(value) {
        this.$emit("update:modelValue", value);
      },
    },
  },
};
</script>

<style scoped>
.form-input {
  /* 【修正】全体の色コード調整 */
  margin-bottom: 20px;
}
.form-label {
  display: block;
  font-weight: bold;
  margin-bottom: 10px;
  text-align: left !important;
}
.form-control {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
</style>