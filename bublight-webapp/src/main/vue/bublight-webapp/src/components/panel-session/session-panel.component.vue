<template>
    <div class="card card-stats">
        <div class="card-header card-header-icon" :class="{ 'card-header-success': sessionRunning, 'card-header-info': !sessionRunning }">
            <div class="card-icon">
                <img width="56" height="56" src="../../assets/hookah.svg">
            </div>
            <p class="card-category">Session</p>
            <h3 style="height: 36px;" class="card-title">
                <div class="row">
                    <div class="col-6">
                        <h3 class="m-0 text-success"><strong v-if="sessionRunning">{{ displayTimer }}</strong></h3>
                    </div>
                    <div class="col-6">
                        <span v-show="!sessionRunning">
                            <i rel="tooltip" title="Start Session" data-toggle="modal" data-target="#sessionModal" class="material-icons icon-small text-info mr-2 mt-1">play_circle</i>                            
                        </span>
                        <span v-show="sessionRunning">
                            <i rel="tooltip" @click="endSession()" title="End Session" class="material-icons icon-small text-info mr-2 mt-1">pause_circle</i>
                            <i rel="tooltip" title="Coal Changed" class="material-icons icon-small text-info mt-1 mr-2">change_circle</i>                        
                        </span>
                        <i rel="tooltip" title="Session Settings" class="material-icons icon-small text-info mt-1">settings</i>
                    </div>  
                </div>                                
            </h3>
        </div>
        <div class="card-footer">

        </div>
    </div>
</template>

<script>
    import moment from 'moment';

    export default {
        name: 'session-panel',

        data() {
            return {
                sessionTimer: moment(),
                timer: 0,
            }
        },

        computed: {
            sessionRunning() {
                return this.$store.state.sessionRunning;
            },

            sessionStart() {
                return this.$store.state.sessionStart
            },

            displayTimer: {
                get() {
                    return new Date(this.timer * 1000).toISOString().substr(14, 5);
                },

                set(val) {
                    this.timer = val;
                }
            }
        },

        mounted() {
            setInterval(() => {
                if(this.sessionRunning) {
                    this.timer = moment().diff(this.sessionStart, 'seconds');
                }
            }, 100);
        },

        methods: {
            endSession() {
                this.$store.commit("END_SESSION");
            }
        }
    }
</script>