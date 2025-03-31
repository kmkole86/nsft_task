package com.example.data.database.dao

//import com.example.data.database.model.GitRepositoryDbSearchIndex
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomWarnings
import androidx.room.Transaction
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
import kotlinx.coroutines.flow.Flow

@Dao
interface GitRepoDao {

    // repo list
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositoryIndex(repositories: List<GitRepositoryDbListIndex>)

    @Query("DELETE FROM ${GitRepositoryDbListIndex.ENTITY_NAME}")
    suspend fun clearRepositoryIndex()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repositories: List<GitRepositoryDb>)

    @Query("SELECT * from ${GitRepositoryDbListIndex.ENTITY_NAME} AS ListIdex INNER JOIN ${GitRepositoryDb.ENTITY_NAME} as RepositoryCache ON ListIdex.id = RepositoryCache.id LEFT JOIN ${GitRepositoryFavouriteIdDb.ENTITY_NAME} AS FavouriteIdex ON ListIdex.id = FavouriteIdex.fav_repo_id ORDER BY RepositoryCache.name ASC")
    fun observeRepositories(): Flow<List<GitRepositoryDbWithFavourite>>

    @Query("SELECT * FROM ${GitRepositoryDb.ENTITY_NAME} WHERE ${GitRepositoryDb.ID} =:repoId LIMIT 1")
    suspend fun getRepository(repoId: Int): GitRepositoryDb

    //repo details
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositoryDetails(repoDetails: GitRepositoryDetailsDb)

    @Query("SELECT * from (SELECT * FROM ${GitRepositoryDetailsDb.ENTITY_NAME} WHERE ${GitRepositoryDetailsDb.ID} =:repoId) as GitRepositoryDetails LEFT JOIN ${GitRepositoryFavouriteIdDb.ENTITY_NAME} AS FavouriteId ON GitRepositoryDetails.id = FavouriteId.fav_repo_id LIMIT 1")
    fun observeRepositoryDetails(repoId: Int): Flow<GitRepositoryDetailsDbWithFavourite?>

    //repo favourites
    @Query("SELECT * from ${GitRepositoryDb.ENTITY_NAME} as Repository INNER JOIN ${GitRepositoryFavouriteIdDb.ENTITY_NAME} AS FavouriteRepositoryId ON Repository.id = FavouriteRepositoryId.fav_repo_id")
    fun observeFavouriteRepositories(): Flow<List<GitRepositoryDbWithFavourite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteRepositoryId(repoId: GitRepositoryFavouriteIdDb)

    @Query("DELETE FROM ${GitRepositoryFavouriteIdDb.ENTITY_NAME} WHERE ${GitRepositoryFavouriteIdDb.ID}=:repoId")
    suspend fun deleteFavouriteRepositoryId(repoId: Int)

    @Query("SELECT EXISTS(SELECT * FROM ${GitRepositoryFavouriteIdDb.ENTITY_NAME} WHERE ${GitRepositoryFavouriteIdDb.ID}=:repoId)")
    suspend fun getRepositoryFavouriteStatus(repoId: Int): Boolean

    //repo contributors
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun _insertContributors(contributors: List<GitRepositoryContributorDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun _insertRepositoryContributorsCrossRef(crossRefs: List<GitRepositoryContributorsCrossRefDb>)

    @Transaction
    suspend fun insertContributors(repoId: Int, contributors: List<GitRepositoryContributorDb>) {
        _insertContributors(contributors)
        _insertRepositoryContributorsCrossRef(contributors.map {
            GitRepositoryContributorsCrossRefDb(
                repoId = repoId,
                contributorId = it.id
            )
        })
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteContributorId(contributorId: GitRepositoryContributorFavouriteIdDb)

    @Query("DELETE FROM ${GitRepositoryContributorFavouriteIdDb.ENTITY_NAME} WHERE ${GitRepositoryContributorFavouriteIdDb.ID}=:contributorId")
    suspend fun deleteFavouriteContributorId(contributorId: Int)

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * from ${GitRepositoryContributorDb.ENTITY_NAME} AS Contributor INNER JOIN (SELECT * FROM ${GitRepositoryContributorsCrossRefDb.ENTITY_NAME} WHERE ${GitRepositoryContributorsCrossRefDb.REPO_ID} =:repoId) AS RepoContributor ON Contributor.id == RepoContributor.contributor_id LEFT JOIN ${GitRepositoryContributorFavouriteIdDb.ENTITY_NAME} AS FavouriteIdex ON Contributor.id = FavouriteIdex.fav_contributor_id ORDER BY Contributor.name ASC")
    fun observeRepositoryContributors(repoId: Int): Flow<List<GitRepositoryContributorDbWithFavourite>>

    @Query("SELECT * from ${GitRepositoryContributorDb.ENTITY_NAME} as Contributor INNER JOIN ${GitRepositoryContributorFavouriteIdDb.ENTITY_NAME} AS FavouriteId ON Contributor.id = FavouriteId.fav_contributor_id")
    fun observeFavouriteContributors(): Flow<List<GitRepositoryContributorDbWithFavourite>>

    @Query("SELECT EXISTS(SELECT * FROM ${GitRepositoryContributorFavouriteIdDb.ENTITY_NAME} WHERE ${GitRepositoryContributorFavouriteIdDb.ID}=:contributorId)")
    suspend fun getRepositoryContributorFavouriteStatus(contributorId: Int): Boolean
}