package com.foods.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.foods.data.database.MealDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideContext(
        app: Application
    ): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideDatabase(
        applicationContext: Context
    ): MealDataBase {
        return Room.databaseBuilder(
            applicationContext,
            MealDataBase::class.java,
            "mealdb.db"
        ).build()
    }

}