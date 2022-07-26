<template>
	<div>
		<div class="row">
			<div class="col-lg-3 col-md-6">
				<session-panel />
			</div>
			<div class="col-lg-3 col-md-6">
				<led-panel />
			</div>
			<div class="col-lg-3 col-md-6">
				<music-panel />
			</div>
			<div class="col-lg-3 col-md-6">
				<led-info />
			</div>
			<div class="col-12">
				<chart title="Pressure Chart" subtitle="Realtime" />
			</div>
			<div class="col-lg-6 col-md-12">
				<mode-panel />
			</div>
			<div class="col-lg-6 col-md-12">
				<overlay-panel />
			</div>
		</div>
		<session-modal />
	</div>
</template>

<script>
	import Chart from './components/chart/chart.component';
	import LedPanel from './components/panel-led/led-panel.component';
	import ModePanel from './components/panel-mode/mode-panel.component';
	import MusicPanel from './components/panel-music/music-panel.component';
	import OverlayPanel from './components/panel-overlay/overlay-panel.component';
	import SessionPanel from './components/panel-session/session-panel.component';
	import LedInfo from './components/led-info/led-info.component';
	import SessionModal from './components/panel-session/modals/session-modal.component';
	import _ from 'lodash';
	

	export default {
		name: 'App',

		components: {
			Chart,
			LedPanel,
			ModePanel,
			MusicPanel,
			OverlayPanel,
			SessionPanel,
			LedInfo,
			SessionModal
		},

		data() {
			return {
				pressure: 0,
				pressureArray: [],				
			}
		},

		async created() {
			await this.getCurrentSettings();
		},
		
		mounted() {
			// window.$.notify({
			// 	message: 'Time to change coal!',
			// 	title: 'Warning!',
			// 	icon: 'warning'
			// },{				
			// 	type: 'warning',
			// 	mouse_over: 'pause',
			// });

			// window.$.notify({
			// 	message: 'Longest drag of session!',
			// 	title: 'Achievment!',
			// 	icon: 'info'
			// },{				
			// 	type: 'info',
			// 	mouse_over: 'pause',
			// });
		},

		methods: {
			async getCurrentSettings() {
				this.$http.get(this.$apiURL + "/config/current")
                .then(response => {
                    if(response.data.baseMode) {
						this.$store.dispatch('activateMode', _.camelCase(response.data.baseMode.modeId));
					}

					if(response.data.overlayMode) {
						this.$store.dispatch('activateMode', _.camelCase(response.data.overlayMode.modeId));
					}

					if(response.data.led) {
						this.$store.dispatch('activateMode', 'led');
					}
                });		
			},
		}
	}
</script>
