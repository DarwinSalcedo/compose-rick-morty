package com.paging.compose.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.paging.compose.domain.model.Character
import com.paging.compose.presentation.home.ImageCard
import com.paging.compose.presentation.home.MainTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(data: Character, onUpClick: () -> Unit) {

    Scaffold(topBar = {
        SmallTopAppBar(title = { Text(text = data.name) },
            navigationIcon = {
                IconButton(onClick = onUpClick) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            })
    }) {
        Column {
            ImageCard(data.image)
            MainTitle(data.name)
            Text(data.species, style = MaterialTheme.typography.headlineSmall)
            Text(data.status, style = MaterialTheme.typography.bodyMedium)
        }
    }
}