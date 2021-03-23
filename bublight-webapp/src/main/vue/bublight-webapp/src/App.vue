<template>
	<div class="row">
		<div class="col-lg-3 col-md-6">
			<pressure-panel title="Pressure" :value="pressure" icon="icon-app" />
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
			<chart title="Pressure Chart" :pressureArray="pressureArray" :value="pressure" subtitle="Realtime" />
		</div>
		<div class="col-lg-6 col-md-12">
			<mode-panel :activeModes="activeModes" @setMode="setMode($event)" />
		</div>
		<div class="col-lg-6 col-md-12">
			<overlay-panel :activeModes="activeModes" @setMode="setMode($event)" />
		</div>
	</div>
</template>

<style>
	.nav-tabs .nav-item .nav-link.text-success {
		color: #9c27b0 !important;
	}

	.custom-range {
        display: block;
        -webkit-appearance: none;
        width: 100%;
        height: 6px;
        border-radius: 5px;  
        background: #FFF;
        outline: none;
        opacity: 0.7;
        -webkit-transition: .2s;
        transition: opacity .2s;
    }

    .custom-range::-webkit-slider-thumb {
        -webkit-appearance: none;
        appearance: none;
        width: 18px;
        height: 18px;
        border-radius: 50%; 
        background: #10dcf7;
        cursor: pointer;
        border: none;
        box-shadow: 0 4px 10px 0px rgba(0, 0, 0, 0.14), 0 7px 5px -5px rgba(0, 188, 212, 0.4);
    }

    .custom-range::-moz-range-thumb {
        width: 18px;
        height: 18px;
        border-radius: 50%;
        background: #10dcf7;
        cursor: pointer;
        border: none;
        box-shadow: 0 4px 10px 0px rgba(0, 0, 0, 0.14), 0 7px 5px -5px rgba(0, 188, 212, 0.4);
    }
</style>

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
