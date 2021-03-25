<template>
	<div>
		<div class="row">
			<div class="col-lg-3 col-md-6">
				<session-panel />
			</div>
			<div class="col-lg-3 col-md-6">
				<led-panel :activeModes="activeModes" @setMode="setMode($event)" />
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
				<mode-panel :activeModes="activeModes" @setMode="setMode($event)" />
			</div>
			<div class="col-lg-6 col-md-12">
				<overlay-panel :activeModes="activeModes" @setMode="setMode($event)" />
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
				activeModes: {
					led: false,
					simpleColor: false,
					rainbow: false,
					brightness: false,
					runningDot: false
				}
			}
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
			setMode(e) {
				if(e.type == 'activate') {
					this.activateMode(e);
				} else {
					this.deactivateModes(e);
				}
			},

			activateMode(e) {
				switch(e.mode) {
					case 'brightness':
						this.$set(this.activeModes, 'runningDot', false);
						this.$set(this.activeModes, 'brightness', true);
						break;
					case 'runningDot':
						this.$set(this.activeModes, 'brightness', false);
						this.$set(this.activeModes, 'runningDot', true);
						break;
					case 'simpleColor':
						this.$set(this.activeModes, 'rainbow', false);
						this.$set(this.activeModes, 'simpleColor', true);
						break;
					case 'rainbow':
						this.$set(this.activeModes, 'simpleColor', false);
						this.$set(this.activeModes, 'rainbow', true);
						break;
					case 'led':
						this.$set(this.activeModes, 'led', true);
						break;
				}	
			},

			deactivateModes(e) {
				switch(e.mode) {
					case 'brightness':						
						this.$set(this.activeModes, 'brightness', false);
						break;
					case 'runningDot':						
						this.$set(this.activeModes, 'runningDot', false);
						break;					
					case 'led':
						this.$set(this.activeModes, 'led', false);
						break;
				}	
			}
		}
	}
</script>
