import { Component, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

@Component({
    selector: 'mode-panel',
    templateUrl: './panel.component.html',    
})


export class ModePanelComponent {
    @Input('title') title: string
    @Input('value') value: number
    @Input('icon') icon: string

    httpOptions = {
        headers: new HttpHeaders({
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Credentials': 'true',
            'Content-Type': 'application/json;charset=UTF-8'
        })
    }

    colors = [
        {
            "red": 0,
            "green": 0,
            "blue": 0
        },
        {
            "red": 10,
            "green": 10,
            "blue": 10
        },
        {
            "red": 20,
            "green": 20,
            "blue": 20
        },
        {
            "red": 30,
            "green": 30,
            "blue": 30
        },
        {
            "red": 40,
            "green": 40,
            "blue": 40
        },
        {
            "red": 50,
            "green": 50,
            "blue": 50
        },
        {
            "red": 60,
            "green": 60,
            "blue": 60
        },
        {
            "red": 70,
            "green": 70,
            "blue": 70
        },
        {
            "red": 80,
            "green": 80,
            "blue": 80
        },
        {
            "red": 90,
            "green": 90,
            "blue": 90
        },
        {
            "red": 100,
            "green": 100,
            "blue": 100
        },
        {
            "red": 110,
            "green": 110,
            "blue": 110
        },
        {
            "red": 120,
            "green": 120,
            "blue": 120
        },
        {
            "red": 130,
            "green": 130,
            "blue": 130
        },
        {
            "red": 140,
            "green": 140,
            "blue": 140
        },
        {
            "red": 150,
            "green": 150,
            "blue": 150
        }
    ]

    constructor(private http: HttpClient) { }

    selectValue( newValue : any ) {
        var color = this.hexToRgb(newValue.color)
        this.colors[0]["red"] = color[0]
        this.colors[0]["green"] = color[1]
        this.colors[0]["blue"] = color[2]
    }

    hexToRgb( hex : string) {
        hex = hex.replace('#','')
        var r = parseInt(hex.substring(0,2), 16)
        var g = parseInt(hex.substring(2,4), 16)
        var b = parseInt(hex.substring(4,6), 16)
        
        return [r, g, b]
    }


    modeSwitch(event: any, mode: number) {
        switch(mode) {
            case 0:
                this.http.post("http://localhost:8080/mode/base/simpleColorMode", this.colors, this.httpOptions).subscribe();
                break;
            case 1:
                this.http.post("http://localhost:8080/mode/base/simpleRainbowMode", {
                    "step": 0.0005
                }, this.httpOptions).subscribe();
                break;
        }
    }
}
