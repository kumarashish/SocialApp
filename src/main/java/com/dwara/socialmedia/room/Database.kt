package com.dwara.socialmedia.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        Post::class, Comment::class,
    ], version = 1
)
abstract class Database() : RoomDatabase() {
    abstract fun resposeDao(): ResponseDAO
}





