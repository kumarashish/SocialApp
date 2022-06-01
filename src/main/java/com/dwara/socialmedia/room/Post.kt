package com.dwara.socialmedia.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
data class Post(
    @PrimaryKey(autoGenerate = true)
    var postId: Int,
    @ColumnInfo(name = "userId") var userId: Int? = null,
    @ColumnInfo(name = "id") var id: Int? = null,
    @ColumnInfo(name = "title") var title: String? = null,
    @ColumnInfo(name = "body") var body: String? = null,
    @ColumnInfo(name = "isFav") var isFav: Boolean? = false
)
