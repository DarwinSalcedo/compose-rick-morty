package com.paging.compose.domain.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.paging.compose.domain.database.CharacterDatabase
import com.paging.compose.domain.database.RemoteKeys
import com.paging.compose.domain.model.Character
import com.paging.compose.domain.repositories.characters.CharactersNetwork
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val service: CharactersNetwork,
    private val database: CharacterDatabase,
) : RemoteMediator<Int, Character>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (database.getRemoteKeysDao().getCreationTime()
                ?: 0) < cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>,
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            var endOfPaginationReached = false
            service.getList(page).collect { response ->
                when (response) {
                    is Response.Error -> MediatorResult.Error(response.exception)
                    is Response.Loading -> {}
                    is Response.Success -> {
                        endOfPaginationReached = response.data.isEmpty()

                        database.withTransaction {
                            if (loadType == LoadType.REFRESH) {
                                database.getRemoteKeysDao().clearRemoteKeys()
                                database.getCharactersDao().clearAllCharacters()
                            }
                            val prevKey = if (page > 1) page - 1 else null
                            val nextKey = if (endOfPaginationReached) null else page + 1
                            val remoteKeys = response.data.map {
                                RemoteKeys(characterID = it.id.toInt(),
                                    prevKey = prevKey,
                                    currentPage = page,
                                    nextKey = nextKey)
                            }
                            remoteKeys.let { database.getRemoteKeysDao().insertAll(it) }

                            database.getCharactersDao().insertAll(response.data)
                        }
                    }
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Character>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.getRemoteKeysDao().getRemoteKeyByCharacterID(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Character>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { data ->
            database.getRemoteKeysDao().getRemoteKeyByCharacterID(data.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Character>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { data ->
            database.getRemoteKeysDao().getRemoteKeyByCharacterID(data.id)
        }
    }
}