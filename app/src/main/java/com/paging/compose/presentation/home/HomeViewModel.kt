package com.paging.compose.presentation.home

import com.paging.compose.domain.model.Character
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paging.compose.actions.GetListCharacters
import com.paging.compose.domain.network.ApiStatus
import com.paging.compose.domain.network.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getListAction: GetListCharacters,
) :
    ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    init {
        getListCharacters()
    }

    fun getListCharacters() {
        viewModelScope.launch {
            uiState = UiState(ApiStatus.LOADING)
            getListAction().collect {
                when (it) {
                    is Response.Success -> uiState = UiState(ApiStatus.DONE, it.data)

                    is Response.Error -> uiState = UiState(ApiStatus.ERROR)

                    is Response.Loading -> uiState = UiState(ApiStatus.LOADING)
                }
            }
        }
    }

    data class UiState(
        val status: ApiStatus = ApiStatus.DONE,
        val listShows: List<Character> = emptyList(),
    )
}