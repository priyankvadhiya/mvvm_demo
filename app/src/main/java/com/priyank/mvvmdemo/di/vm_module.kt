package com.priyank.mvvmdemo.di

import com.priyank.mvvmdemo.presentation.home.HomeViewModel
import com.priyank.mvvmdemo.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel() }
    viewModel { HomeViewModel(get()) }
}