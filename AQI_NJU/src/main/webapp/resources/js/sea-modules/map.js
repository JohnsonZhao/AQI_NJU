define(function (require, exports) {
    // require引入依赖
    require("materialize");
    require("mapbox");
    var geoModel = require('geoModel');

    //exports提供对外接口,地图初始化
    exports.init = function () {
        L.mapbox.accessToken = 'pk.eyJ1IjoibHVsZWluanUiLCJhIjoiY2lsYnppeWM4MXl5aHR5bHgwMGV3NDVhaCJ9.CcVHdQD-JTUiKPfp0tRryw';
        var map = L.mapbox.map('map', 'luleinju.pajg0dam', {
            // 是否允许滚轮控制
            scrollWheelZoom: false,
            zoomControl: false,
            minZoom: 4,
            maxZoom: 8,
            maxBounds: new L.LatLngBounds(new L.LatLng(15, 58), new L.LatLng(58, 150))
        }).setView([34, 108.9], 5).locate();

        L.control.zoom({
            position: 'bottomleft'
        }).addTo(map);

        // add custom popups to each using our custom feature properties
        map.featureLayer.on('layeradd', function (e) {
            var marker = e.layer,
                feature = marker.feature;

            // create custom popup content
            var popupContent =
                '<div class="leaflet_common leaflet_level' + feature.properties.level +
                '"><dl><dt><p class="leaflet_location">' + feature.properties.city +
                '<a href="' + feature.properties.url +
                '" class="leftinfo_border_level' + feature.properties.level + '" style="border-width:1px;">查看详细<span class="lfl_arrow_right leftbarl_arrow_level' +
                feature.properties.level + '"><s></s></span></a></p><p class="leaflet_date">数据更新时间：' +
                feature.properties.time + '</p></dt>';

            popupContent += '<dd><p><span class="leaflet_info leaflet_info_border_level' + feature.properties.level + '">' + feature.properties.aqi + '</span><span>AQI指数</span></p><p><span class="leaflet_info leaflet_info_border_level' + feature.properties.level + '">' + feature.properties.levelName + '</span><span>空气质量级别</span></p><p><span class="leaflet_info leaflet_info_border_level' + feature.properties.level + '">' + feature.properties.order + '</span><span>全国排名</span></p><div class="clear"></div></dd></dl></div>';
            marker.bindPopup(popupContent, {
                closeButton: false,
                minWidth: 320
            });

        });

        geoModel.getMapFeature(function (geoJson) {
            map.featureLayer.setGeoJSON(geoJson);

            $('#loading').remove();
        });
    };

});