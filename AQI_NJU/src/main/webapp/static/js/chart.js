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
                console.log(result);
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

    function fomulateData(data, result) {
        data.forEach(function (aqiInfo) {
            var chartdata = {
                time: aqiInfo.date.split('_')[3]+'时',
                aqi: aqiInfo.aqi
            };

            result.push(chartdata);
        });
    }

});
