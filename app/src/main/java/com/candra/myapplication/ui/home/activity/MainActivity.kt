package com.candra.myapplication.ui.home.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ListMovieViewModel::class.java)
        viewModel.refresh()

        setRecyclerView()
        setRefreshLayout()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.listNewestWorkshop.observe(this, Observer {
            it?.let {
                mList.clear()
                mList.addAll(it)

                rvAdapter.clearAndNotify()
                rvAdapter.insertAndNotify(it)
            }
        })

        viewModel.stateRequest.observe(this, Observer {
            when(it){
                RequestState.STATE_LOADING -> {
                    srLayout.isRefreshing = true
                    tvError.visibility = View.GONE
                    rvMain.visibility = View.GONE
                    pbList.visibility = View.VISIBLE
                }
                RequestState.STATE_FAILURE -> {
                    mList.clear()
                    rvAdapter.clearAndNotify()

                    srLayout.isRefreshing = false
                    tvError.visibility = View.VISIBLE
                    rvMain.visibility = View.GONE
                    pbList.visibility = View.GONE
                    viewModel.resetState()
                }
                RequestState.STATE_SUCCESS -> {
                    srLayout.isRefreshing = false
                    tvError.visibility = View.GONE
                    rvMain.visibility = View.VISIBLE
                    pbList.visibility = View.GONE
                    viewModel.resetState()
                }
                else -> return@Observer
            }
        })
    }

    private fun setRefreshLayout() {
        srLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun setRecyclerView() {
        mList = arrayListOf()

        rvAdapter = MovieListAdapter(baseContext, mList as MutableList<ListMovieModel>)
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = AppUtils.gridLayoutManager(baseContext!!, 2)
    }
}
