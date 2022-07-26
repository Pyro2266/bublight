<template>
    <div>
        <h4 class="text-muted">Speed: {{ parseInt(this.step / (0.01 / 100)) }}%</h4>
        <div class="mt-2">
            <input v-model.number="step" name="step" type="range" max="0.01" min="0.0001" step="0.0001" class="custom-range" />
        </div>
        <hr style="background-color: rgba(180, 180, 180, 0.2)" />
        <button v-if="!$store.state.activeModes.rainbow" @click="activateMode()" class="btn btn-info btn-sm">Activate</button>
        <button v-else @click="updateMode()" class="btn btn-success btn-sm">Update</button>
    </div>
</template>

<script>
    export default {        
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
                this.$store.dispatch('activateMode', 'simpleRainbow');                
            },

            updateMode() {
                this.modeSetRequest();
            },
        }
    }
</script>