package ru.nondoanything.weatherappwithfeatures.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.nondoanything.weatherappwithfeatures.data.response.CurrentWeatherResponse


const val API_KEY = "5a8141462176498c927195347202012"

//http://api.weatherapi.com/v1/current.json?key=5a8141462176498c927195347202012&q=Samara&lang=ru

interface WeatherAPIService {

    @GET("current.json")
    fun getCurrentWeather(
            @Query ("q") location: String,
            @Query ("lang") languageCode: String = "ru"
    ): Deferred<CurrentWeatherResponse>

    companion object{
        operator fun invoke(): WeatherAPIService {
            val requestInterceptor = Interceptor {chain ->  

                val url = chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("key", API_KEY)
                        .build()
                val request = chain.request()
                        .newBuilder()
                        .url(url)
                        .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(requestInterceptor)
                    .build()

            return Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("https://api.weatherapi.com/v1/")
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WeatherAPIService::class.java)
        }
    }

}