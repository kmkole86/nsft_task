package com.example.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.database.model.GitRepositoryDb.Companion.ID

@Entity
data class GitRepositoryDbWithFavourite(
    @PrimaryKey @ColumnInfo(name = ID) val id: Int,
    @ColumnInfo(name = GitRepositoryDb.NAME) val name: String,
    @ColumnInfo(name = GitRepositoryDb.DESCRIPTION) val description: String,
    @ColumnInfo(name = GitRepositoryDb.PROGRAMMING_LANGUAGE) val programmingLanguage: String,
    @ColumnInfo(name = GitRepositoryDb.STARGAZERS_COUNT) val stargazersCount: Int,
    @ColumnInfo(name = GitRepositoryDb.FORKS_COUNT) val forksCount: Int,
    @ColumnInfo(name = GitRepositoryDb.OPEN_ISSUES) val openIssues: Int,
    @ColumnInfo(name = GitRepositoryDb.WATCHERS_COUNT) val watchersCount: Int,
    @Embedded(prefix = GitRepositoryDb.OWNER) val owner: OwnerDb,
    @ColumnInfo(name = GitRepositoryDb.CONTRIBUTORS_URL) val contributorsUrl: String,
    @ColumnInfo(name = GitRepositoryFavouriteIdDb.ID) val favouriteId: Int?,
)