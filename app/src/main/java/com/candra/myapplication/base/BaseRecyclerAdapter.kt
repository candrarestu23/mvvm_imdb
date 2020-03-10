package com.candra.myapplication.base

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<Model, VB: ViewDataBinding, VH : BaseRecyclerAdapter<Model, VB, VH>.BaseViewHolder> : RecyclerView.Adapter<VH> {
    protected var context: Context?
    protected var modelList: MutableList<Model>
    protected var inflater: LayoutInflater
    private var adapterId = 0

    protected val activity: Activity?
        get() = context as? Activity

    constructor(context: Context?, modelList: MutableList<Model>) {
        this.context = context
        this.modelList = modelList
        inflater = LayoutInflater.from(context)
    }

    constructor(context: Context?, modelList: MutableList<Model>, adapterId : Int) {
        this.context = context
        this.modelList = modelList
        this.adapterId = adapterId
        inflater = LayoutInflater.from(context)
    }

    constructor(context: Context?, modelList: MutableList<Model>, recyclerCallback: RecyclerInterface) {
        this.context = context
        this.modelList = modelList
        inflater = LayoutInflater.from(context)
        this.recyclerCallback = recyclerCallback
    }

    constructor(context: Context?, modelList: MutableList<Model>, recyclerCallback: RecyclerInterface, adapterId : Int) {
        this.context = context
        this.modelList = modelList
        this.adapterId = adapterId
        inflater = LayoutInflater.from(context)
        this.recyclerCallback = recyclerCallback
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(modelList[position])
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    protected fun initViewBinding(viewType: Int, parent: ViewGroup): VB {
        return DataBindingUtil.inflate(inflater, getResLayout(viewType), parent, false)
    }

    protected abstract fun getResLayout(type: Int): Int

    private var recyclerCallback: RecyclerInterface? = null

    fun getRecyclerCallback() : RecyclerInterface? {
        return recyclerCallback
    }

    fun getAdapterId() : Int {
        return adapterId
    }

    fun insertAndNotify(models: List<Model>?) {
        if (models != null && models.size > 0) {
            modelList.addAll(models)
            activity?.runOnUiThread{
                if (modelList.size - models.size == 0) {
                    notifyDataSetChanged()
                } else {
                    notifyItemRangeInserted(modelList.size - models.size, models.size)
                }
            }
        }
    }

    fun insertAndNotify(model: Model) {
        modelList.add(model)
        activity?.runOnUiThread{
            if (modelList.size == 1) {
                notifyDataSetChanged()
            } else {
                notifyItemRangeInserted(modelList.size - 1, 1)
            }
        }

    }

    fun clearAndNotify() {
        modelList.clear()
        activity?.runOnUiThread {
            notifyDataSetChanged()
        }
    }

    abstract inner class BaseViewHolder (val view: VB): RecyclerView.ViewHolder(view.root) {

        abstract fun onBind(model: Model)
    }
}