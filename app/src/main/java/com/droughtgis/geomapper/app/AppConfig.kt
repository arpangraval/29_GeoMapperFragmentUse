package com.droughtgis.geomapper.app

import com.droughtgis.geomapper.app.AppConfig

object AppConfig {
    // Server user login url  this is ip obtain from iPhone hotspot,
    // iPhone wifi IP: http://172.20.10.14, Lan IP: http://192.168.25.129, Web: http://www.droughtgis.com http://droughtgis.com
    var LOCALHOST = "http://172.16.14.216/droughtgis/"
    var WEBHOST = "https://tools.svgaikwad.com/droughtgis/"

    // Assign HOST variable to SERVER_URL
    var SERVER_URL = LOCALHOST

    // URL
    var URL_LOGIN = SERVER_URL + "app/login.php"
    var URL_QUICKMAPPER = SERVER_URL + "app/insertQuickMapper.php"
    var URL_GEOMAPPER = SERVER_URL + "app/insertGeoMapper.php"
    var URL_INSERTPROJECT = SERVER_URL + "app/insertProject.php"
    var URL_REGISTER = SERVER_URL + "app/register.php"
    val APP_URL = SERVER_URL + "app/"
}