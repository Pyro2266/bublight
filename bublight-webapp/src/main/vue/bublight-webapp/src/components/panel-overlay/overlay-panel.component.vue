<template>
    <div class="card">
        <div class="card-header card-header-tabs card-header-info">
            <div class="nav-tabs-navigation">
                <div class="nav-tabs-wrapper">
                    <span class="nav-tabs-title">Overlays:</span>
                    <ul class="nav nav-tabs" data-tabs="tabs">
                        <li class="nav-item">
                            <a class="nav-link" :class="{'text-success active show': $store.state.activeModes.brightness }" href="#brightnessByPressure" data-toggle="tab">
                                <i class="material-icons">light_mode</i> Brightness
                                <div class="ripple-container"></div>
                                <div class="ripple-container"></div>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" :class="{'text-success active show': $store.state.activeModes.runningDot }" href="#runningDot" data-toggle="tab">
                                <i class="material-icons">hdr_strong</i> Running Dot
                                <div class="ripple-container"></div>
                                <div class="ripple-container"></div>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="disableOverlay">
                <button class="btn btn-sm btn-secondary" @click="disableOverlay()">Disable Overlay</button>
            </div>
        </div>
        <div class="card-body">
            <div class="tab-content">
                <div class="tab-pane" :class="{'active show': $store.state.activeModes.brightness }" id="brightnessByPressure">         
                    <brightness />
                </div>
                <div class="tab-pane" :class="{'active show': $store.state.activeModes.runningDot }" id="runningDot">
                    <running-dot />
                </div>
            </div>            
        </div>
    </div>
</template>

<style scoped>
    .card-title { 
        float:left 
    }

    .card-toolbar { 
        float: right; 
    }

    .swithLabel { 
        color:#FFF; 
        margin-right:10px; 
    }
</style>

<script>
    import Brightness from './overlays/brightness.component';
    import RunningDot from './overlays/runningDot.component';

    export default {                

        components: { 
            Brightness,
            RunningDot
        },

        methods: {
            disableOverlay() {
                this.$http.post(this.$apiURL + "/mode/overlay/disable", {});
                //this.$emit("setMode", {type: 'deactivate', mode: 'overlay'});
                this.$store.dispatch('deactivateMode', 'overlay');
            }
        }
    }
</script>
                