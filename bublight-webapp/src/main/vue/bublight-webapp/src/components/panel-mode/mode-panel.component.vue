<template>
    <div class="card">
        <div class="card-header card-header-tabs card-header-info">
            <div class="nav-tabs-navigation">
                <div class="nav-tabs-wrapper">
                    <span class="nav-tabs-title">Modes:</span>
                    <ul class="nav nav-tabs" data-tabs="tabs">
                        <li class="nav-item">
                            <a class="nav-link" :class="{'text-success active show': activeModes.simpleColor}" href="#simpleColor" data-toggle="tab">
                                <i class="material-icons">color_lens</i> Simple Color
                                <div class="ripple-container"></div>
                                <div class="ripple-container"></div>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" :class="{'text-success active show': activeModes.rainbow}" href="#rainbow" data-toggle="tab">
                                <i class="material-icons">looks</i> Rainbow
                                <div class="ripple-container"></div>
                                <div class="ripple-container"></div>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="card-body">
            <div class="tab-content">
                <div class="tab-pane" :class="{'active show': activeModes.simpleColor}" id="simpleColor">         
                    <div class="color-nodes-list">
                        <color-node v-for="(color, key) in colors" :key="key" @setColor="setColor(key, $event)" :color="color" />
                    </div>     
                    <br />
                    <hr style="background-color: rgba(180, 180, 180, 0.2)" />
                    <button v-if="!activeModes.simpleColor" @click="activateSimpleColorMode()" class="btn btn-info btn-sm">Activate</button>
                    <button v-else @click="updateSimpleColorMode()" class="btn btn-success btn-sm">Update</button>
                </div>
                <div class="tab-pane" :class="{'active show': activeModes.rainbow}" id="rainbow">
                    <div class="form-group">
                        <div>Speed: {{ parseInt(this.step / (0.01 / 100)) }}%</div>
                        <div>
                            <input v-model.number="step" name="step" type="range" max="0.01" min="0.0001" step="0.0001" class="custom-range" />
                        </div>
                    </div>
                    <button v-if="!activeModes.rainbow" @click="activateRainbowMode()" class="btn btn-info btn-sm">Activate</button>
                    <button v-else @click="updateRainbowMode()" class="btn btn-success btn-sm">Update</button>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
    .custom-range {
        display: block;
        width: 100%;
    }
</style>

<script>
    import Vue from 'vue';
    import ColorNode from '../color-node.component';

    export default {
        props: ["activeModes"],

        components: {
            ColorNode
        },

        data: () => {
            return {
                step: 0.0005,
                colors: [],
            }
        },

        created() {
            this.simpleColorModeGetRequest();
            this.rainbowModeGetRequest();
            
        },

        methods: {
            simpleColorModeGetRequest() {
                this.$http.get(this.$apiURL + "/mode/base/simpleColorMode/")
                .then(response => {
                    this.colors = response.data.colors;
                });
            },

            simpleColorModeSetRequest() {
                this.$http.post(this.$apiURL + "/mode/base/simpleColorMode", {
                    "colors": this.colors
                });
            },

            rainbowModeGetRequest() {
                this.$http.get(this.$apiURL + "/mode/base/simpleRainbowMode")
                .then(response => {
                    this.step = response.data.step;
                });
            },

            rainbowModeSetRequest() {
                this.$http.post(this.$apiURL + "/mode/base/simpleRainbowMode", {
                    "step": this.step
                });
            },

            activateSimpleColorMode() {
                this.simpleColorModeSetRequest();
                this.$emit("setMode", {mode: "simpleColor", type: "activate"});
            },

            activateRainbowMode() {
                this.rainbowModeSetRequest();
                this.$emit("setMode", {mode: "rainbow", type: "activate"});
            },     
            
            updateSimpleColorMode() {
                this.simpleColorModeSetRequest();
            },

            updateRainbowMode() {
                this.rainbowModeSetRequest();
            },

            setColor(index, color) {
                Vue.set(this.colors, index, color);
            }
        }
    }
</script>