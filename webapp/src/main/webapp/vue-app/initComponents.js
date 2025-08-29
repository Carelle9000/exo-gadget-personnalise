import alfrescoAppMain from './components/alfrescoAppMain.vue';
import alfrescoAppFilesLists from './components/alfrescoAppFilesLists.vue';

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