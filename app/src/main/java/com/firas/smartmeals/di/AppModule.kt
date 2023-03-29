package com.firas.smartmeals.di

import android.content.Context
import androidx.room.Room
import com.firas.smartmeals.data.local.Dao
import com.firas.smartmeals.data.local.DataBase
import com.firas.smartmeals.data.remote.ApiService
import com.firas.smartmeals.data.repository.Repository
import com.firas.smartmeals.others.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    }

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext app: Context): DataBase {
        return Room.databaseBuilder(
            app,
            DataBase::class.java,
            "meals_db",
        ).build()

    }

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService,@ApplicationContext context: Context,db:DataBase): Repository {
        return Repository(apiService,context,db )

    }
    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }



}