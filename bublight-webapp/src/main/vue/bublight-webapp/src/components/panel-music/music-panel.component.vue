<template>
    <div class="card card-stats">
        <div class="card-header card-header-info card-header-icon">
            <div class="card-icon">
                <i class="material-icons">music_note</i>
            </div>
            <p class="card-category">Hookah Music</p>
            <h3 style="height: 36px;" class="card-title">
                <i @click="playMusic($event)" class="material-icons text-info icon-small mr-2 mt-1">play_arrow</i>                
            </h3>
        </div>
        <div class="card-footer">

        </div>
    </div>
</template>

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