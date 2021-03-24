<template>
    <div :style="{'background-color': nodeColor}" :data-color="nodeColor" class="color-node"></div>
</template>

<style>
    .color-node { 
        padding: 5px; 
        width: 20px; 
        height: 20px; 
        border-radius: 50%; 
        margin-right: 10px; 
        background-color: #FFF;
        float: left;
        cursor:pointer; 
    }

    .color-picker.no-alpha .color-picker\:a {
        display: none;
    }
</style>

<script>
    export default {
        props: ["color"],

        name: 'color-node',

        data() {
            return {
                nodeColor: this.rgbToHex(this.color)
            }
        },

        watch: {
            color(value) {
                this.nodeColor = this.rgbToHex(value);
            }
        },

        mounted() {
            let picker = new window.CP(this.$el);

            picker.self.classList.add('no-alpha');
            picker.on("change", (r, g, b) => {
                this.$emit("setColor", {red: r, green: g, blue: b});
            });            
        },

        methods: {
            rgbToHex(color) {
                var r = color.red.toString(16)
                var g = color.green.toString(16)
                var b = color.blue.toString(16)

                if (r.length == 1)
                    r = "0" + r
                if (g.length == 1)
                    g = "0" + g
                if (b.length == 1)
                    b = "0" + b

                return "#" + r + g + b
            },
        }
    }
</script>