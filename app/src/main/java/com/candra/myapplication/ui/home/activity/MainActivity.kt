package com.candra.myapplication.ui.home.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.candra.myapplication.R
import com.candra.myapplication.data.model.ListMovieModel
import com.candra.myapplication.data.model.RequestState
import com.candra.myapplication.ui.home.adapter.MovieListAdapter
import com.candra.myapplication.ui.home.viewModel.ListMovieViewModel
import com.candra.myapplication.utils.AppUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ListMovieViewModel
    private lateinit var rvAdapter: MovieListAdapter
    private lateinit var mList: ArrayList<ListMovieModel>
    private var pages = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ListMovieViewModel::class.java)
        viewModel.refresh()

        setRecyclerView()
        setRefreshLayout()
        observeViewModel()
        setPagination()
    }

    private fun observeViewModel() {
        viewModel.listMovie.observe(this, Observer {
            it?.let {
                rvAdapter.insertAndNotify(it)
                rvAdapter.notifyDataSetChanged()
            }
        })

        viewModel.stateRequest.observe(this, Observer {
            when(it){
                RequestState.STATE_LOADING -> {
                    if(pages == 2){
                        srLayout.isRefreshing = true
                        tvError.visibility = View.GONE
                        rvMain.visibility = View.GONE
                        pbList.visibility = View.VISIBLE
                    }
                }
                RequestState.STATE_FAILURE -> {
                    if (pages == 2){
                        rvAdapter.clearAndNotify()

                        srLayout.isRefreshing = false
                        tvError.visibility = View.VISIBLE
                        rvMain.visibility = View.GONE
                        pbList.visibility = View.GONE
                    }
                    viewModel.resetState()
                }

                RequestState.STATE_SUCCESS -> {
                    if(pages == 2){
                        srLayout.isRefreshing = false
                        tvError.visibility = View.GONE
                        rvMain.visibility = View.VISIBLE
                        pbList.visibility = View.GONE
                    }
                    viewModel.resetState()
                }
                else -> return@Observer
            }
        })
    }

    private fun setRefreshLayout() {
        srLayout.setOnRefreshListener {
            rvAdapter.clearAndNotify()
            pages = 2
            viewModel.refresh()
        }
    }

    private fun setRecyclerView() {
        mList = arrayListOf()

        rvAdapter = MovieListAdapter(baseContext, mList as MutableList<ListMovieModel>)
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = AppUtils.gridLayoutManager(baseContext!!, 2)
    }

    private fun setPagination(){
        rvMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.getNextPage(pages)
                    pages++
                }
            }
        })
    }
}
