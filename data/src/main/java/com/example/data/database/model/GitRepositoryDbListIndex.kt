package com.example.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//current repo list index table
@Entity(tableName = GitRepositoryDbListIndex.ENTITY_NAME)
data class GitRepositoryDbListIndex(
    @PrimaryKey @ColumnInfo(name = ID) val id: Int,
) {

    companion object {
        const val ENTITY_NAME = "repo_list_index_table"
        const val ID = "id"
    }
}