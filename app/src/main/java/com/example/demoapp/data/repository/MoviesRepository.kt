package com.example.demoapp.data.repository

import com.example.demoapp.data.api.MovieApiService
import com.example.demoapp.data.model.Movies
import com.example.networkmodule.utils.Resource
import com.example.networkmodule.utils.RetrofitService
import com.example.networkmodule.utils.Status
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepository () {
    var apiKey: String = "38a73d59546aa378980a88b645f487fc"

    val apiService: MovieApiService = RetrofitService.createService(MovieApiService::class.java)

    suspend fun getPhotosFromApi(sol: Int, page: Int): Resource<Movies>? {
        var result: Resource<Movies>? = null;
        apiService.getPopularMovies(apiKey).enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                if (response.body() != null) {
                    result = Resource<Movies>(
                        Status.SUCCESS,response.body(),null
                    )
                } else {
                    result = Resource<Movies>(
                        Status.SUCCESS,null,null
                    )
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                result = Resource<Movies>(
                    Status.ERROR,null,"SOMETHING WENT WRONG"
                )
            }
        })

        return result;
    }

}