package com.ahpp.notshoes.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import com.ahpp.notshoes.viewModel.LoginScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    viewModel { LoginScreenViewModel(androidContext()) }
}