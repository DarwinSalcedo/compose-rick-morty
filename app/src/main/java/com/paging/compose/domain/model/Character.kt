package com.paging.compose.domain.model

data class Character(
    val id: Long = 0,
    val name: String,
    val image: String,
    val status: String,
    val species: String,
    val episode: List<String>
)

data class DataCharacters(
    val results: List<Character>? = emptyList()
)