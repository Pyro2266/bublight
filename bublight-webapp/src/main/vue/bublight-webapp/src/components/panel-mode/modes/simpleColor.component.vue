<template>
    <div>
        <div class="color-nodes-list">
            <color-node v-for="(color, key) in colors" :key="key" @setColor="setColor(key, $event)" :color="color" />
        </div>     
        <br />
        <hr style="background-color: rgba(180, 180, 180, 0.2)" />
        <button v-if="!activeModes.simpleColor" @click="activateMode()" class="btn btn-info btn-sm">Activate</button>
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

        name: 'simpleColor',

        data() {
            return {
                colors: [],
            }
        },

        created() {
            this.modeGetRequest();
        },

        methods: {
            modeGetRequest() {
                this.$http.get(this.$apiURL + "/mode/base/simpleColorMode/")
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
                this.$emit("setMode", {mode: "simpleColor", type: "activate"});
            },

            updateMode() {
                this.modeSetRequest();
            },

            setColor(index, color) {
                this.$set(this.colors, index, color);
            }
        }
    }
</script>