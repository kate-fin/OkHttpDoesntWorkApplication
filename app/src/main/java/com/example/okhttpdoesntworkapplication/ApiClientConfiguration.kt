package com.example.okhttpdoesntworkapplication

class ApiClientConfiguration(val url: String, val timeout: Long = 60) {
    companion object {
        val debug = ApiClientConfiguration("https://dev2.im-dispatcher.ru/api/v1/", 600)

        val release = ApiClientConfiguration("https://qa0.mybonus.ru/")

        val default = debug
    }

    fun getRequestAddress(relativeAddress: String) =
        url.trimEnd('/') + "/" + relativeAddress.trimStart('/')
}