import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  serverUrl = window.location.origin+'/socket'
  title = 'BubLight'
  stompClient;
  pressure = 0
  pressureArray = []

  constructor(private http: HttpClient){
    for(var i = 0; i < 50; i++) {
      this.pressureArray.push(0)
    }

    this.initializeWebSocketConnection()
  }

  initializeWebSocketConnection(){
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws)
    this.stompClient.debug = null
    let that = this
    this.stompClient.connect({}, function(frame) {
      that.stompClient.subscribe("/pressureSubscribe", (message) => {
        const pressure = (Math.round(message.body))
        that.pressure = pressure
        that.pressureArray.push(pressure)
        that.pressureArray.shift()
        that.pressureArray = that.pressureArray.slice()
      });
    });
  }
}
