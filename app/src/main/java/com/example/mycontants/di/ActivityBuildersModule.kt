package com.example.mycontants.di


import com.example.mycontants.view.MainActivity
import com.example.mycontants.view.MainViewModelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [MainViewModelModule::class])
    abstract fun contributeMainActivity(): MainActivity

}