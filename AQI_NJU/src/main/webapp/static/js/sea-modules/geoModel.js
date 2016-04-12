/**
 * Created by lulei on 16/3/5.
 */
define(function (require, exports) {
    exports.getMapFeature = function (mapDataFn) {
        $.getJSON('AirQuality/getAllCurrentAirQuality', function (data) {
            var fullResult = [];
            formatResultData(data, fullResult);
            setOrder(fullResult);
            mapDataFn(fullResult);
            //mapDataFn(data);
        });
    }

    //map level to color
    var levelColorMap = {
        // green
        '一级（优）': '#00E400',
        // yellow
        '二级（良）': '#FFFF00',
        // orange
        '三级（轻度污染）': '#FF7E00',
        // red
        '四级（中度污染）': '#FF0000',
        // purple
        '五级（重度污染）': '#99004C',
        // maroon
        '六级（严重污染）': '#7E0023'
    };

    //map level to int
    var levelIntMap = {
        '一级（优）': 1,
        '二级（良）': 2,
        '三级（轻度污染）': 3,
        '四级（中度污染）': 4,
        '五级（重度污染）': 5,
        '六级（严重污染）': 6
    };

    // 对ajax请求得到的data数据进行处理,得到marker所需的完整信息
    function formatResultData(data, fullResult) {
        data.forEach(function (cityInfo) {
            var cityFullInfo = {type: 'Feature'};
            if (cityInfo.hasOwnProperty('coordinates')) {
                cityFullInfo.geometry = {
                    type: 'Point',
                    coordinates: JSON.parse(cityInfo.coordinates)
                };
            }


            cityFullInfo.properties = {
                'marker-size': 'small',
                aqi: cityInfo.aqi,
                level: levelIntMap[cityInfo.indexType],
                levelName: cityInfo.indexType.substr(3, cityInfo.indexType.length - 4),
                'marker-color': levelColorMap[cityInfo.indexType],
                url: 'AirQuality/getCurrentAirQuality?city=' + cityInfo.id.split("_")[0],
                city: cityInfo.cityName,
                time: cityInfo.date
            };

            fullResult.push(cityFullInfo);
        })
    }


    function setOrder(data) {
        data.sort(function (a, b) {
            return a.properties.aqi - b.properties.aqi;
        });

        data.forEach(function (cityInfo, i) {
            cityInfo.properties.order = i + 1;
        })
    }

});