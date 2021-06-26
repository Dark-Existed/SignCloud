package com.de.signcloud

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.instacart.library.truetime.TrueTime
import kotlinx.coroutines.Dispatchers
import kotlin.concurrent.thread

class SignCloudApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        thread(start = true) {
            TrueTime.build()
                .withNtpHost("time1.aliyun.com")
                .initialize()
        }
    }
}