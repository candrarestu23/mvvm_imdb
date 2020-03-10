package com.candra.myapplication.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.candra.myapplication.data.remote.DataManager
import com.candra.myapplication.utils.AppShared
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application): AndroidViewModel(application),
    CoroutineScope {
    private val job = Job()

    protected val dataManager: DataManager = AppShared.dataManager()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}