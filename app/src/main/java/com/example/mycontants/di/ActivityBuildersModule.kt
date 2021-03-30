package com.example.mycontants.di


import androidx.compose.foundation.ExperimentalFoundationApi
import com.example.mycontants.view.MainActivity
import com.example.mycontants.view.MainViewModelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ExperimentalFoundationApi
    @ContributesAndroidInjector(modules = [MainViewModelModule::class])
    abstract fun contributeMainActivity(): MainActivity

}