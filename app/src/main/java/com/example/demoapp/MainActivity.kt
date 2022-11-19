package com.example.demoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.demoapp.data.api.MovieApiService
import com.example.demoapp.databinding.ActivityMainBinding
import com.example.demoapp.ui.adapter.MoviesAdapter
import com.example.demoapp.viewmodel.MoviesViewModel
import com.example.networkmodule.utils.RetrofitService

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var moviesViewModel: MoviesViewModel? = null
    private lateinit var movieAdapter : MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        initViewModel()
        getData()
        setupObservers()
    }

    private fun setupView() {
        movieAdapter = MoviesAdapter()
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(applicationContext,1)
            adapter = movieAdapter
        }
    }

    private fun initViewModel() {
        if (null == moviesViewModel) {
            moviesViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        }
    }

    private fun getData() {
        moviesViewModel?.getPopularMovies()
    }

    private fun setupObservers() {
        moviesViewModel?.observeMovieLiveData()?.observe(this, Observer { movieList ->
            movieAdapter.setMovieList(movieList)
        })
    }
}