import Vue from 'vue';
import App from './App.vue';
import axios from 'axios';

Vue.config.productionTip = false;
Vue.prototype.$http = axios;
Vue.prototype.$apiURL = window.location.origin;

new Vue({
	render (h) {
		return h(App)
	},
    el: '#app',
});
