<template>
  <div class="dashboard-container">
    <div class="header">
      <Welcome :user="user" />
      <div class="header-right">
        <Weather :city="city" />
        <Clock />
      </div>
    </div>

    <div class="content">
      <Quote :quote="quote" />
      <Docs :documents="documents" />
    </div>
  </div>
</template>

<script>
import Welcome from './Welcome.vue';
import Weather from './Weather.vue';
import Clock from './Clock.vue';
import Quote from './Quote.vue';
import Docs from './Docs.vue';

export default {
  name: 'App',
  components: { Welcome, Weather, Clock, Quote, Docs },
  data() {
    return {
      user: { name: 'Thomas' }, // Peut être récupéré via eXo Platform
      city: 'Douala',
      quote: null,
      documents: []
    };
  },
  async created() {
    // Charger les données depuis les API
   
    
  }
};
</script>

<style scoped>
.dashboard-container {
  background: #f7f8fa;
  border-radius: 12px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
  padding: 15px;
  font-family: 'Roboto', sans-serif;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #eaf3ff;
  padding: 15px;
  border-radius: 10px;
}

.header-right {
  display: flex;
  gap: 15px;
}

.content {
  display: flex;
  justify-content: space-between;
  margin-top: 15px;
}
</style>
