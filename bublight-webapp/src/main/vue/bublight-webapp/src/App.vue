<template>
	<div class="row">
		<div class="col-lg-3 col-md-6">
			<pressure-panel title="Pressure" :value="pressure" icon="icon-app"></pressure-panel>
		</div>
		<div class="col-lg-3 col-md-6">
			<led-panel></led-panel>
		</div>
		<div class="col-lg-3 col-md-6">
			<music-panel></music-panel>
		</div>
		<div class="col-lg-3 col-md-6">
			<led-info></led-info>
		</div>
		<div class="col-12">
			<chart title="Pressure Chart" :pressureArray="pressureArray" :value="pressure" subtitle="Realtime"></chart>
		</div>
		<div class="col-lg-6 col-md-12">
			<mode-panel></mode-panel>
		</div>
		<div class="col-lg-6 col-md-12">
			<overlay-panel></overlay-panel>
		</div>
	</div>
</template>

<script>
	import Chart from './components/chart/chart.component';
	import LedPanel from './components/panel-led/led-panel.component';
	import ModePanel from './components/panel-mode/mode-panel.component';
	import MusicPanel from './components/panel-music/music-panel.component';
	import OverlayPanel from './components/panel-overlay/overlay-panel.component';
	import PressurePanel from './components/panel-pressure/pressure-panel.component';	
	import LedInfo from './components/led-info/led-info.component';
	import SockJS from 'sockjs-client';
	import Stomp from 'stompjs';

	export default {
		name: 'App',

		components: {
			Chart,
			LedPanel,
			ModePanel,
			MusicPanel,
			OverlayPanel,
			PressurePanel,
			LedInfo
		},

		data() {
			return {
				pressure: 0,
				pressureArray: []
			}
		},

		created() {
			for(var i = 0; i < 50; i++) {
				this.pressureArray.push(0);
			}

			let ws = new SockJS(this.$apiURL + "/socket");
			let stompClient = Stomp.over(ws)
			stompClient.debug = null
			stompClient.connect({}, () => {
				stompClient.subscribe("/pressureSubscribe", (message) => {
					const pressure = (Math.round(message.body));
					this.pressure = pressure;
					this.pressureArray.push(pressure);
					this.pressureArray.shift();					
				});
			});
		}

	}
</script>
