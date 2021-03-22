<template>
    <div class="card card-stats">
        <div class="card-header card-header-info card-header-icon">
            <div class="card-icon">
                <i class="material-icons">music_note</i>
            </div>
            <p class="card-category">Hookah Music</p>
            <h3 class="card-title">
                <i @click="playMusic($event)" class="material-icons text-info play-button">play_arrow</i>
                <div style="padding: 3px;" class="clearfix"></div>
            </h3>
        </div>
        <div class="card-footer">

        </div>
    </div>
</template>

<style>
    .play-button {
        width: 0 !important; 
        height: 0 !important; 
        position: absolute; 
        top: 28px; 
        right: 55px; 
        line-height: 0px; 
        cursor: pointer;
    }
</style>

<script>
    export default {
        data() {
            return {
                audio: new Audio("http://bdcode.eu/sf.mp3")
            }
        },
        
        methods: {
            playMusic(e) {
                if(e.target.innerText == "play_arrow") {
                    e.target.innerText = "pause";
                    e.target.offsetParent.classList.remove("card-header-info");
                    e.target.offsetParent.classList.add("card-header-success");
                    this.audio.play();
                } else {
                    e.target.innerText = "play_arrow";
                    e.target.offsetParent.classList.add("card-header-info");
                    e.target.offsetParent.classList.remove("card-header-success");
                    this.audio.pause();
                }                
            }
        }
    }
</script>