<template>
  <div class="docs-card">
    <h3>📂 Documents récents</h3>
    <ul>
      <li v-for="doc in documents" :key="doc.name">
        <span>{{ doc.name }}</span>
        <span>{{ doc.time }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'Docs',
  props: {
    documents: { type: Array, required: true }
  }
};
</script>

<style scoped>
.docs-card {
  background: #96c2d3;
  padding: 15px;
  border-radius: 8px;
  width: 35%;
}
ul {
  list-style: none;
  padding: 0;
}
li {
  display: flex;
  justify-content: space-between;
  padding: 5px 0;
}
</style>
