package com.dwara.socialmedia.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "postId") var postId: Int,
    @ColumnInfo(name = "comment") var comment: String,
    @ColumnInfo(name = "commentby") var commentby: String,
    @ColumnInfo(name = "commentOn") var commentOn: String,
)

