<template>
  <div class="quote-card">
    <h3>💬 Citation du jour</h3>
    <blockquote>"{{ quote.text }}"</blockquote>
    <p>— {{ quote.author }}</p>
  </div>
</template>

<script>
export default {
  name: 'Quote',
  props: {
    quote: { type: Object, required: true }
  }
};
</script>

<style scoped>
.quote-card {
  background: #fffbea;
  padding: 15px;
  border-radius: 8px;
  width: 60%;
}
blockquote {
  font-size: 1.2em;
  margin: 10px 0;
}
</style>
