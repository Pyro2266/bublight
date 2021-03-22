<template>
    <div style="margin: 30px 0px; height: 110px">
        <canvas id="canvas" height=110></canvas>
    </div>
</template>

<script>
    import SockJS from 'sockjs-client';
	import Stomp from 'stompjs';

    export default {
        data() {
            return {
                colors: []
            }
        },

        watch: {
            colors() {
                this.drawCircle();
            }
        },

        created() {
            for(var i = 0; i < 16; i++) {
                this.colors.push({
                    red: 255,
                    green: 255,
                    blue: 255
                });
            }

            let ws = new SockJS(this.$apiURL + "/socket");
			let stompClient = Stomp.over(ws)
			stompClient.debug = null
			stompClient.connect({}, () => {
				stompClient.subscribe("/colorsSubscribe", (message) => {                    
					this.colors = JSON.parse(message.body);
				});
			});
        },

        mounted() {
            this.drawCircle();
        },

        methods: {
            drawCircle() {
                var canvas=document.getElementById("canvas");
                var ctx=canvas.getContext("2d");

                ctx.clearRect(0, 0, canvas.width, canvas.height);
                ctx.lineWidth=10;

                var PI=Math.PI;
                var PI2=PI*2;
                var cx=150;
                var cy=55;
                var radius=50;
                var arcRadians=PI2/16;
                var spacingRadians=PI2/70;
                var currentAngle=PI;

                for(const color of this.colors){
                    var startingAngle=currentAngle;
                    var endingAngle=startingAngle+arcRadians-spacingRadians;
                    ctx.beginPath();
                    ctx.arc(cx,cy,radius,startingAngle,endingAngle);
                    ctx.strokeStyle=this.rgbToHex(color);
                    ctx.stroke();
                    currentAngle+=arcRadians;
                }
            },

            rgbToHex(color) {
                var r = color.red.toString(16)
                var g = color.green.toString(16)
                var b = color.blue.toString(16)

                if (r.length == 1)
                    r = "0" + r
                if (g.length == 1)
                    g = "0" + g
                if (b.length == 1)
                    b = "0" + b

                return "#" + r + g + b
            },
        }
    }
</script>