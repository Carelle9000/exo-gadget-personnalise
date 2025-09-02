import Weather from './components/Weather.vue';
import Clock from './components/Clock.vue';
import Quote from './components/Quote.vue';
import Docs from './components/Docs.vue';
import Welcome from './components/Welcome.vue';
const components = {
  'weather-widget': Weather,
  'clock-widget': Clock,
  'quote-widget': Quote,
  'docs-widget': Docs,
  'welcome-widget': Welcome,
};

for (const key in components) {
  Vue.component(key, components[key]);
}