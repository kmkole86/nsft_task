package com.example.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.database.model.GitRepositoryDb.Companion.ID

@Entity
data class GitRepositoryContributorDbWithFavourite(

    @PrimaryKey @ColumnInfo(name = ID) val id: Int,
    @ColumnInfo(name = GitRepositoryContributorDb.NAME) val name: String,
    @ColumnInfo(name = GitRepositoryContributorDb.AVATAR_URL) val avatarUrl: String,
    @ColumnInfo(name = GitRepositoryContributorFavouriteIdDb.ID) val favouriteId: Int?,
)