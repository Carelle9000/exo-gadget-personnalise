<template>
  <div class="weather-card">
    <span>☀️ {{ temperature }}°C</span>
    <p>{{ city }}</p>
  </div>
</template>

<script>
import api from '../services/api';

export default {
  name: 'Weather',
  props: {
    city: { type: String, required: true }
  },
  data() {
    return { temperature: '--' };
  },
  async created() {
    this.temperature = await api.getWeather(this.city);
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
