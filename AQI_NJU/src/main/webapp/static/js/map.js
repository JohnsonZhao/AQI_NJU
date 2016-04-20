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

        //L.control.zoom({
        //    position: 'bottomleft'
        //}).addTo(map);

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

            popupContent += '<dd><p><span class="leaflet_info leaflet_info_border_level' + feature.properties.level + '">' + feature.properties.aqi + '</span><span>AQI指数</span></p><p><span class="leaflet_info leaflet_info_border_level' + feature.properties.level + ' leaflet_levelInfo">' + feature.properties.levelName + '</span><span>空气质量级别</span></p><p><span class="leaflet_info leaflet_info_border_level' + feature.properties.level + '">' + feature.properties.order + '</span><span>全国排名</span></p><div class="clear"></div></dd></dl></div>';
            marker.bindPopup(popupContent, {
                closeButton: false,
                minWidth: 320
            });

        });

        map.addControl(L.mapbox.geocoderControl('mapbox.places', {
            autocomplete: true,
            position: 'bottomleft'
        }).on('select', function (info) {
            map.featureLayer.eachLayer(function (marker) {
                if (marker.feature.properties.text === info.feature.text.toLowerCase()) {
                    marker.openPopup();
                }
            })
        }));


        L.Control.zoomHome = L.Control.extend({
            options: {
                position: 'bottomleft',
                zoomInText: '+',
                zoomInTitle: 'Zoom in',
                zoomOutText: '-',
                zoomOutTitle: 'Zoom out',
                zoomHomeText: '<i class="material-icons">restore</i>',
                zoomHomeTitle: 'Zoom home',
                locateText: '<i class="material-icons">my_location</i>',
                locateTitle: 'locate'
            },

            onAdd: function (map) {
                var controlName = 'gin-control-zoom',
                    container = L.DomUtil.create('div', controlName + ' leaflet-bar'),
                    options = this.options;

                this._zoomInButton = this._createButton(options.zoomInText, options.zoomInTitle,
                    controlName + '-in zoom', container, this._zoomIn);
                this._zoomHomeButton = this._createButton(options.zoomHomeText, options.zoomHomeTitle,
                    controlName + '-home zoom', container, this._zoomHome);
                this._zoomOutButton = this._createButton(options.zoomOutText, options.zoomOutTitle,
                    controlName + '-out zoom', container, this._zoomOut);
                this._locateButton = this._createButton(options.locateText, options.locateTitle, controlName + '-locate zoom', container, this._locate);
                this._updateDisabled();
                map.on('zoomend zoomlevelschange', this._updateDisabled, this);

                return container;
            },

            onRemove: function (map) {
                map.off('zoomend zoomlevelschange', this._updateDisabled, this);
            },

            _zoomIn: function (e) {
                this._map.zoomIn(e.shiftKey ? 3 : 1);
            },

            _zoomOut: function (e) {
                this._map.zoomOut(e.shiftKey ? 3 : 1);
            },

            _zoomHome: function (e) {
                map.setView([34, 108.9], 5);
            },

            _locate: function (e) {
                e.preventDefault();
                e.stopPropagation();
                map.locate({setView: true});
                map.on('locationfound', function (e) {
                    var marker = L.marker([e.latitude, e.longitude]).bindPopup('You are here :)');
                    var circle = L.circle([e.latitude, e.longitude], e.accuracy / 2, {
                        weight: 1,
                        color: 'blue',
                        fillColor: '#cacaca',
                        fillOpacity: 0.2
                    });
                    map.addLayer(marker);
                    map.addLayer(circle);
                })
            },

            _createButton: function (html, title, className, container, fn) {
                var link = L.DomUtil.create('a', className, container);
                link.innerHTML = html;
                link.href = '#';
                link.title = title;

                L.DomEvent.on(link, 'mousedown dblclick', L.DomEvent.stopPropagation)
                    .on(link, 'click', L.DomEvent.stop)
                    .on(link, 'click', fn, this)
                    .on(link, 'click', this._refocusOnMap, this);

                return link;
            },

            _updateDisabled: function () {
                var map = this._map,
                    className = 'leaflet-disabled';

                L.DomUtil.removeClass(this._zoomInButton, className);
                L.DomUtil.removeClass(this._zoomOutButton, className);

                if (map._zoom === map.getMinZoom()) {
                    L.DomUtil.addClass(this._zoomOutButton, className);
                }
                if (map._zoom === map.getMaxZoom()) {
                    L.DomUtil.addClass(this._zoomInButton, className);
                }
            }
        });
        var zoomHome = new L.Control.zoomHome();
        zoomHome.addTo(map);


        geoModel.getMapFeature(function (geoJson) {
            map.featureLayer.setGeoJSON(geoJson);

            $('#loading').remove();
        });
    };

});