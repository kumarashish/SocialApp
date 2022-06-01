package com.dwara.socialmedia.room

import androidx.room.Room
import com.dwara.socialmedia.SocialApplication

object SocialDB {
    private val dbInstance = Room.databaseBuilder(
        SocialApplication.socialApplication,
        Database::class.java, "Social-DB"
    ).allowMainThreadQueries().fallbackToDestructiveMigration()
        .build()

    private val responseDAO:ResponseDAO =dbInstance.resposeDao()
    fun responseDao() = responseDAO


    fun clearAllTable() {
        dbInstance.clearAllTables()
    }
}