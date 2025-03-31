package com.example.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = GitRepositoryContributorFavouriteIdDb.ENTITY_NAME)
data class GitRepositoryContributorFavouriteIdDb(
    @PrimaryKey @ColumnInfo(name = ID) val id: Int,
) {

    companion object {
        const val ENTITY_NAME = "favourite_contributors"
        const val ID = "fav_contributor_id"
    }
}