package com.example.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = GitRepositoryFavouriteIdDb.ENTITY_NAME)
data class GitRepositoryFavouriteIdDb(
    @PrimaryKey @ColumnInfo(name = ID) val id: Int,
) {

    companion object {
        const val ENTITY_NAME = "favourite_repos"
        const val ID = "fav_repo_id"
    }
}