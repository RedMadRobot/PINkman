package com.redmadrobot.sample.internal

import android.app.Application
import com.redmadrobot.pinkman.Pinkman
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object HiltModule {

    @Singleton
    @Provides
    fun providePinkman(application: Application): Pinkman {
        return Pinkman(application.applicationContext)
    }
}
