package com.example.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = GitRepositoryDetailsDb.ENTITY_NAME)
data class GitRepositoryDetailsDb(
    @PrimaryKey @ColumnInfo(name = ID) val id: Int,
    @ColumnInfo(name = NAME) val name: String,
    @ColumnInfo(name = DESCRIPTION) val description: String,
    @ColumnInfo(name = PROGRAMMING_LANGUAGE) val programmingLanguage: String,
    @ColumnInfo(name = STARGAZERS_COUNT) val stargazersCount: Int,
    @ColumnInfo(name = FORKS_COUNT) val forksCount: Int,
    @ColumnInfo(name = OPEN_ISSUES) val openIssues: Int,
    @ColumnInfo(name = WATCHERS_COUNT) val watchersCount: Int,
    @Embedded(prefix = OWNER) val owner: OwnerDb,
    @ColumnInfo(name = DEFAULT_BRANCH) val defaultBranch: String,
    @ColumnInfo(name = HTML_URL) val htmlUrl: String,
    @ColumnInfo(name = CONTRIBUTORS_URL) val contributorsUrl: String,
    @ColumnInfo(name = CREATED_AT) val createdAt: String,
    @ColumnInfo(name = UPDATED_AT) val updatedAt: String
) {

    companion object {
        const val ENTITY_NAME = "git_repository_details_db"
        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val PROGRAMMING_LANGUAGE = "programming_language"
        const val STARGAZERS_COUNT = "stargazers_count"
        const val FORKS_COUNT = "forks_count"
        const val OPEN_ISSUES = "open_issues"
        const val WATCHERS_COUNT = "watchers_count"
        const val OWNER = "owner"
        const val HTML_URL = "html_url"
        const val CONTRIBUTORS_URL = "contritubors_url"
        const val DEFAULT_BRANCH = "nadefault_branchme"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"
    }
}