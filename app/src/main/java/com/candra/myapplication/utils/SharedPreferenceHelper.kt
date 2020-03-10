package com.candra.myapplication.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferenceHelper {
    companion object{
        private const val PREF_PROFILE = "Pref time"
        private const val PREF_GALLERY = "Pref Gallery"
        private const val PREF_PORTOFOLIO = "Pref Portofolio"
        private const val PREF_WORKSHOP = "Pref Workshop"
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

    fun saveUpdateTime(time: Long) {
        prefs?.edit(commit = true){
            putLong(PREF_PROFILE, time)
        }
    }

    fun saveGalleryTime(time: Long) {
        prefs?.edit(commit = true){
            putLong(PREF_GALLERY, time)
        }
    }

    fun savePortofolioTime(time: Long) {
        prefs?.edit(commit = true){
            putLong(PREF_PORTOFOLIO, time)
        }
    }

    fun saveWorkshopTime(time: Long){
        prefs?.edit(commit = true){
            putLong(PREF_WORKSHOP, time)
        }
    }

    fun getUpdateTime() = prefs?.getLong(PREF_PROFILE, 0)

    fun getGalleryTime() = prefs?.getLong(PREF_GALLERY, 0)

    fun getPortofolioTime() = prefs?.getLong(PREF_PORTOFOLIO, 0)

    fun getWorkshopTime() = prefs?.getLong(PREF_WORKSHOP, 0)

    fun getChacheDuration() = prefs?.getString("pref_cache_duration", "")
}