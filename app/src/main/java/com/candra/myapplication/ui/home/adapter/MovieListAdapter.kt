package com.candra.myapplication.ui.home.adapter

import android.content.Context
import android.view.ViewGroup
import com.candra.myapplication.R
import com.candra.myapplication.base.BaseRecyclerAdapter
import com.candra.myapplication.data.model.ListMovieModel
import com.candra.myapplication.databinding.ItemListMovieBinding

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

//            view.llItemWorkshop.onClick {
//                context?.let{
//                    val intent = Intent(context, WorkshopDetailActivity::class.java)
//                    intent.putExtra("id", model.id)
//                    it.startActivity(intent)
//                }
//            }
        }
    }
}