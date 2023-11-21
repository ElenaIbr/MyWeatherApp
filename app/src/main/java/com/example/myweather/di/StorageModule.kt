package com.example.myweather.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.myweather.domain.repositories.StorageRepository
import com.example.myweather.storage.StorageRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {
    @Singleton
    @Provides
    fun bindStorageRepository(
        @ApplicationContext context: Context
    ): StorageRepository {
        val dataStore = PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile("StorageRepositoryPreferences") }
        )
        return StorageRepositoryImpl(
            context,
            dataStore
        )
    }
}
