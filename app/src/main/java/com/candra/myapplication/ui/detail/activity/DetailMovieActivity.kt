package com.candra.myapplication.ui.detail.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.candra.myapplication.R
import com.candra.myapplication.data.model.DetailMovieModel
import com.candra.myapplication.data.model.RequestState
import com.candra.myapplication.databinding.ActivityDetailBinding
import com.candra.myapplication.ui.detail.viewModel.DetailMovieViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_detail.*


/**
 * Created by Candra Restu on 11,March,2020
 */
class DetailMovieActivity: AppCompatActivity() {
    private lateinit var viewModel: DetailMovieViewModel
    private lateinit var dataBinding: ActivityDetailBinding
    private var remoteData: DetailMovieModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        viewModel = ViewModelProviders.of(this).get(DetailMovieViewModel::class.java)
        val movieID = intent.getIntExtra("movieID",0)
        viewModel.fetchData(movieID)

        observeViewModel()
        lifecycle.addObserver(ytPlayer)
    }

    @SuppressLint("SetTextI18n")
    private fun observeViewModel() {
        viewModel.movieData.observe(this, Observer {
            it?.let {
                dataBinding.data = it
                remoteData = it
                ytPlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                        val videoId = it.videos?.results?.get(0)?.key
                        videoId?.let { id -> youTubePlayer.cueVideo(id, 0f) }
                    }
                })
            }
        })

        viewModel.genreList.observe(this, Observer {
            it?.let {
                tvMovieInfo.text = "${remoteData?.runtime} min | $it"
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}