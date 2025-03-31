package com.example.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GitRepositoryDetailsDbWithFavourite(
    @PrimaryKey @ColumnInfo(name = GitRepositoryDetailsDb.ID) val id: Int,
    @ColumnInfo(name = GitRepositoryDetailsDb.NAME) val name: String,
    @ColumnInfo(name = GitRepositoryDetailsDb.DESCRIPTION) val description: String,
    @ColumnInfo(name = GitRepositoryDetailsDb.PROGRAMMING_LANGUAGE) val programmingLanguage: String,
    @ColumnInfo(name = GitRepositoryDetailsDb.STARGAZERS_COUNT) val stargazersCount: Int,
    @ColumnInfo(name = GitRepositoryDetailsDb.FORKS_COUNT) val forksCount: Int,
    @ColumnInfo(name = GitRepositoryDetailsDb.OPEN_ISSUES) val openIssues: Int,
    @ColumnInfo(name = GitRepositoryDetailsDb.WATCHERS_COUNT) val watchersCount: Int,
    @Embedded(prefix = GitRepositoryDetailsDb.OWNER) val owner: OwnerDb,
    @ColumnInfo(name = GitRepositoryDetailsDb.HTML_URL) val htmlUrl: String,
    @ColumnInfo(name = GitRepositoryDetailsDb.CONTRIBUTORS_URL) val contributorsUrl: String,
    @ColumnInfo(name = GitRepositoryDetailsDb.DEFAULT_BRANCH) val defaultBranch: String,
    @ColumnInfo(name = GitRepositoryDetailsDb.CREATED_AT) val createdAt: String,
    @ColumnInfo(name = GitRepositoryDetailsDb.UPDATED_AT) val updatedAt: String,
    @ColumnInfo(name = GitRepositoryFavouriteIdDb.ID) val favouriteId: Int?,
)