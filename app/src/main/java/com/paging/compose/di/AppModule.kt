package com.paging.compose.di

import android.content.Context
import androidx.room.Room
import com.paging.compose.core.App
import com.paging.compose.domain.dao.CharactersDao
import com.paging.compose.domain.dao.RemoteKeysDao
import com.paging.compose.domain.database.CharacterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): App {
        return app as App
    }

    @Singleton
    @Provides
    fun provideCharacterDatabase(@ApplicationContext context: Context): CharacterDatabase =
        Room.databaseBuilder(context, CharacterDatabase::class.java, "rick_morty_database").build()

    @Singleton
    @Provides
    fun provideCharactersDao(database: CharacterDatabase): CharactersDao =
        database.getCharactersDao()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(database: CharacterDatabase): RemoteKeysDao =
        database.getRemoteKeysDao()

}