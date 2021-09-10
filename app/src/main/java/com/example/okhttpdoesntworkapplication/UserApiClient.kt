package com.example.okhttpdoesntworkapplication

import android.util.Log
import com.google.gson.reflect.TypeToken
import ru.spaceapp.infocity.android.sal.enums.RequestMethod
import ru.spaceapp.infocity.android.sal.enums.ResponseStatus

class UserApiClient(configuration: ApiClientConfiguration = ApiClientConfiguration.default)
    : BaseApiClient(configuration) {

    suspend fun getUser(request: GetUserRequest): ResponseWrapper<List<PersonModel>> {
        try {
            val requestType = object : TypeToken<GetUserRequest>() {}.type
            val responseType = object : TypeToken<List<PersonModel>>() {}.type
            val response: ResponseWrapper<List<PersonModel>> = makeRequest("person", RequestMethod.GET, request, requestType, responseType)
            return response
        }
        catch (e: Exception){
            Log.d("test", e.toString())
            return ResponseWrapper(ResponseStatus.UNKNOWN_ERROR, null)
        }
    }
}