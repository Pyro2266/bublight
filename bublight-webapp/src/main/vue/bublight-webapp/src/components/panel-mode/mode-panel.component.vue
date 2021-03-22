<template>
    <div class="card">
        <div class="card-header card-header-tabs card-header-info">
            <div class="nav-tabs-navigation">
                <div class="nav-tabs-wrapper">
                    <span class="nav-tabs-title">Modes:</span>
                    <ul class="nav nav-tabs" data-tabs="tabs">
                        <li class="nav-item">
                            <a class="nav-link active show" href="#simpleColor" data-toggle="tab">
                                <i class="material-icons">color_lens</i> Simple Color
                                <div class="ripple-container"></div>
                                <div class="ripple-container"></div>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#rainbow" data-toggle="tab">
                                <i class="material-icons">toys</i> Rainbow
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
                <div class="tab-pane active show" id="simpleColor">         
                    <div class="color-nodes-list">
                        <color-node v-for="(color, key) in colors" :key="key" @setColor="setColor(key, $event)" :color="color" />
                    </div>     
                    <br />
                    <hr style="background-color: rgba(180, 180, 180, 0.2)" />
                    <button @click="modeSwitch(0)" class="btn btn-info btn-sm">Activate</button>
                </div>
                <div class="tab-pane" id="rainbow">
                    <div class="form-group">
                        <label for="step" class="control-label">Step:</label>
                        <input v-model.number="step" name="step" max="1" min="0.0001" step="0.0001" class="form-control" />
                    </div>
                    <button @click="modeSwitch(1)" class="btn btn-info btn-sm">Activate</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import Vue from 'vue';
    import ColorNode from './color-node.component';

    export default {
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
            this.$http.get(this.$apiURL + "/mode/base/simpleColorMode/")
            .then(response => {
                this.colors = response.data.colors;
            });

            this.$http.get(this.$apiURL + "/mode/base/simpleRainbowMode")
            .then(response => {
                this.step = response.data.step;
            });

            for(var i = 0; i < 16; i++) {
                this.colors.push({
                    red: 10,
                    green: 10,
                    blue: 10
                })
            }
        },

        methods: {
            modeSwitch(mode) {
                switch(mode) {
                    case 0:
                        this.$http.post(this.$apiURL + "/mode/base/simpleColorMode", {
                            "colors": this.colors
                        });
                        break;
                    case 1:
                        this.$http.post(this.$apiURL + "/mode/base/simpleRainbowMode", {
                            "step": this.step
                        });
                        break;
                }
            },

            setColor(index, color) {
                Vue.set(this.colors, index, color);
            }
        }
    }
</script>