package com.example.blogdeinternet.system.configuration

class GlobalConfig {
    companion object {
        @JvmField //sea accesible desde Java sin necesidad de un método getter


        var ws_url = "http://192.168.1.78/wsBlog/"

        var WS_ACTION_INSERT = "insertarEntrada"

    }
}