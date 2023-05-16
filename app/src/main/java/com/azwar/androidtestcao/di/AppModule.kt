package com.azwar.androidtestcao.di

import com.azwar.androidtestcao.viewmodels.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { UserViewModel() }
}