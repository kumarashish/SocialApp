package com.dwara.socialmedia

import android.app.Application

class SocialApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        socialApplication=this
    }
    companion object {
        lateinit var socialApplication: SocialApplication
    }
}