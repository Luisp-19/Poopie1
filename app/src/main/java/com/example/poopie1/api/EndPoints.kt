package com.example.poopie1.api

import com.example.poopie1.LoginActivity
import retrofit2.Call
import  retrofit2.http.*

interface EndPoints{

    /*@GET("/users/")
    fun getUsers(): Call<List<User>>

    @GET("/users/{id}")
    fun getUserById(@Path("id")id:Int): Call<User>*/

    @FormUrlEncoded
    @POST("/myslim/api/login/post")
    fun postTest(@Field("username") username: String, @Field("pass") pass: String?): Call<LoginOutput>
}