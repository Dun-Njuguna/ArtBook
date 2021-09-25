package com.example.artbook.util

import com.example.artbook.BuildConfig
import com.example.artbook.util.Util.logResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RequestInterceptor @Inject constructor() : Interceptor {

    @Throws(NoConnectivityException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkLiveData.isNetworkAvailable()) {
            throw NoConnectivityException()
        } else {
            var request = chain.request()
            val requestUrl = request.url.newBuilder()
                .addQueryParameter("key", BuildConfig.API_KEY)
                .build()
            request = request.newBuilder().url(requestUrl).build()

            val response = chain.proceed(request)
            when (response.code) {
                200 -> {
                    logResponse("Server response .........200....${response.message}")
                }
                400 -> {
                    logResponse("Server response .....400........${response.message}")
                }

                401 -> {
                    logResponse("Server response .....403........${response.message}")
                }

                403 -> {
                    logResponse("Server response .....403........${response.message}")
                }

                404 -> {
                    logResponse("Server response ......404.......${response.message}")
                }

                502 -> {
                    logResponse("Server response ........502.....${response.message}")
                }
            }
            return response
        }
    }

    @Singleton
    @Provides
    fun getOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(RequestInterceptor())
        return builder.build()
    }

}