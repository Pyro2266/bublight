import { Component, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

@Component({
    selector: 'mode-panel',
    templateUrl: './panel.component.html',    
})


export class ModePanelComponent {
    step: number = 0.0005

    httpOptions = {
        headers: new HttpHeaders({
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Credentials': 'true',
            'Content-Type': 'application/json;charset=UTF-8'
        })
    }

    colors = []

    constructor(private http: HttpClient) {
        this.http.get(window.location.origin+"/mode/base/simpleColorMode/").subscribe((data: any) => this.colors = data["colors"])
        this.http.get(window.location.origin+"/mode/base/simpleRainbowMode").subscribe((data: any) => this.step = data["step"])

        for(var i = 0; i < 16; i++) {
            this.colors.push({
                red: 10,
                green: 10,
                blue: 10
            })
        }
    }

    selectValue( newValue : any ) {
        var index = newValue.elementRef.nativeElement.attributes[0].nodeValue
        var color = this.hexToRgb(newValue.color)
        this.colors[index]["red"] = color[0]
        this.colors[index]["green"] = color[1]
        this.colors[index]["blue"] = color[2]
    }

    hexToRgb( hex : string ) {
        hex = hex.replace('#','')
        var r = parseInt(hex.substring(0,2), 16)
        var g = parseInt(hex.substring(2,4), 16)
        var b = parseInt(hex.substring(4,6), 16)
        
        return [r, g, b]
    }

    RGBToHex(color) {
        var r = color.red.toString(16)
        var g = color.green.toString(16)
        var b = color.blue.toString(16)

        if (r.length == 1)
            r = "0" + r
        if (g.length == 1)
            g = "0" + g
        if (b.length == 1)
            b = "0" + b

        return "#" + r + g + b
    }

    modeSwitch(mode: number) {
        switch(mode) {
            case 0:
                this.http.post(window.location.origin+"/mode/base/simpleColorMode", {"colors": this.colors}, this.httpOptions).subscribe();
                break;
            case 1:
                this.http.post(window.location.origin+"/mode/base/simpleRainbowMode", {
                    "step": this.step
                }, this.httpOptions).subscribe();
                break;
        }
    }
}
