package com.paging.compose.domain.repositories.characters

import com.paging.compose.domain.model.Character
import com.paging.compose.domain.network.ApiService
import com.paging.compose.domain.network.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : CharactersRepository {

    private var currentPage = 1
    private var cache = mutableListOf<Character>()

    override suspend fun getList(): Flow<Response<List<Character>>> = flow {
        emit(Response.Loading)
        val newResult =
            apiService.getListCharacters(currentPage.toString())
        currentPage++
        cache.addAll(newResult.results!!.map {
            Character(it.id, it.name, it.image, it.status, it.species ?: "", it.episode)
        })
        emit(Response.Success(cache))

    }.catch { exception ->
        emit(Response.Error(exception))
    }
}