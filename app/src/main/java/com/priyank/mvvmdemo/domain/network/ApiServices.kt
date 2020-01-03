package com.priyank.mvvmdemo.domain.network

import com.priyank.mvvmdemo.presentation.home.model.UserListMainModel
import com.priyank.mvvmdemo.utility.ApiConstant
import retrofit2.http.GET

interface ApiServices {

    @GET(ApiConstant.ApiGetUserList)
    suspend fun getUserList(): UserListMainModel

}