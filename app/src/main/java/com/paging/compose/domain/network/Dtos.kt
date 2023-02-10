package com.paging.compose.domain.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Characters(
    @Json(name = "info") val info: InfoDto? = null,
    @Json(name = "results") val results: List<CharacterDto>? = emptyList(),
) {
    data class InfoDto(
        @Json(name = "count") val count: Long = 0,
        @Json(name = "pages") val pages: Long = 0,
        @Json(name = "next") val next: String? = null,
        @Json(name = "prev") val prev: String? = null,
    )

    data class CharacterDto(
        @Json(name = "id") val id: Long = 0,
        @Json(name = "name") val name: String,
        @Json(name = "status") val status: String,
        @Json(name = "species") val species: String = "",
        @Json(name = "image") val image: String,
        @Json(name = "episode") val episode: List<String>,
    )
}





