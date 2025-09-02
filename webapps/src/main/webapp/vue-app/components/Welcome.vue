<template>
  <div class="welcome">
    <h2>Bonjour, <strong>{{ user.name }}</strong> !</h2>
    <p>Ensemble, faisons avancer nos projets avec enthousiasme !</p>
  </div>
</template>

<script>
export default {
  name: 'Welcome',
  props: {
    user: { type: Object, required: true }
  }
};
</script>

<style scoped>
.welcome h2 {
  margin: 0;
  color: #089aee;
}
.welcome p {
  color: #fcf9f9;
}
</style>
