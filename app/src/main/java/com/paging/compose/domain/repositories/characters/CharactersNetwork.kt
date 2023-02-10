package com.paging.compose.domain.repositories.characters

import com.paging.compose.domain.network.Response
import kotlinx.coroutines.flow.Flow
import com.paging.compose.domain.model.Character

interface CharactersNetwork {

    suspend fun getList(page : Int): Flow<Response<List<Character>>>

}