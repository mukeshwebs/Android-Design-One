package dev.droid.template

import android.app.Application
import android.content.Context
import android.text.TextUtils

open class ApplicationUtil : Application() {
    private lateinit var mContext : Context
    private lateinit var applicationUtil : ApplicationUtil

    override fun onCreate() {
        super.onCreate()
        mContext = this
        applicationUtil = this
    }

    companion object{
        fun String.isEmailValid() : Boolean {
            return ! TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
                    .matches()
        }
    }
}