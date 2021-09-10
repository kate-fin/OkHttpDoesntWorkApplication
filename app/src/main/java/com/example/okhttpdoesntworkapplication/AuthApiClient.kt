package com.example.okhttpdoesntworkapplication

import com.google.gson.reflect.TypeToken
import ru.spaceapp.infocity.android.sal.enums.RequestMethod

class AuthApiClient(configuration: ApiClientConfiguration = ApiClientConfiguration.default)
    : BaseApiClient(configuration) {

    suspend fun getAuthToken(request: GetAuthTokenRequest): ResponseWrapper<GetAuthTokenResponse> {
        val requestType = object : TypeToken<GetAuthTokenRequest>() { }.type
        val responseType = object : TypeToken<GetAuthTokenResponse>() { }.type
        return makeRequest("auth/token", RequestMethod.POST, request, requestType, responseType)
    }
}