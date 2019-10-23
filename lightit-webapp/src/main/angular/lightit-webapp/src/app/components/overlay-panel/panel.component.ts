import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import $ from 'jquery';

@Component({
    selector: 'overlay-panel',
    templateUrl: './panel.component.html',
    styles: ['.card-title { float:left }', '.card-toolbar { float: right; }', '.swithLabel { color:#FFF; margin-right:10px; }'],
})
export class OverlayPanelComponent {
    positivePressureRange: number = 800
    negativePressureRange: number = 500
    defaultBrightness: number = 0.2
    minBrightness: number = 0.01
    maxBrightness: number = 0.5
    maxStep: number = 0.07

    constructor(private http: HttpClient) { 
        this.http.get(window.location.origin+"/mode/overlay/brightnessByPressureMode").subscribe((data: any) => {
            this.positivePressureRange = data["positivePressureRange"]
            this.negativePressureRange = data["negativePressureRange"]
            this.defaultBrightness = data["defaultBrightness"]
            this.minBrightness = data["minBrightness"]
            this.maxBrightness = data["maxBrightness"]
            this.maxStep = data["maxStep"]
        })
    }

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
            this.http.post(window.location.origin+"/mode/overlay/brightnessByPressureMode", this.options).subscribe();
        }
    }

    updatePressureMode() {
        this.http.post(window.location.origin+"/mode/overlay/brightnessByPressureMode", this.options).subscribe();
    }
}
