package com.candra.myapplication.ui.detail.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.candra.myapplication.R
import com.candra.myapplication.data.model.RequestState
import com.candra.myapplication.ui.detail.viewModel.DetailMovieViewModel
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * Created by Candra Restu on 11,March,2020
 */
class DetailMovieActivity: AppCompatActivity() {
    private lateinit var viewModel: DetailMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel = ViewModelProviders.of(this).get(DetailMovieViewModel::class.java)
        val movieID = intent.getIntExtra("movieID",0)
        viewModel.fetchData(movieID)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.movieData.observe(this, Observer {
            it?.let {

            }
        })

        viewModel.stateRequest.observe(this, Observer {
            when(it){
                RequestState.STATE_LOADING -> {
                    tvError.visibility = View.GONE
                    llContainer.visibility = View.GONE
                    pbDetail.visibility = View.VISIBLE
                }
                RequestState.STATE_FAILURE -> {
                    tvError.visibility = View.VISIBLE
                    llContainer.visibility = View.GONE
                    pbDetail.visibility = View.GONE
                    viewModel.resetState()
                }
                RequestState.STATE_SUCCESS -> {
                    tvError.visibility = View.GONE
                    llContainer.visibility = View.VISIBLE
                    pbDetail.visibility = View.GONE
                    viewModel.resetState()
                }
                else -> return@Observer
            }
        })
    }
}