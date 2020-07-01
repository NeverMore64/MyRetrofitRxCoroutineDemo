package com.zy.myretrofitrxdemo

import com.zy.myretrofitrxdemo.model.Repo
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * create by zy on 2020/6/30
 * </p>
 */
interface Api {

    // retrofit 方式
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<Repo>>

    // kotlin 协程 方式
    @GET("users/{user}/repos")
    suspend fun listReposKt(@Path("user") user: String): List<Repo>

    // rxjava方式调用
    @GET("users/{user}/repos")
    fun listReposRx(@Path("user") user: String): Single<List<Repo>>
}