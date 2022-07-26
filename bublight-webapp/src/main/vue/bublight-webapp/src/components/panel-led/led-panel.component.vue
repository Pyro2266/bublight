<template>
    <div class="card card-stats">
        <div class="card-header card-header-info card-header-icon">
            <div class="card-icon">
                <i class="material-icons">wb_incandescent</i>
            </div>
            <p class="card-category">LEDs Switch</p>
            <h3 class="card-title">
                <input v-model="led" type="checkbox" class="led-switch" /> 
            </h3>
        </div>
        <div class="card-footer">

        </div>
    </div>
</template>

<script>
    import { mapFields } from '../../helpers';

    export default {        
        data: function() {
            return {
                switch: null
            }
        },

        computed: {
            ...mapFields({
                fields: [
                    'led',
                ],
                base: 'activeModes',
                mutation: "UPDATE_MODE"
            }),
        },

        mounted() {
            var ledSwitch = document.querySelector('.led-switch');            
			this.switch = new window.Switchery(ledSwitch, { 
				color: '#25b1c3'
			});                                
        },

        watch: {
            led(value) {                
                if(value) {
                    this.$http.post(this.$apiURL+"/led/start", {});                    
                } else {
                    this.$http.post(this.$apiURL+"/led/stop", {});                    
                }
                
                if(!this.switch.isChecked()) {
                    this.switch.setPosition(value);
                }                                
            },
        }
    }
</script>