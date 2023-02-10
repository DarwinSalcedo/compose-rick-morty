package com.paging.compose.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paging.compose.domain.dao.CharactersDao
import com.paging.compose.domain.dao.RemoteKeysDao
import com.paging.compose.domain.model.Character

@Database(
    entities = [Character::class, RemoteKeys::class],
    version = 1,
)
abstract class CharacterDatabase: RoomDatabase() {
    abstract fun getCharactersDao(): CharactersDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}