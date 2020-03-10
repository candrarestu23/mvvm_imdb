package com.candra.myapplication.ui.home.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.candra.myapplication.base.BaseViewModel
import com.candra.myapplication.base.ResponseArray
import com.candra.myapplication.data.model.ListMovieModel
import com.candra.myapplication.data.model.RequestState

import com.candra.myapplication.utils.AppUtils
import com.candra.myapplication.utils.SharedPreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListMovieViewModel(application: Application): BaseViewModel(application) {
    private var prefHelper = SharedPreferenceHelper(getApplication())
    private var refreshTime = AppUtils.getRefreshTime()

    val stateRequest = MutableLiveData<RequestState>().apply { value = RequestState.STATE_IDLE }
    val listNewestWorkshop = MutableLiveData<List<ListMovieModel>>()

    private val disposable = CompositeDisposable()

    fun resetState(){ stateRequest.value = RequestState.STATE_IDLE }

    fun refresh(){
        fetchFromRemote(1)
//        val updateTime = prefHelper.getWorkshopTime()
//        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
//            fetchFromDatabase()
//        }else{
//            fetchFromRemote(1)
//        }
    }

    private fun fetchFromDatabase() {
        stateRequest.postValue(RequestState.STATE_LOADING)
        launch {
//            val listData = NewestWorkshopDatabase(getApplication()).dao().getNewestWorkshop()
//            setList(listData)
        }
    }

    fun getNextPage(page: Int){
        fetchFromRemote(page)
    }

    private fun fetchFromRemote(page: Int) {
        stateRequest.postValue(RequestState.STATE_LOADING)
        disposable.add(
            dataManager.getMovieList(false,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<ResponseArray<ListMovieModel>>(){
                    override fun onError(e: Throwable) {
                        stateRequest.postValue(RequestState.STATE_FAILURE)
                        e.printStackTrace()
                    }

                    override fun onSuccess(t: ResponseArray<ListMovieModel>) {
                        t.results?.let {
                            Log.d("responseSize", it.size.toString())
//                            storeNewestWorkshopLocally(it)
                            setList(it)
                        }
                    }
                })
        )
    }

//    private fun storeNewestWorkshopLocally(data: List<ListMovieModel>) {
//        launch {
//            val dao = NewestWorkshopDatabase(getApplication()).dao()
//            dao.deleteAll()
//            dao.insertAll(data)
//            setList(data)
//        }
//        prefHelper.saveWorkshopTime(System.nanoTime())
//    }

    private fun setList(list: List<ListMovieModel>){
        listNewestWorkshop.value = list
        stateRequest.postValue(RequestState.STATE_SUCCESS)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}