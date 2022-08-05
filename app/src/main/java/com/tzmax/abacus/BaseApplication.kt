package com.tzmax.abacus

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class BaseApplication: Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context
    }

    override fun attachBaseContext(base: Context?) {
        mContext = this
        super.attachBaseContext(base)
    }
}