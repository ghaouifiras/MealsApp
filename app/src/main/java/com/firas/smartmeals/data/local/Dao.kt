package com.firas.smartmeals.data.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.firas.smartmeals.data.model.Category

@androidx.room.Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(list: List<Category>)

    @Query("SELECT * FROM Category")
    suspend fun getCategories(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategorie(c: Category)

    @Query("SELECT * FROM Category WHERE strCategory LIKE '%' || :word || '%' ")
    suspend fun searchCategorie(word: String): List<Category>


}


