package com.priyank.mvvmdemo.presentation.splash

import android.os.Bundle
import com.priyank.mvvmdemo.R
import com.priyank.mvvmdemo.presentation.base.BaseActivity
import com.priyank.mvvmdemo.presentation.home.HomeActivity
import com.priyank.mvvmdemo.utility.launchActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashActivity : BaseActivity() {

    private val splashViewModel: SplashViewModel by viewModel()

    override fun getBaseViewModel() = splashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        launch {
            delay(2000)
            launchActivity<HomeActivity> { }
        }
    }
}
