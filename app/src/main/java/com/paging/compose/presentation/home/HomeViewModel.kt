package com.paging.compose.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.paging.compose.actions.GetListCharacters
import com.paging.compose.domain.model.Character
import com.paging.compose.domain.network.ApiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getListAction: GetListCharacters,
) :
    ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    fun getListCharacters() = getListAction.getCharacters()

    data class UiState(
        val status: ApiStatus = ApiStatus.DONE,
        val listShows: Flow<PagingData<Character>>? = null,
    )
}