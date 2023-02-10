package com.paging.compose.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.paging.compose.R
import com.paging.compose.domain.model.Character
import com.paging.compose.presentation.theme.ComposepagingrickmortyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onItemClick: (Character) -> Unit, viewModel: HomeViewModel = hiltViewModel()) {
    ComposepagingrickmortyTheme {
        Surface(modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background) {
            Scaffold(topBar = {
                SmallTopAppBar(title = { Text(text = stringResource(id = R.string.title_home)) })
            }) {
                CharacterItems(onItemClick = { data -> onItemClick(data) },
                    viewModel.uiState.listShows,
                    modifier = Modifier.padding(it))
            }
        }
    }
}