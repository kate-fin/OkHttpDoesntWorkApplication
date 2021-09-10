package com.example.okhttpdoesntworkapplication


import com.google.gson.*
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import ru.spaceapp.infocity.android.sal.enums.RequestMethod
import ru.spaceapp.infocity.android.sal.enums.ResponseStatus
//import ru.spaceapp.infocity.android.sal.requests.AuthorizedRequest
import java.io.*
import java.lang.reflect.*
import java.util.*
import java.util.concurrent.*
import kotlin.coroutines.*


open class BaseApiClient(val configuration: ApiClientConfiguration = ApiClientConfiguration.default) {
    companion object {
        private val commonHttpClient = OkHttpClient()

        private fun createSerializer() = GsonBuilder()
//            .registerTypeAdapter(Date::class.java, Iso8601DateAdapter())
//            .registerTypeAdapter(Date::class.createType(nullable = true).javaClass, UnixDateAdapter())
            .create()

        public fun <T> serialize(value: T, type: Type): String = createSerializer().toJson(value, type)

        public fun <T> deserialize(s: String, type: Type): T = createSerializer().fromJson(s, type)
    }

    protected suspend fun <TRequest, TResponse> makeRequest(relativeUrl: String, requestMethod: RequestMethod, request: TRequest, requestType: Type, responseType: Type): ResponseWrapper<TResponse> = withContext(Dispatchers.Default) {
        val networkRequest = prepareNetworkRequest(relativeUrl, requestMethod, request, requestType)
        return@withContext suspendCoroutine<ResponseWrapper<TResponse>> { continuation ->
            createHttpClient().newCall(networkRequest).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val result = processNetworkResponse<TResponse>(response, responseType)
                        continuation.resume(result)
                    } catch (ex: Exception) {
                        continuation.resumeWithException(ex)
                    }
                }
            })
        }
    }

    protected suspend fun <TResponse> makeRequest(relativeUrl: String, prepareRequest: (Request.Builder) -> Unit, responseType: Type): ResponseWrapper<TResponse> = withContext(Dispatchers.Default) {
        val networkRequest = prepareNetworkRequest(relativeUrl, prepareRequest)
        return@withContext suspendCoroutine<ResponseWrapper<TResponse>> { continuation ->
            createHttpClient().newCall(networkRequest).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val result = processNetworkResponse<TResponse>(response, responseType)
                        continuation.resume(result)
                    } catch (ex: Exception) {
                        continuation.resumeWithException(ex)
                    }
                }
            })
        }
    }

    private fun <TRequest> prepareNetworkRequest(relativeUrl: String, requestMethod: RequestMethod, request: TRequest, requestType: Type): Request {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        var builder = Request.Builder()
            .url(configuration.getRequestAddress(relativeUrl))
        if (requestMethod != RequestMethod.GET) {
            val requestBody = serialize(request, requestType).toRequestBody(mediaType)
            when (requestMethod) {
                RequestMethod.POST -> builder = builder.post(requestBody)
                RequestMethod.PUT -> builder = builder.put(requestBody)
                RequestMethod.DELETE -> builder = builder.delete(requestBody)
                else -> {
                }
            }
        } else {
            builder = builder.get()
        }

        val authorizedRequest = request as? AuthorizedRequest
        if (authorizedRequest != null) {
            builder = builder.addHeader("Authorization", "Bearer ${authorizedRequest.token}")
        }
        return builder.build()
    }

    private fun prepareNetworkRequest(relativeUrl: String, prepareRequest: (Request.Builder) -> Unit): Request {
        var builder = Request.Builder()
            .url(configuration.getRequestAddress(relativeUrl))
        prepareRequest(builder)
        return builder.build()
    }

    private fun <TResponse> processNetworkResponse(response: Response, responseType: Type): ResponseWrapper<TResponse> {
        val responseBody = response.body?.string() ?: ""
        when (response.code) {
            401 -> return ResponseWrapper(ResponseStatus.UNAUTHORIZED, null)
            500 -> return ResponseWrapper(ResponseStatus.UNKNOWN_ERROR, null)
            200 -> return ResponseWrapper(ResponseStatus.SUCCESS, deserialize(responseBody, responseType))
            201 -> return ResponseWrapper(ResponseStatus.SUCCESS, deserialize(responseBody, responseType))
            else -> {
                throw Exception("Request to address ${response.request.url} completed with status code ${response.code}, response \"$responseBody\"")
            }
        }
    }
    private fun createHttpClient() = commonHttpClient.newBuilder()
        .connectTimeout(configuration.timeout, TimeUnit.SECONDS)
        .writeTimeout(configuration.timeout, TimeUnit.SECONDS)
        .readTimeout(configuration.timeout, TimeUnit.SECONDS)
        .cache(null)
        .addInterceptor(getHttpLogger())// delete on production
        .build()
}


// delete on production
private fun getHttpLogger(): Interceptor {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    return logging
}