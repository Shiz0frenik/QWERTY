package com.example.qwerty.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.qwerty.data.Point

@Dao
interface pointDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addpoint(point:Point)
    @Query("SELECT*FROM points ORDER BY id ASC")
    fun readAllData():LiveData<List<Point>>
}