package com.example.qwerty.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PointViewModel(application: Application):AndroidViewModel(application) {
    val readAllData: LiveData<List<Point>>
    private val repository:PointRepository
    init {
        val pointDao=PointDatabase.getDatabase(application).pointDao()
        repository = PointRepository(pointDao)
        readAllData = repository.readAllData
    }
    fun addPoint(point:Point){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPoint(point)
        }

    }
}