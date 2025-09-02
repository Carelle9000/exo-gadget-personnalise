<template>
  <div class="clock">{{ time }}</div>
</template>

<script>
export default {
  name: 'Clock',
  data() {
    return { time: new Date().toLocaleTimeString() };
  },
  mounted() {
    this.timer = setInterval(() => {
      this.time = new Date().toLocaleTimeString();
    }, 1000);
  },
  beforeUnmount() {
    clearInterval(this.timer);
  }
};
</script>

<style scoped>
.clock {
  font-size: 1.2em;
  font-weight: bold;
}
</style>