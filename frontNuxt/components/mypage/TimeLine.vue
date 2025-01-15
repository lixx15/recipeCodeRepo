<template>
    <div class="timeline">
      <h2>タイムライン</h2>
      <ul v-if="timeline && Object.keys(timeline).length">
        <li v-for="(message, dateTime) in timeline" :key="dateTime" class="timeline-item">
          <p><strong>{{ formatDateTime(dateTime) }}</strong>: {{ message }}</p>
        </li>
      </ul>
      <p v-else>タイムラインはまだありません。</p>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      timeline: {
        type: Object,
        required: true,
      },
    },
    methods: {
      formatDateTime(dateTime) {
        const date = new Date(dateTime);
        return date.toLocaleString(); // 日時をフォーマット
      },
    },
  };
  </script>
  
  <style scoped>
  .timeline {
    margin-bottom: 20px;
  }
  .timeline-item {
    border-bottom: 1px solid #ccc;
    padding: 10px 0;
  }
  </style>
  