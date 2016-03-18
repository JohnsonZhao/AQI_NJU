/**
 * Created by lulei on 16/3/6.
 * a example of geoJson
 * geoJson is a format of encoding geographic data structures
 * GeoJSON supports the following geometry types: Point, LineString, Polygon, MultiPoint, MultiLineString, and MultiPolygon.
 * Geometric objects with additional properties are Feature objects. Sets of features are contained by FeatureCollection objects.
 */

var geoJson = {
    type: 'feature',
    geometry: {
        type: 'point',
        coordinates: [125.6, 10.1]
    },
    properties: {
        name: 'name'
    }
}

var featureCollection = {
    type: 'FeatureCollection',
    features: [
        {
            type: 'feature',
            geometry: {
                type: 'point',
                coordinates: [125.3, 10]
            },
            properties: {
                prop0: 'value0'
            }
        },
        {
            type: 'feature',
            geometry: {
                type: 'multiPoint',
                coordinates: [[199,10.0],[100,11.0]]
            }
        }
    ]
}
