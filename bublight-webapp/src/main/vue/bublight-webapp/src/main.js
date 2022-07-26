import Vue from 'vue';
import App from './App.vue';
import axios from 'axios';
import './scss/custom.scss'
import store from './store'

Vue.config.productionTip = false;
Vue.prototype.$http = axios;
Vue.prototype.$apiURL = "http://localhost:8080";

new Vue({
    render (h) {
		return h(App)
	},
    store,
    el: '#app'
});
