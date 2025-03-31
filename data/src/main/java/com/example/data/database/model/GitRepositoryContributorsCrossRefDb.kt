package com.example.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = GitRepositoryContributorsCrossRefDb.ENTITY_NAME,
    primaryKeys = [GitRepositoryContributorsCrossRefDb.REPO_ID, GitRepositoryContributorsCrossRefDb.CONTRIBUTOR_ID]
)
data class GitRepositoryContributorsCrossRefDb(

    @ColumnInfo(name = GitRepositoryContributorsCrossRefDb.REPO_ID) val repoId: Int,
    @ColumnInfo(name = GitRepositoryContributorsCrossRefDb.CONTRIBUTOR_ID) val contributorId: Int,
) {
    companion object {
        const val ENTITY_NAME = "git_repository_contributors_cross_ref_db"
        const val REPO_ID = "repo_id"
        const val CONTRIBUTOR_ID = "contributor_id"
    }
}