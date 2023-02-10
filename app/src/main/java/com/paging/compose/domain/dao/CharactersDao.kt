package com.paging.compose.domain.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.paging.compose.domain.model.Character

@Dao
interface CharactersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Character>)

    @Query("Select * From characters Order By page")
    fun getCharacters(): PagingSource<Int, Character>

    @Query("Delete From characters")
    suspend fun clearAllCharacters()
}