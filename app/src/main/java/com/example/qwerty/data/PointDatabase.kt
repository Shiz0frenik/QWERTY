package com.example.qwerty.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Point::class],version = 1,exportSchema = false)
abstract class PointDatabase: RoomDatabase() {
    abstract fun pointDao(): pointDao
    companion object{
        @Volatile
        private  var INSTANCE: PointDatabase?= null
        fun getDatabase(context: Context): PointDatabase{
            val temptInstance = INSTANCE
            if (temptInstance!=null){
                return temptInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PointDatabase::class.java,
                    "point_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}