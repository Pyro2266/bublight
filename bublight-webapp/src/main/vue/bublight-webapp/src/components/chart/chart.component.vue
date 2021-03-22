<template>
    <div class="card card-chart">
        <div class="card-header pb-0">
            <div class="row">
                <div class="col-sm-6 text-left">
                    <h5 class="card-category">{{ subtitle }}</h5>
                    <h2 class="card-title">{{ title }}</h2>
                </div>
            </div>
        </div>
        <div class="card-body pt-0">
            <div class="chart-area">
                <canvas id="pressureChart"></canvas>
            </div>
        </div>
    </div>
</template>

<script>
    import Chart from 'chart.js';
    export default {    
        props: ["title", "subtitle", "value", "pressureArray"],

        data() {
            return {
                chart_labels: [],
                chart: null,                
                config: {
                    type: 'line',                    
                    data: {
                        labels: [],
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
                    options: {
                        maintainAspectRatio: false,
                        legend: {
                            display: false
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
                                    fontColor: "#2380f7",
                                    stepSize: 250
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
                                    display: false                                  
                                }
                            }]
                        }
                    }
                }
            }
        },

        watch: {
            pressureArray(value) {
                this.config.data.datasets[0].data = value;
                if(this.chart != null) {
                    this.chart.update();
                }
            }
        },

        created() {
            for(var i = 0; i < 50; i++) {
                this.config.data.labels.push(i);
            }

            Chart.defaults.global.tooltips.enabled = false;
        },

        mounted() {
            const ctx = document.getElementById("pressureChart");
            this.chart = new Chart(ctx, this.config);
        },
    }
</script>