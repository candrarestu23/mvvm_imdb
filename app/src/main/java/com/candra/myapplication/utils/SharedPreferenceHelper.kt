package com.candra.myapplication.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferenceHelper {
    companion object{
        private const val PREF_LIST = "Pref List"
        private var prefs: SharedPreferences? = null

        @Volatile private var instance: SharedPreferenceHelper? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): SharedPreferenceHelper = instance
            ?: synchronized(
                LOCK
            ){
                instance
                    ?: buildHelper(context).also {
                        instance = it
                    }
            }

        private fun buildHelper(context: Context): SharedPreferenceHelper {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferenceHelper()
        }
    }


    fun saveListTime(time: Long){
        prefs?.edit(commit = true){
            putLong(PREF_LIST, time)
        }
    }

    fun getMovieTime() = prefs?.getLong(PREF_LIST, 0)

    fun getChacheDuration() = prefs?.getString("pref_cache_duration", "")
}