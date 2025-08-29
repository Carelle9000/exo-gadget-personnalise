<template>
  <div class="clock-card">
    <div>{{ formattedDate }}</div>
    <div>{{ formattedTime }}</div>
  </div>
</template>

<script>
export default {
  name: 'Clock',
  data() {
    return {
      now: new Date()
    };
  },
  computed: {
    formattedDate() {
      return this.now.toLocaleDateString('fr-FR');
    },
    formattedTime() {
      return this.now.toLocaleTimeString('fr-FR');
    }
  },
  created() {
    setInterval(() => {
      this.now = new Date();
    }, 1000);
  }
};
</script>

<style scoped>
.clock-card {
  background: white;
  padding: 10px;
  border-radius: 8px;
  text-align: center;
}
</style>
