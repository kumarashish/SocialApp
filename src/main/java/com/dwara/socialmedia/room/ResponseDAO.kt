package com.dwara.socialmedia.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ResponseDAO {
    @Query("SELECT * FROM POST")
    fun getAll(): List<Post>

    @Query("SELECT * FROM POST where isFav=:isFav")
    fun getAllFavorite(isFav:Int): List<Post>

    @Query("SELECT * FROM comments where postId=:postId")
    fun getAllComments(postId:Int): List<Comment>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(post: ArrayList<Post>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(comment: Comment)

    @Delete
    fun delete(post:Post)

    @Query("Update POST set isFav=:fav where postId=:postId")
    fun updatePost(fav: Boolean,postId: Int)
}


