<template>
    <div class="card">
        <div class="card-header card-header-tabs card-header-info">
            <div class="nav-tabs-navigation">
                <div class="nav-tabs-wrapper">
                    <span class="nav-tabs-title">Modes:</span>
                    <ul class="nav nav-tabs" data-tabs="tabs">
                        <li class="nav-item">
                            <a class="nav-link" :class="{ 'text-success active show': $store.state.activeModes.simpleColor }" href="#simpleColor" data-toggle="tab">
                                <i class="material-icons">color_lens</i> Simple Color
                                <div class="ripple-container"></div>
                                <div class="ripple-container"></div>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" :class="{ 'text-success active show': $store.state.activeModes.rainbow }" href="#rainbow" data-toggle="tab">
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
                <div class="tab-pane" :class="{ 'active show': $store.state.activeModes.simpleColor }" id="simpleColor">         
                    <simple-color />
                </div>
                <div class="tab-pane" :class="{ 'active show': $store.state.activeModes.rainbow }" id="rainbow">
                    <rainbow />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import SimpleColor from './modes/simpleColor.component';
    import Rainbow from './modes/rainbow.component';

    export default {        
        components: {
            SimpleColor,
            Rainbow
        },
    }
</script>