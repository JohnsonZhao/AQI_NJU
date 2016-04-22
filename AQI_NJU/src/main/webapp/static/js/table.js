/**
 * Created by lulei on 16/4/13.
 */
define(function (require, exports) {
    var dynatable = require('dynatable');
    exports.current_rank = function (url) {
        $.getJSON(url, function (data) {
            data.sort(function (a, b) {
                return a['aqi'] - b['aqi'];
            });
            data.forEach(function (city_info, i) {
                city_info.order = i + 1;
                city_info.cityName = '<a href="getCurrentAirQualityByChinese?city='+city_info.cityName+'">'+city_info.cityName+'</a>';
            });
            $('#current-table').dynatable({
                features: {
                    recordCount: false,
                    perPageSelect: false,
                    search: false
                },
                dataset: {
                    records: data,
                    sorts: {'order': 1},
                    perPageDefault: 20
                }
            });
        });
    };

    //exports.history = function (url, city, dayNum) {
    //    $.getJSON(url, {
    //        city: city, dayNum: dayNum
    //    }, function (data) {
    //        $('#history-data').dynatable({
    //            dataset: {
    //                records: data
    //            }
    //        })
    //    });
    //}


});
