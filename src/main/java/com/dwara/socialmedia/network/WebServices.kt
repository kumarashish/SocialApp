package com.dwara.socialmedia.network
import com.dwara.socialmedia.room.Post
import retrofit2.Response
import retrofit2.http.*

interface WebServices {
    @GET
    suspend fun getPost(@Url url: String): Response<ArrayList<Post>>

    @POST
    suspend fun addCommentPost(@Url url: String): Response<ArrayList<Post>>
}
