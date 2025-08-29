<template>
  <div class="weather-card">
    <span>☀️ {{ temperature }}°C</span>
    <p>{{ city }}</p>
  </div>
</template>

<script>

export default {
  name: 'Weather',
  props: {
    city: { type: String, required: true }
  },
  data() {
    return { temperature: '--' };
  },
  async created() {
  }
};
</script>

<style scoped>
.weather-card {
  background: white;
  padding: 10px;
  border-radius: 8px;
  text-align: center;
}
</style>
