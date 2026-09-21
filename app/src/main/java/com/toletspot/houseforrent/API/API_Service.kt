package com.toletspot.houseforrent.API

import com.toletspot.houseforrent.API.StartUp_API.API_Interface
import com.toletspot.houseforrent.MainActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class API_Service {
    companion object {

        private fun providesHttpLogging(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // Always log
            }
        }

        fun getUnsafeOkHttpClient(): OkHttpClient {
            return try {
                val trustAllCerts = arrayOf<TrustManager>(
                    object : X509TrustManager {
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>, authType: String
                        ) {}
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>, authType: String
                        ) {}
                        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                    }
                )

                val sslContext = SSLContext.getInstance("SSL").apply {
                    init(null, trustAllCerts, SecureRandom())
                }
                val sslSocketFactory = sslContext.socketFactory

                OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                    .hostnameVerifier { _, _ -> true }
                    .addInterceptor(providesHttpLogging())
                    .connectTimeout(90, TimeUnit.SECONDS)
                    .writeTimeout(90, TimeUnit.SECONDS)
                    .readTimeout(90, TimeUnit.SECONDS)
                    .build()

            } catch (e: Exception) {
                OkHttpClient.Builder().build()
            }
        }

        fun create(): API_Interface {
            val retrofit = Retrofit.Builder()
                .baseUrl(MainActivity.getLiveUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient())
                .build()
            return retrofit.create(API_Interface::class.java)
        }
    }
}