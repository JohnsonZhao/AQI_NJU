/**
 * Created by lulei on 16/4/6.
 * js to create charts
 * using amcharts.js
 */

define(function (require, exports) {
    exports.makechart = function (location, city) {
        $.getJSON('getCityAQIHistory', {
                city: city.split('_')[0]
            }, function (data) {
                var result = [];
                fomulateData(data, result);
                AmCharts.makeChart(location, {
                    type: 'serial',
                    theme: 'chalk',
                    dataProvider: result,
                    //color: '#697E8C',
                    titles: [
                        {
                            text: '24小时AQI指数实时变化',
                            color: '#697E8C'
                        }
                    ],
                    categoryField: 'time',
                    startDuration: 1,
                    graphs: [
                        {
                            type: 'line',
                            title: 'aqi',
                            valueField: 'aqi',
                            lineThickness: 2,
                            fillAlphas: 0,
                            bullet: "round",
                            balloonText: "<b>aqi:[[aqi]]</b>"
                        }
                    ],
                    categoryAxis: {
                        gridPosition: "start"
                    },
                    valueAxes: [{
                        title: "aqi指数"
                    }],
                    legend: {
                        useGraphSettings: true
                    }
                });
                //makeCharts(data, 'chartDiv','chalk','#282828');
            }
        );

    };

    var chartData = [];
    exports.historyChart = function (url, id, dayNum) {
        var city = id.split('_')[0];
        $.getJSON(url, {city: city, dayNum: dayNum}, function (data) {
            generateData(data);
            console.log(chartData);
            var chart = AmCharts.makeChart('history-chart', {
                type: 'serial',
                theme: 'light',
                dataProvider: chartData,
                categoryField: 'date',
                categoryAxis: {
                    parseDates: true,
                    minorGridEnabled: true,
                    axisColor: "#DADADA"
                },
                valueAxes: [{
                    id: 'v1',
                    axisAlpha: 0,
                    position: 'left',
                    guides: [{
                        value: 50,
                        lineAlpha: 0.2
                    }, {
                        value: 100
                    }, {
                        value: 150
                    }, {
                        value: 200
                    }, {
                        value: 300
                    }, {
                        value: 500
                    }]
                }, {
                    id: 'v2',
                    axisAlpha: 0,
                    position: 'right'
                }],
                graphs: [{
                    'id': 'g1',
                    'title': 'aqi',
                    'valueAxis': 'v1',
                    "bullet": "round",
                    "bulletSize": 8,
                    "lineThickness": 2,
                    "valueField": 'aqi',
                    balloonText: '[[title]]<br /><b style="font-size: 130%">[[value]]</b>'
                }, {
                    id: 'g2',
                    title: 'pm2.5/h',
                    valueAxis: 'v1',
                    valueField: 'pm25',
                    bullet: 'round',
                    bulletBorderAlpha: 1,
                    bulletColor: "#FFFFFF",
                    bulletSize: 5,
                    hideBulletsCount: 50,
                    lineThickness: 2,
                    dashLength: 5,
                    useLineColorForBulletBorder: true,
                    balloonText: '[[title]]<br/><b style="font-size: 130%">[[value]] μg/m3</b> '
                }, {
                    id: 'g3',
                    title: 'pm10/h',
                    valueAxis: 'v1',
                    valueField: 'pm10',
                    bullet: 'round',
                    bulletBorderAlpha: 1,
                    bulletColor: '#FFFFFF',
                    bulletSize: 5,
                    hideBulletsCount: 50,
                    lineThickness: 2,
                    dashLength: 5,
                    useLineColorForBulletBorder: true,
                    hidden: true,
                    balloonText: '[[title]]<br/><b style="font-size: 130%">[[value]] μg/m3</b> '
                }, {
                    id: 'g4',
                    title: 'co/h',
                    valueAxis: 'v2',
                    valueField: 'co',
                    bullet: 'round',
                    bulletBorderAlpha: 1,
                    bulletColor: '#FFFFFF',
                    bulletSize: 5,
                    hideBulletsCount: 50,
                    lineThickness: 2,
                    dashLength: 5,
                    useLineColorForBulletBorder: true,
                    hidden: true,
                    balloonText: '[[title]]<br/><b style="font-size: 130%">[[value]] mg/m3</b> '
                },{
                    id: 'g5',
                    title: 'no2/h',
                    valueAxis: 'v1',
                    valueField: 'no2',
                    bullet: 'round',
                    bulletBorderAlpha: 1,
                    bulletColor: '#FFFFFF',
                    bulletSize: 5,
                    hideBulletsCount: 50,
                    lineThickness: 2,
                    dashLength: 5,
                    useLineColorForBulletBorder: true,
                    hidden: true,
                    balloonText: '[[title]]<br/><b style="font-size: 130%">[[value]] μg/m3</b> '
                },{
                    id: 'g6',
                    title: 'so2/h',
                    valueAxis: 'v1',
                    valueField: 'so2',
                    bullet: 'round',
                    bulletBorderAlpha: 1,
                    bulletColor: '#FFFFFF',
                    bulletSize: 5,
                    hideBulletsCount: 50,
                    lineThickness: 2,
                    dashLength: 5,
                    useLineColorForBulletBorder: true,
                    hidden: true,
                    balloonText: '[[title]]<br/><b style="font-size: 130%">[[value]] μg/m3</b> '
                }],
                chartCursor: {
                    fullWidth: true,
                    valueLineEnabled: true,
                    valueLineBalloonEnabled: true,
                    cursorAlpha: 0,
                    valueLineAlpha: 0.2
                },
                chartScrollbar: {
                    scrollbarHeight: 40,
                    color: "#FFFFFF",
                    autoGridCount: true,
                    graph: "g1"
                },
                legend: {
                    useGraphSettings: true,
                    position: 'top'
                },
                balloon: {
                    borderThickness: 1,
                    shadowAlpha: 0
                }
            })
        });
        chart.addListener('dataUpdated', zoomChart);
    };

    function generateData(data) {
        console.log(data);
        data.forEach(function (aqiInfo) {
            var date = AmCharts.stringToDate(aqiInfo.date, 'YYYY_MM_DD');
            var aqi = aqiInfo.aqi;
            var pm25 = aqiInfo.pm25;
            var pm10 = aqiInfo.pm10;
            var co = aqiInfo.co;
            var no2 = aqiInfo.no2,
                so2 = aqiInfo.so2;
            chartData.push({
                date: date,
                aqi: aqi,
                pm25: pm25,
                pm10: pm10,
                co: co,
                no2: no2,
                so2: so2
            });
        });
    }

    function fomulateData(data, result) {
        data.forEach(function (aqiInfo) {
            var chart_data = {
                time: aqiInfo.date.split('_')[3] + '时',
                aqi: aqiInfo.aqi
            };

            result.push(chart_data);
        });
    }

    function zoomChart() {
        chart.zoomToIndexes(chartData.length - 40, chartData.length - 1);
    }

});
