import { Component } from '@angular/core';
import $ from 'jquery';

@Component({
  selector: 'music-panel',
  templateUrl: './panel.component.html',
  styles: ['.play-button { width: 0 !important; height: 0 !important; position: absolute; top: 28px; right: 55px; line-height: 0px; cursor: pointer; }'],
})

export class MusicPanelComponent {

    audio = new Audio("http://bdcode.eu/sf.mp3");

    playMusic(event:any) {
        var el = $(event.target)
        if(el.text() == "play_arrow") {
            el.text("pause")
            el.parent().parent().removeClass("card-header-info").addClass("card-header-success")
            this.audio.play()
        } else {
            el.text("play_arrow")
            el.parent().parent().removeClass("card-header-success").addClass("card-header-info")
            this.audio.pause()
        }
        
    }
}
