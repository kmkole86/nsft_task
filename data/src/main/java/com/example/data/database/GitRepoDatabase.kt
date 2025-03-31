package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.dao.GitRepoDao
import com.example.data.database.model.GitRepositoryContributorDb
import com.example.data.database.model.GitRepositoryContributorDbWithFavourite
import com.example.data.database.model.GitRepositoryContributorFavouriteIdDb
import com.example.data.database.model.GitRepositoryContributorsCrossRefDb
import com.example.data.database.model.GitRepositoryDb
import com.example.data.database.model.GitRepositoryDbListIndex
import com.example.data.database.model.GitRepositoryDbWithFavourite
import com.example.data.database.model.GitRepositoryDetailsDb
import com.example.data.database.model.GitRepositoryDetailsDbWithFavourite
import com.example.data.database.model.GitRepositoryFavouriteIdDb

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        GitRepositoryDb::class,
        GitRepositoryDbWithFavourite::class,
        GitRepositoryDbListIndex::class,
        GitRepositoryFavouriteIdDb::class,

        GitRepositoryDetailsDb::class,
        GitRepositoryDetailsDbWithFavourite::class,

        GitRepositoryContributorDb::class,
        GitRepositoryContributorsCrossRefDb::class,
        GitRepositoryContributorFavouriteIdDb::class,
        GitRepositoryContributorDbWithFavourite::class
    ]
)

abstract class GitRepoDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME: String = "git_repo_database"
    }

    abstract fun gitRepositoriesDao(): GitRepoDao
}