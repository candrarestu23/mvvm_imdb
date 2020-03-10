package com.candra.myapplication.utils

import android.util.Log
import com.candra.myapplication.BuildConfig


object Logger{
    private val ENABLE_STACK_TRACE = true
    private val ENABLE_LONG_LOG = false

    private val TAG = "DANGER"

    fun log(e: Throwable?) {

        if (e == null)
            return

        log(Log.ERROR, e.toString(), null)
        printStack(e)
    }

    fun log(e: Throwable?, printStack: Boolean) {

        if (e == null)
            return

        log(Log.ERROR, e.toString(), null)

        if (printStack)
            printStack(e)
    }

    private fun printStack(e: Throwable) {
        if (ENABLE_STACK_TRACE)
            e.printStackTrace()
    }

    @JvmOverloads
    fun log(mode: Int, message: String?, tag: String? = null) {
        var messages= message
        var tags = tag

        if (!BuildConfig.DEBUG)
            return

        if (messages == null)
            return

        if (tags == null)
            tags = ""

        if (messages.trim { it <= ' ' }.length == 0)
            messages = "Data Empty"

        if (ENABLE_LONG_LOG) {
            longLog(mode, tags, messages)
        } else {
            if (mode == Log.DEBUG) {
                Log.d(TAG + tags, messages)
            } else if (mode == Log.ERROR) {
                Log.e(TAG + tags, messages)
            } else if (mode == Log.INFO) {
                Log.i(TAG + tags, messages)
            } else if (mode == Log.WARN) {
                Log.w(TAG + tags, messages)
            }
        }
    }

    private fun longLog(mode: Int, tag: String, message: String) {
        val maxLogSize = 1000
        for (i in 0..message.length / maxLogSize) {
            val start = i * maxLogSize
            var end = (i + 1) * maxLogSize
            end = if (end > message.length) message.length else end

            if (mode == Log.DEBUG) {
                Log.d(TAG + tag, message.substring(start, end))
            } else if (mode == Log.ERROR) {
                Log.e(TAG + tag, message.substring(start, end))
            } else if (mode == Log.INFO) {
                Log.i(TAG + tag, message.substring(start, end))
            } else if (mode == Log.WARN) {
                Log.w(TAG + tag, message.substring(start, end))
            }
        }
    }
}