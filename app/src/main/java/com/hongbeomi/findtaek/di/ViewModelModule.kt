package com.hongbeomi.findtaek.di

import com.hongbeomi.findtaek.view.ui.add.AddFragmentViewModel
import com.hongbeomi.findtaek.view.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author hongbeomi
 */

val viewModelModule = module {
    viewModel { AddFragmentViewModel(repository = get()) }
    viewModel { MainViewModel(repository = get()) }
}