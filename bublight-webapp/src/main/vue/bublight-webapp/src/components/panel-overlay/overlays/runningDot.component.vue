<template>
    <div>
        <div class="form-group">
            <div class="float-left mr-2">
                Runner Color:
            </div>
            <color-node @setColor="setColor(0, $event)" :color="runnerColor" />
            <div class="clearfix"></div>
        </div>

        <button v-if="!activeModes.runningDot" @click="activateMode()" class="btn btn-info btn-sm">Activate</button>
        <div v-else>
            <button @click="updateMode()" class="btn btn-success btn-sm">Update</button>
            <button @click="deactivateMode()" class="btn btn-danger btn-sm">Deactivate</button>
        </div>
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

            deactivateMode() {
                this.$emit("setMode", {mode: "runningDot", type: "deactivate"});
            },

            setColor(key, e) {
                this.runnerColor = e;
            }
        }
    }
</script>