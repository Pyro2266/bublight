import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
	state: {
		sessionRunning: false,
		sessionStart: null,
		activeModes: {
			led: false,
			simpleColor: false,
			rainbow: false,
			brightness: false,
			runningDot: false
		}
	},
	mutations: {
		START_SESSION: function(state, payload) {
			state.sessionRunning = true;
			state.sessionStart = payload;
		},
		END_SESSION: function(state) {
			state.sessionRunning = false;
		},
		ACTIVATE_MODE: function(state, payload) {
			state.activeModes[payload] = true;
		},
		DEACTIVATE_MODE: function(state, payload) {
			state.activeModes[payload] = false;
		},
		UPDATE_MODE: function(state, payload) {
			state.activeModes[payload.field] = payload.value;
		}
	},
	actions: {
		activateMode({ commit }, mode) {
			switch(mode) {
				case 'brightnessByPressure':
					commit("DEACTIVATE_MODE", 'runningDot');
					commit("ACTIVATE_MODE", 'brightness');
					break;
				case 'runningDot':
					commit("DEACTIVATE_MODE", 'brightness');
					commit("ACTIVATE_MODE", 'runningDot');
					break;
				case 'simple':
					commit("DEACTIVATE_MODE", 'rainbow');
					commit("ACTIVATE_MODE", 'simpleColor');
					break;
				case 'simpleRainbow':
					commit("DEACTIVATE_MODE", 'simpleColor');
					commit("ACTIVATE_MODE", 'rainbow');
					break;
				case 'led':
					commit("ACTIVATE_MODE", 'led');
					break;
			}
		},

		deactivateMode({ commit }, mode) {
			switch(mode) {
				case 'overlay':						
					commit("DEACTIVATE_MODE", 'brightness');
					commit("DEACTIVATE_MODE", 'runningDot');
					break;					
				case 'led':
					commit("DEACTIVATE_MODE", 'led');
					break;
			}	
		}
	},
	modules: {
	}
})
