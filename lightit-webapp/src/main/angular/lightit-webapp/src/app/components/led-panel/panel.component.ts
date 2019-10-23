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
            this.http.post("http://127.0.0.1:8080/led/start", {}).subscribe();
        else
            this.http.post("http://127.0.0.1:8080/led/stop", {}).subscribe();
        
    }
}
