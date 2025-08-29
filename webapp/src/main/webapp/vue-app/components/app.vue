<template>
  <div>
    <div v-if="loading" class="loader-container">
      <div class="loader"></div>
      <p>Chargement des données...</p>
    </div>
    <div v-else class="dashboard-container">
      <div class="header">
        <welcome :user="user" />
        <div class="header-right">
          <weather :city="city" :temperature="weather.temperature" />
          <clock />
        </div>
      </div>
      <div class="content">
        <quote :quote="quote" />
        <docs :documents="documents" />
      </div>
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
      user: { name: 'Thomas' },
      city: 'Douala',
      weather: { temperature: '--' },
      quote: null,
      documents: [],
      loading: true
    };
  },
  methods: {
    async fetchWeather() {
      try {
        const resp = await fetch('/rest/gadget/weather');
        if (resp.ok) {
          this.weather = await resp.json();
        }
      } catch (e) {
        this.weather = { temperature: '--' };
      }
    },
    async fetchQuote() {
      try {
        const resp = await fetch('/rest/gadget/quote');
        if (resp.ok) {
          this.quote = await resp.json();
        }
      } catch (e) {
        this.quote = null;
      }
    },
    async fetchDocuments() {
      try {
        const resp = await fetch('/rest/gadget/documents');
        if (resp.ok) {
          this.documents = await resp.json();
        }
      } catch (e) {
        this.documents = [];
      }
    },
    async loadAll() {
      await Promise.all([
        this.fetchWeather(),
        this.fetchQuote(),
        this.fetchDocuments()
      ]);
      this.loading = false;
    }
  },
  created() {
    this.loadAll();
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

.loader-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 300px;
}

.loader {
  border: 6px solid #f3f3f3;
  border-top: 6px solid #2c9f45;
  border-radius: 50%;
  width: 50px;
  height: 50px;
  animation: spin 1s linear infinite;
  margin-bottom: 10px;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
</style>