<template>
    <div>
        <div class="form-group">
            <label for="positivePressureRange" class="control-label">Positive Pressure Range:</label>
            <input v-model.number="positivePressureRange" name="positivePressureRange" max="1000" min="0" step="1" class="form-control" />
        </div>
        <div class="form-group">
            <label for="negativePressureRange" class="control-label">Negative Pressure Range:</label>
            <input v-model.number="negativePressureRange" name="negativePressureRange" max="1000" min="0" step="1" class="form-control" />
        </div>
        <div class="form-group">
            <label for="defaultBrightness" class="control-label">Default Brightness:</label>
            <input v-model.number="defaultBrightness" name="defaultBrightness" max="1" min="0.1" step="01" class="form-control" />
        </div>
        <div class="form-group">
            <label for="minBrightness" class="control-label">Min Brightness:</label>
            <input v-model.number="minBrightness" name="minBrightness" max="1" min="0.01" step="0.01" class="form-control" />
        </div>
        <div class="form-group">
            <label for="maxBrightness" class="control-label">Max Brightness:</label>
            <input v-model.number="maxBrightness" name="maxBrightness" max="1" min="0.01" step="0.01" class="form-control" />
        </div>
        <div class="form-group">
            <label for="maxStep" class="control-label">Max Step:</label>
            <input v-model.number="maxStep" name="maxStep" max="1" min="0.01" step="0.01" class="form-control" />
        </div>
        <button v-if="!$store.state.activeModes.brightness" @click="activateMode()" class="btn btn-info btn-sm">Activate</button>
        <button v-else @click="updateMode()" class="btn btn-success btn-sm">Update</button>
    </div>
</template>

<script>
    export default {        

        name: 'brightness',

        data() {
            return {
                positivePressureRange: 800,
                negativePressureRange: 500,
                defaultBrightness: 0.2,
                minBrightness: 0.01,
                maxBrightness: 1,
                maxStep: 0.07,   
            }
        },

        created() {
            this.modeGetRequest()
        },

        methods: {
            modeGetRequest() {
                this.$http.get(this.$apiURL+"/mode/overlay/brightnessByPressureMode")
                .then(response => {
                    this.positivePressureRange = response.data.positivePressureRange
                    this.negativePressureRange = response.data.negativePressureRange
                    this.defaultBrightness = response.data.defaultBrightness
                    this.minBrightness = response.data.minBrightness
                    this.maxBrightness = response.data.maxBrightness
                    this.maxStep = response.data.maxStep
                });
            },

            modeSetRequest() {
                this.$http.post(this.$apiURL + "/mode/overlay/brightnessByPressureMode", {
                    positivePressureRange: this.positivePressureRange,
                    negativePressureRange: this.negativePressureRange,
                    defaultBrightness: this.defaultBrightness,
                    minBrightness: this.minBrightness,
                    maxBrightness: this.maxBrightness,
                    maxStep: this.maxStep
                });
            },

            activateMode() {
                this.modeSetRequest();
                this.$store.dispatch('activateMode', 'brightnessByPressure');                
            },

            updateMode() {
                this.modeSetRequest();
            }
        }
    }
</script>