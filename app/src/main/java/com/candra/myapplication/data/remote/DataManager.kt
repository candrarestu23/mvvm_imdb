package com.candra.myapplication.data.remote

import com.candra.myapplication.base.ResponseArray
import com.candra.myapplication.data.model.DetailMovieModel
import com.candra.myapplication.data.model.ListMovieModel
import com.candra.myapplication.utils.Constant
import io.reactivex.Single

class DataManager (private val apiService: ApiServices) {

    fun getMovieList(
        includeVideo : Boolean,
        page: Int
    ) : Single<ResponseArray<ListMovieModel>> {
        return apiService.getListMovie(Constant.API_KEY_V3, Constant.LANG_US, Constant.SORT_DESC, false, includeVideo, page)
    }

    fun getMovieDetail(movieID: Int):Single<DetailMovieModel>{
        return apiService.getDetailMovie(movieID, Constant.API_KEY_V3, Constant.LANG_US, "videos")
    }
}
