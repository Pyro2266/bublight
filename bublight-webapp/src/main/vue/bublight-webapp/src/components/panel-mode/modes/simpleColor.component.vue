<template>
    <div>
        <h4 class="text-muted">Pick LED colors:</h4>
        <div class="color-nodes-list">
            <color-node v-for="(color, key) in colors" :key="key" @setColor="setColor(key, $event)" :color="color" />
        </div>    
        <div class="clearfix"></div>

        <h4 class="mt-2">Set all colors:</h4>
        <div class="form-group">            
            <color-node @setColor="setGlobalColor(0, $event)" :color="globalColor" />
        </div>
        <div class="clearfix"></div>
                
        <hr style="background-color: rgba(180, 180, 180, 0.2)" />
        <button v-if="!$store.state.activeModes.simpleColor" @click="activateMode()" class="btn btn-info btn-sm">Activate</button>
        <button v-else @click="updateMode()" class="btn btn-success btn-sm">Update</button>
    </div>
</template>

<script>
    import ColorNode from '../../color-node.component';

    export default {        
        components: {
            ColorNode
        },

        name: 'simpleColor',

        data() {
            return {
                colors: [],
                globalColor: {
                    red: 255,
                    green: 255,
                    blue: 255
                }
            }
        },

        created() {
            this.modeGetRequest().then(() => {
                let isSame = true;
                let colorI = this.colors[0];
                this.colors.forEach((color) => {                    
                    isSame = JSON.stringify(color) == JSON.stringify(colorI);
                    colorI = color;
                });

                if(isSame) {
                    this.globalColor = colorI;
                }
            });            
        },

        methods: {
            async modeGetRequest() {
                await this.$http.get(this.$apiURL + "/mode/base/simpleColorMode/")
                .then(response => {
                    this.colors = response.data.colors;
                });
            },

            modeSetRequest() {
                this.$http.post(this.$apiURL + "/mode/base/simpleColorMode", {
                    "colors": this.colors
                });
            },

            activateMode() {
                this.modeSetRequest();
                this.$store.dispatch('activateMode', 'simple');                      
            },

            updateMode() {
                this.modeSetRequest();
            },

            setColor(index, color) {
                this.$set(this.colors, index, color);
            },

            setGlobalColor(index, e) {
                this.globalColor = e;
                this.colors.forEach((color, index) => {
                    this.$set(this.colors, index, e);
                });
            }
        }
    }
</script>