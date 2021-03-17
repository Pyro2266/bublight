import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import $ from 'jquery';


@Component({
  selector: 'led-panel',
  templateUrl: './panel.component.html'
})

export class LedPanelComponent {

    constructor(private http: HttpClient) {}

    ledSwitch(event:any) {
        var switchEl = $(event.target)
        
        if(switchEl.is(":checked"))
            this.http.post(window.location.origin+"/led/start", {}).subscribe();
        else
            this.http.post(window.location.origin+"/led/stop", {}).subscribe();
        
    }
}
