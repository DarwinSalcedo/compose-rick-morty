package com.paging.compose.domain.repositories.characters

import com.paging.compose.domain.model.Character
import com.paging.compose.domain.network.ApiService
import com.paging.compose.domain.network.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharactersNetworkImpl @Inject constructor(
    private val apiService: ApiService,
) : CharactersNetwork {


    override suspend fun getList(page: Int): Flow<Response<List<Character>>> = flow {
        emit(Response.Loading)
        val newResult =
            apiService.getListCharacters(page)

        emit(Response.Success(newResult.results!!.map {
            Character(it.id.toInt(),
                it.name,
                it.image,
                it.status,
                it.species,
                page)
        }))

    }.catch { exception ->
        emit(Response.Error(exception))
    }
}