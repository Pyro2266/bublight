import { Component, Input } from '@angular/core';
import Chart from 'chart.js';

@Component({
    selector: 'graph',
    templateUrl: './graph.component.html'
})
export class GraphComponent {
    @Input('title') title: string
    @Input('subtitle') subtitle: string
    @Input('value') value: string

    chart_labels = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 ]
    chart = null
    gradientChartOptionsConfigurationWithTooltipBlue =  {
        maintainAspectRatio: false,
        legend: {
              display: false
        },
        tooltips: {
           backgroundColor: '#f5f5f5',
           titleFontColor: '#333',
           bodyFontColor: '#666',
           bodySpacing: 4,
           xPadding: 12,
           mode: "nearest",
           intersect: 0,
           position: "nearest"
        },
        responsive: true,
        scales:{
            yAxes: [{
                barPercentage: 1.6,
                gridLines: {
                    drawBorder: false,
                    color: 'rgba(29,140,248,0.0)',
                    zeroLineColor: "transparent",
                },
                ticks: {
                    suggestedMin: 0,
                    suggestedMax: 1000,
                    padding: 20,
                    fontColor: "#2380f7"
                }
            }],

           xAxes: [{
                barPercentage: 1.6,
                gridLines: {
                    drawBorder: false,
                    color: 'rgba(29,140,248,0.1)',
                    zeroLineColor: "transparent",
                },
                ticks: {
                    padding: 20,
                    fontColor: "#2380f7"
                }
            }]
        }
    }

    config = {
        type: 'line',
        data: {
            labels: this.chart_labels,
            datasets: [{                
                fill: true,
                borderColor: '#1d8cf8',
                borderWidth: 2,
                borderDash: [],
                borderDashOffset: 0.0,
                pointBackgroundColor: '#1d8cf8',
                pointBorderColor:'rgba(255,255,255,0)',
                pointHoverBackgroundColor: '#1d8cf8',
                pointBorderWidth: 20,
                pointHoverRadius: 4,
                pointHoverBorderWidth: 15,
                pointRadius: 4,
                data: [ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ],
            }]
        },
        options: this.gradientChartOptionsConfigurationWithTooltipBlue
    }

    createChart() {
        const ctx = document.getElementById("pressureChart")
        this.chart = new Chart(ctx, this.config)
    }

    ngAfterContentInit() {
        this.createChart();
    }

    ngOnChanges() {
        this.config.data.datasets[0].data.push(parseFloat(this.value))
        this.config.data.datasets[0].data.shift()
        if(this.chart != null)
            this.chart.update()
    }
}
