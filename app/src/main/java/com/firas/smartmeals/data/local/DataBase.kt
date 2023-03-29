package com.firas.smartmeals.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.firas.smartmeals.data.model.Category

@Database(
    entities = [Category::class],
    version = 1
)
abstract class DataBase : RoomDatabase() {

    abstract val dao: Dao
}