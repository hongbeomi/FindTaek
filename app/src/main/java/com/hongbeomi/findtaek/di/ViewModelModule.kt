package com.hongbeomi.findtaek.di

import com.hongbeomi.findtaek.view.ui.add.AddViewModel
import com.hongbeomi.findtaek.view.ui.main.MainViewModel
import com.hongbeomi.findtaek.view.ui.timeline.TimeLineViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

/**
 * @author hongbeomi
 */

val viewModelModule = module {
    viewModel { AddViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { TimeLineViewModel(get()) }
}