<template>
    <div>        
        <h4 class="float-left mr-2">
            Runner Color:
        </h4>
        <color-node @setColor="setColor(0, $event)" :color="runnerColor" />
        <div class="clearfix"></div>

        <hr style="background-color: rgba(180, 180, 180, 0.2)" />        

        <button v-if="!activeModes.runningDot" @click="activateMode()" class="btn btn-info btn-sm">Activate</button>
        <button v-else @click="updateMode()" class="btn btn-success btn-sm">Update</button>
    </div>
</template>

<script>
    import ColorNode from '../../color-node.component';

    export default {
        props: ["activeModes"],

        components: {
            ColorNode
        },

        name: 'runningDot',

        data() {
            return {
                positivePressureStep: 50,
                negativePressureStep: 20,
                pressureDiffThreshold: 25,
                clockwiseRotation: true,
                defaultSpeed: 0.1,
                movePerStep: 0.01,
                runnerColor: {
                    red: 255,
                    green: 255,
                    blue: 255
                }
            }
        },

        created() {
            this.modeGetRequest();
        },

        methods: {
            modeGetRequest() {
                this.$http.get(this.$apiURL+"/mode/overlay/runningDotMode")
                .then(response => {
                    this.positivePressureStep = response.data.positivePressureStep,
                    this.negativePressureStep = response.data.negativePressureStep,
                    this.pressureDiffThreshold = response.data.pressureDiffThreshold,
                    this.clockwiseRotation = response.data.clockwiseRotation,
                    this.defaultSpeed = response.data.defaultSpeed,
                    this.movePerStep = response.data.movePerStep,
                    this.runnerColor = {
                        red: response.data.runnerColor.red,
                        green: response.data.runnerColor.green,
                        blue: response.data.runnerColor.blue
                    }
                });
            },

            modeSetRequest() {
                this.$http.post(this.$apiURL + "/mode/overlay/runningDotMode", {
                    positivePressureStep: this.positivePressureStep,
                    negativePressureStep: this.negativePressureStep,
                    pressureDiffThreshold: this.pressureDiffThreshold,
                    clockwiseRotation: this.clockwiseRotation,
                    defaultSpeed: this.defaultSpeed,
                    movePerStep: this.movePerStep,
                    runnerColor: {
                        red: this.runnerColor.red,
                        green: this.runnerColor.green,
                        blue: this.runnerColor.blue
                    }
                });
            },

            activateMode() {
                this.modeSetRequest();
                this.$emit("setMode", {mode: "runningDot", type: "activate"});
            },

            updateMode() {
                this.modeSetRequest();
            },

            setColor(key, e) {
                this.runnerColor = e;
            }
        }
    }
</script>