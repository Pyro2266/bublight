import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
	state: {
		sessionRunning: false,
		sessionStart: null,
	},
	mutations: {
		START_SESSION: function(state, payload) {
			state.sessionRunning = true;
			state.sessionStart = payload;
		},
		END_SESSION: function(state) {
			state.sessionRunning = false;
		}
	},
	actions: {
	},
	modules: {
	}
})
