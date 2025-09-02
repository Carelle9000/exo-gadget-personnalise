<template>
  <div class="uiBox dashboard-container">
    <div v-if="loading" class="loader-container">
      <div class="loader"></div>
      <p>Chargement des données...</p>
    </div>
    <div v-else>
      <div class="uiHeader header">
        <welcome :user="user" />
        <div class="header-right">
          <weather :city="city" :temperature="weather.temperature" />
          <clock />
        </div>
      </div>
      <div class="uiGrid content">
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
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.07);
  padding: 24px;
  font-family: "Open Sans", Arial, sans-serif;
  margin: 24px auto;
  max-width: 900px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #f4f7fb;
  padding: 18px 24px;
  border-radius: 6px;
  margin-bottom: 18px;
  border-bottom: 1px solid #e5e8ec;
}

.header-right {
  display: flex;
  gap: 18px;
}

.content {
  display: flex;
  gap: 24px;
  margin-top: 18px;
}

.loader-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 250px;
}

.loader {
  border: 6px solid #e5e8ec;
  border-top: 6px solid #2c9f45;
  border-radius: 50%;
  width: 48px;
  height: 48px;
  animation: spin 1s linear infinite;
  margin-bottom: 12px;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
</style>