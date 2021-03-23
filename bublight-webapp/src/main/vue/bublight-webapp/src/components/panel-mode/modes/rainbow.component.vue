<template>
    <div>
        <div class="form-group">
            <div>Speed: {{ parseInt(this.step / (0.01 / 100)) }}%</div>
            <div>
                <input v-model.number="step" name="step" type="range" max="0.01" min="0.0001" step="0.0001" class="custom-range" />
            </div>
        </div>
        <button v-if="!activeModes.rainbow" @click="activateMode()" class="btn btn-info btn-sm">Activate</button>
        <button v-else @click="updateMode()" class="btn btn-success btn-sm">Update</button>
    </div>
</template>

<style scoped>
    .custom-range {
        display: block;
        width: 100%;
    }
</style>

<script>
    export default {
        props: ["activeModes"],

        name: 'rainbow',

        data() {
            return {
               step: 0.0005,
            }
        },

        created() {
            this.modeGetRequest();
        },

        methods: {
            modeGetRequest() {
                this.$http.get(this.$apiURL + "/mode/base/simpleRainbowMode")
                .then(response => {
                    this.step = response.data.step;
                });
            },

            modeSetRequest() {
                this.$http.post(this.$apiURL + "/mode/base/simpleRainbowMode", {
                    "step": this.step
                });
            },

            activateMode() {
                this.modeSetRequest();
                this.$emit("setMode", {mode: "rainbow", type: "activate"});
            },

            updateMode() {
                this.modeSetRequest();
            },
        }
    }
</script>