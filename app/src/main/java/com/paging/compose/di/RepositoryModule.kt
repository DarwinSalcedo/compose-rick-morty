package com.paging.compose.di


import com.paging.compose.domain.network.ApiService
import com.paging.compose.domain.repositories.characters.CharactersRepository
import com.paging.compose.domain.repositories.characters.CharactersRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideTvShowsRepository(
        apiService: ApiService,
    ): CharactersRepository {
        return CharactersRepositoryImpl(apiService)
    }

}
