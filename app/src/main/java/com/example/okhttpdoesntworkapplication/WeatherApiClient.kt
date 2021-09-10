package com.example.okhttpdoesntworkapplication

import android.util.Log
import com.google.gson.reflect.TypeToken
import ru.spaceapp.infocity.android.sal.enums.RequestMethod
import ru.spaceapp.infocity.android.sal.enums.ResponseStatus

class WeatherApiClient(configuration: ApiClientConfiguration = ApiClientConfiguration.default)
    : BaseApiClient(configuration) {

    suspend fun getWeather(request: WeatherRequest): ResponseWrapper<WeatherModel> {
        try {
            val requestType = object : TypeToken<WeatherRequest>() {}.type
            val responseType = object : TypeToken<WeatherModel>() {}.type
            val response: ResponseWrapper<WeatherModel> = makeRequest("weather?q=London&appid=c49f1b4058370ccbda6ce765e22bcc75", RequestMethod.GET, request, requestType, responseType)
            return response
        }
        catch (e: Exception){
            Log.d("test", e.toString())
            return ResponseWrapper(ResponseStatus.UNKNOWN_ERROR, null)
        }
    }
}