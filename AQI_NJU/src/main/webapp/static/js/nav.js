/**
 * Created by lulei on 16/3/8.
 * js for common nav
 */

define(function (require, exports) {
    exports.toggleSearch = function () {

        // press icon toggle search input
        $('#toggle-search').click(function () {
            var search = $('#search');

            //search.toggle('slide', {direction: 'left'} );
            search.toggle();

        });


    }

});