package com.candra.myapplication.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat


object AppUtils {

    private val MY_PERMISSIONS_REQUEST = 500

    fun defaultLinearLayoutManager(context: Context): LinearLayoutManager {
        return LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    fun horizontalLinearLayoutManager(context: Context): LinearLayoutManager {
        return LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    fun gridLayoutManager(context: Context, rows: Int): GridLayoutManager {
        return GridLayoutManager(context, rows)
    }

    fun isEmailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex()) && email.length > 0
    }

    fun isPhoneValid(phoneNumber: String): Boolean{
        if(isNullOrEmpty(phoneNumber)){
            return false
        }else return phoneNumber.length in 8..15
    }

    fun getRate(aDouble: Double): String {
        return DecimalFormat("##.#").format(aDouble)
    }

    fun getWebViewClient(): WebViewClient {
        return object: WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val intent = Intent(Intent.ACTION_VIEW, request!!.url)
                view!!.context.startActivity(intent)
                return true
            }
        }
    }

    fun isNullOrEmpty(string: String?): Boolean {
        return if (string == null) {
            true
        } else string.isEmpty()
    }

    fun checkReadWritePermission(activity: Activity): Boolean {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST
            )
            return false
        } else {
            return true
        }
    }

    fun checkLocationPermission(activity: Activity): Boolean {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                MY_PERMISSIONS_REQUEST
            )
            return false
        } else {
            return true
        }
    }

    fun checkCameraPermission(activity: Activity): Boolean {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                arrayOf(Manifest.permission.CAMERA),
                MY_PERMISSIONS_REQUEST
            )
            return false
        } else {
            return true
        }
    }

    fun getMoneyFormat(money: Int?): String {
        val formatter = DecimalFormat("#,###")
        return if (money == 0) {
            ""
        } else "Rp " + formatter.format(money)
    }

    fun millisToMinute(millis: Int): Int{
        return (millis / 1000) / 60
    }

    fun millisToSecond(millis: Int): Int{
        return (millis / 1000) % 60
    }

    fun getRefreshTime(): Long{
        return 10 * 60 * 1000 * 1000 * 1000L //10 minute in Nano second
    }
}