package com.example.ktsreddit.data.network

import com.example.ktsreddit.data.network.model.Reddit.RedditDataResponse
import com.example.ktsreddit.data.network.model.Reddit.RedditJsonWrapper
import retrofit2.Response
import retrofit2.http.*

interface RedditApi {

    @GET("r/{subreddit}/{category}.json")
    suspend fun getSubreddit(
        @Path("subreddit") subreddit: String,
        @Path("category") category: String,
        @Query("limit") limit: Int,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): Response<RedditJsonWrapper<RedditDataResponse>>

    @FormUrlEncoded
    @POST("api/save")
    suspend fun savedPost(
        @Field("category") category: String?,
        @Field("id") id: String
    ) : Response<Unit>

    @FormUrlEncoded
    @POST("api/unsave")
    suspend fun unSavedPost(
        @Field("id") id: String
    ) : Response<Unit>

    @FormUrlEncoded
    @POST("api/vote")
    suspend fun votePost (
        @Field("dir") dir: String,
        @Field("id") id: String,
    ): Response<Unit>


    @GET("api/v1/me.json")
    suspend fun getMe (
    ): Response<Unit>

}