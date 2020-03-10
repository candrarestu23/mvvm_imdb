package com.candra.myapplication.utils

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.candra.myapplication.R

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
//        .error(ContextCompat.getDrawable(context, R.drawable.error_no_picture))
        .into(this)
}

//Crete Name Space for XML
@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String?){
    view.loadImage(url, getProgressDrawable(view.context))
}


