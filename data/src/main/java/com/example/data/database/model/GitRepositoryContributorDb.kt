package com.example.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = GitRepositoryContributorDb.ENTITY_NAME)
data class GitRepositoryContributorDb(

    @PrimaryKey @ColumnInfo(name = ID) val id: Int,
    @ColumnInfo(name = NAME) val name: String,
    @ColumnInfo(name = AVATAR_URL) val avatarUrl: String,
) {
    companion object {
        const val ENTITY_NAME = "git_repository_contributors_db"
        const val ID = "id"
        const val NAME = "name"
        const val AVATAR_URL = "avatarUrl"
    }
}