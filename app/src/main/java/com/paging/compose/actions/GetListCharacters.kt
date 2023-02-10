package com.paging.compose.actions

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.paging.compose.domain.database.CharacterDatabase
import com.paging.compose.domain.model.Character
import com.paging.compose.domain.network.CharacterRemoteMediator
import com.paging.compose.domain.repositories.characters.CharactersNetwork
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListCharacters @Inject constructor(
    private val service: CharactersNetwork,
    private val database: CharacterDatabase,
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getCharacters(): Flow<PagingData<Character>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 10,
                initialLoadSize = 10,
            ),
            pagingSourceFactory = {
                database.getCharactersDao().getCharacters()
            },
            remoteMediator = CharacterRemoteMediator(
                service,
                database,
            )
        ).flow
}
