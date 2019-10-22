import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import $ from 'jquery';

@Component({
    selector: 'overlay-panel',
    templateUrl: './panel.component.html',
    styles: ['.card-title { float:left }', '.card-toolbar { float: right; }', '.swithLabel { color:#FFF; margin-right:10px; }'],
})
export class OverlayPanelComponent {

    constructor(private http: HttpClient) { }

    options = {
        "positivePressureRange": 800,
        "negativePressureRange": 500,
        "defaultBrightness": 0.2,
        "minBrightness": 0.01,
        "maxBrightness": 0.5,
        "maxStep": 0.07
    }

    enablePressureMode(event: any) {
        var switchEl = $(event.target)
        
        if(switchEl.is(":checked")) {
            this.http.post("http://localhost:8080/mode/overlay/brightnessByPressureMode", this.options).subscribe();
        }
    }
}
