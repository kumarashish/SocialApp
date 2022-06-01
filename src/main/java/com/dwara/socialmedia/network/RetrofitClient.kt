package com.dwara.socialmedia.network



import androidx.viewbinding.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

object RetrofitClient {

    private val webServices: WebServices
    private val okHttpClient: OkHttpClient

    init {

        val okHttpBuilder = OkHttpClient.Builder()



        okHttpClient = okHttpBuilder
            .readTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .connectTimeout(90, TimeUnit.SECONDS)

            .build()

        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(okHttpClient)
            .build()

        webServices = retrofit.create(WebServices::class.java)


    }

    fun getService(): WebServices = webServices
}