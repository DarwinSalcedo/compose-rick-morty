package com.paging.compose.actions

import com.paging.compose.domain.repositories.characters.CharactersRepository
import javax.inject.Inject

class GetListCharacters @Inject constructor(private val repository: CharactersRepository) {

    suspend operator fun invoke() = repository.getList()

}
