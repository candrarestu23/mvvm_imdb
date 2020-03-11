package com.candra.myapplication.ui.detail.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.candra.myapplication.base.BaseViewModel
import com.candra.myapplication.data.model.DetailMovieModel
import com.candra.myapplication.data.model.GenresModel
import com.candra.myapplication.data.model.RequestState
import com.candra.myapplication.utils.AppUtils
import com.candra.myapplication.utils.SharedPreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by Candra Restu on 11,March,2020
 */

class DetailMovieViewModel(application: Application): BaseViewModel(application) {
    private var prefHelper = SharedPreferenceHelper(getApplication())
    private var refreshTime = AppUtils.getRefreshTime()

    val stateRequest = MutableLiveData<RequestState>().apply { value = RequestState.STATE_IDLE }
    val movieData = MutableLiveData<DetailMovieModel>()
    val genreList = MutableLiveData<String>()

    private val disposable = CompositeDisposable()

    fun resetState(){ stateRequest.value = RequestState.STATE_IDLE }

    fun fetchData(movieID: Int){
        fetchFromRemote(movieID)
    }

    private fun fetchFromRemote(movieID: Int) {
        stateRequest.postValue(RequestState.STATE_LOADING)
        disposable.add(
            dataManager.getMovieDetail(movieID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<DetailMovieModel>(){
                    override fun onError(e: Throwable) {
                        stateRequest.postValue(RequestState.STATE_FAILURE)
                        e.printStackTrace()
                    }

                    override fun onSuccess(t: DetailMovieModel) {
                        t?.let {
                            setData(it)
                        }
                    }

                })
        )
    }


    private fun setData(data: DetailMovieModel){
        movieData.value = data
        getGenreList(data.genres)
        stateRequest.postValue(RequestState.STATE_SUCCESS)
    }

    private fun getGenreList(data: List<GenresModel>){
        var tempData = ""
        for (i in data){
            i.name?.let { tempData += i.name + "," }
        }
        genreList.value = tempData
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}