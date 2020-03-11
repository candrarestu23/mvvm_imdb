package com.candra.myapplication.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import com.candra.myapplication.R
import com.candra.myapplication.base.BaseRecyclerAdapter
import com.candra.myapplication.data.model.ListMovieModel
import com.candra.myapplication.databinding.ItemListMovieBinding
import com.candra.myapplication.ui.detail.activity.DetailMovieActivity
import org.jetbrains.anko.sdk27.coroutines.onClick

class MovieListAdapter(
    context: Context?,
    modelList: MutableList<ListMovieModel>
) : BaseRecyclerAdapter<ListMovieModel, ItemListMovieBinding, MovieListAdapter.ViewHolder>(context, modelList) {
    override fun getResLayout(type: Int): Int {
        return R.layout.item_list_movie
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(initViewBinding(viewType, parent))
    }

    inner class ViewHolder (view : ItemListMovieBinding)
        : BaseViewHolder(view) {

        override fun onBind(model: ListMovieModel) {
            view.data = model

            view.cvContent.onClick {
                context?.let{
                    val intent = Intent(context, DetailMovieActivity::class.java)
                    intent.putExtra("movieID", model.ID)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    it.startActivity(intent)
                }
            }
        }
    }
}