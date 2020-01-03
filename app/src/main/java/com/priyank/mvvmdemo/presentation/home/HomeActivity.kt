package com.priyank.mvvmdemo.presentation.home

import android.os.Bundle
import androidx.lifecycle.Observer
import com.priyank.mvvmdemo.R
import com.priyank.mvvmdemo.presentation.base.BaseActivity
import com.priyank.mvvmdemo.presentation.home.adapter.HomeUserLIstAdapter
import com.priyank.mvvmdemo.presentation.home.model.UserListMainModel
import com.priyank.mvvmdemo.utility.isNetworkAvailable
import com.priyank.mvvmdemo.utility.toast
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity() {

    private val homeViewModel: HomeViewModel by viewModel()

    override fun getBaseViewModel() = homeViewModel

    lateinit var userAdapter: HomeUserLIstAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()
    }

    private fun init() {
        attachObserver()
        getUserList()
    }

    private fun attachObserver() {
        homeViewModel.userListLiveData.observe(this, Observer {
            it.apply {
                setUpAdapter(this.data)
            }
        })
    }

    private fun setUpAdapter(userListMainModel: List<UserListMainModel.Data>) {
        userAdapter = HomeUserLIstAdapter(userListMainModel, this)
        recyclerList.apply {
            hasFixedSize()
            adapter = userAdapter
        }
    }

    private fun getUserList() {
        if (isNetworkAvailable()) {
            homeViewModel.getUserList()
        } else {
            toast("No internet connection")
        }
    }
}
