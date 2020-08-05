package com.paulsoia.todo135.data.database.dao

import androidx.room.*
import com.paulsoia.todo135.data.database.entity.TagEntity

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTag(tag: TagEntity): Long

    @Update
    suspend fun updateTag(tag: TagEntity)

    @Query("DELETE FROM tag_table WHERE id LIKE :tagId")
    suspend fun removeTagById(tagId: Long)

    @Query("SELECT * FROM tag_table")
    suspend fun getAllTags(): List<TagEntity>

}