package com.example.qwerty.data

import androidx.lifecycle.LiveData

class PointRepository(private val pointDao: pointDao) {
    val readAllData : LiveData<List<Point>> = pointDao.readAllData()

    suspend fun addPoint(point: Point){
        pointDao.addpoint(point)
    }
}
