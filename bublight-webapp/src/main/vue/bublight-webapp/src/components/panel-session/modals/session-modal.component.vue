<template>
    <div class="modal fade" id="sessionModal" tabindex="-1" aria-labelledby="sessionModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="sessionModalLabel">Start session</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form>
                        <h5 class="mb-4">Your setup:</h5>
                        <div class="form-group">
                            <label for="hookah" class="control-label">Hookah</label>
                            <input name="hookah" type="text" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label for="bowl" class="control-label">Bowl</label>
                            <input name="bowl" type="text" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label for="hms" class="control-label">HMS</label>
                            <input name="hms" type="text" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label for="coal" class="control-label">Coal</label>
                            <input name="coal" type="text" class="form-control" />
                        </div>
                        <h5>Tobacco: <div @click="addTabacco()" class="float-right addTabacco" rel="tooltip" title="Add Tabacco"><i class="material-icons text-dark">add</i></div></h5>                         
                        <div class="form-group" v-for="(item, key) in tabacco" :key="key">                            
                            <div class="row">
                                <div class="col-7">
                                    <input v-model="item.name" name="name" placeholder="Tabacco" type="text" class="form-control" />
                                </div>
                                <div class="col-5">
                                    <div class="input-group">
                                        <input v-model="item.percentage" name="percentage" placeholder="Percentage" type="text" class="form-control" />
                                        <span class="input-group-btn">
                                            <button @click="removeTabacco(key)" type="button" class="btn btn-fab btn-sm btn-round btn-secondary addTabacco">
                                                <i class="material-icons">close</i>
                                            </button>
                                        </span>
                                    </div>
                                </div>
                            </div>                            
                        </div>                        
                    </form>
                </div>					
                <div class="modal-footer">
                    <button @click="startSesion()" type="button" class="btn btn-sm btn-info">Start</button>                    
                </div>
            </div>
        </div>
    </div>
</template>

<style lang="scss" scoped>
    .dark-edition .modal .form-control {
        color: #333;
        background-image: linear-gradient(to top, #9c27b0 2px, rgba(156, 39, 176, 0) 2px), linear-gradient(to top, #d2d2d2 1px, rgba(210, 210, 210, 0) 1px);
    }

    .addTabacco {
        cursor: pointer;
    }
</style>

<script>
    import moment from 'moment';
    
    export default {
        name: 'session-modal',

        data() {
            return {
                tabacco: []
            }
        },

        methods: {
            addTabacco() {
                this.tabacco.push({
                    name: "",
                    percentage: null,
                })
            },

            removeTabacco(key) {
                this.tabacco.splice(key, 1);
            },

            startSesion() {
                this.$store.commit("START_SESSION", moment());
                window.$(this.$el).modal('hide');
            }
        }
    }
</script>