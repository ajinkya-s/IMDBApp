package com.example.demoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demoapp.data.model.Movies
import com.example.demoapp.data.model.Result
import com.example.demoapp.data.repository.MoviesRepository
import com.example.networkmodule.utils.Resource
import com.example.networkmodule.utils.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewModel : ViewModel() {

    private var movieLiveData = MutableLiveData<List<Result>>()
    var job: Job? = null
    val loading = MutableLiveData<Boolean>()
    private val moviesRepository: MoviesRepository = MoviesRepository()


    fun getPopularMovies() {
        job = CoroutineScope(Dispatchers.IO).launch {
            moviesRepository.apiService.getPopularMovies(moviesRepository.apiKey).enqueue(object : Callback<Movies> {
                override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                    if (response.body() != null) {
                        movieLiveData.postValue(response.body()!!.results)
                        loading.value = false
                    } else {
                        loading.value = false
                    }
                }

                override fun onFailure(call: Call<Movies>, t: Throwable) {
                    loading.value = false
                }
            })
        }
    }

    fun observeMovieLiveData() : LiveData<List<Result>> {
        return movieLiveData
    }
}