import './initComponents.js';
import App from './components/app.vue';
import './../css/gadget.css';

const lang = eXo?.env?.portal?.language || 'en';
const bundle = 'locale.portlet.alfrescoApp';
const url = `${eXo.env.portal.context}/${eXo.env.portal.rest}/i18n/bundle/${bundle}-${lang}.json`;

exoi18n.loadLanguageAsync(lang, url)
  .then(i18n => {
    new Vue({
      render: h => h(App),
      i18n
    }).$mount('#vue_webpack_mygadget');
  });