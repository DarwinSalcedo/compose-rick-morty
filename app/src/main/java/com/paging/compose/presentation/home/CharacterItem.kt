package com.paging.compose.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.paging.compose.domain.model.Character


@Composable
fun CharacterItems(
    onItemClick: (Character) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: HomeViewModel = hiltViewModel()

    val list = viewModel.getListCharacters().collectAsLazyPagingItems()

    LazyColumn {
        itemsIndexed(list) { _, item ->
            item?.let {
                CharacterItem(data = it,
                    onClick = { onItemClick(it) },
                    modifier = modifier
                )
            }
        }
    }
}


@Composable
fun CharacterItem(data: Character, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.clickable(onClick = onClick)) {
        ImageCard(data.image)
        MainTitle(data.name)
    }
}

@Composable
fun ImageCard(url: String) {
    Box(modifier = Modifier
        .height(200.dp)
        .fillMaxWidth()) {

        AsyncImage(model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(1000)
            .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

    }
}

@Composable
fun MainTitle(name: String) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp)) {
        Text(name, style = MaterialTheme.typography.titleLarge)
    }
}