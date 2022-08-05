package com.tzmax.abacus.utils

import android.content.res.Resources
import androidx.annotation.StringRes
import com.tzmax.abacus.BaseApplication

fun string(@StringRes id: Int) = res.getString(id)

val res: Resources get() = BaseApplication.mContext.resources