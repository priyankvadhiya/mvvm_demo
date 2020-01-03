package com.priyank.mvvmdemo.presentation.home

import androidx.lifecycle.MutableLiveData
import com.priyank.mvvmdemo.domain.network.ApiServices
import com.priyank.mvvmdemo.presentation.base.BaseViewModel
import com.priyank.mvvmdemo.presentation.home.model.UserListMainModel
import com.priyank.mvvmdemo.utility.Logger
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(private val apiServices: ApiServices) : BaseViewModel() {

    val userListLiveData: MutableLiveData<UserListMainModel> = MutableLiveData()

    fun getUserList() {
        launch {
            kotlin.runCatching {
                apiServices.getUserList()
            }.fold({
                userListLiveData.postValue(it)
            }, {
                exceptionLiveData.postValue(it)
            })
        }
    }

}