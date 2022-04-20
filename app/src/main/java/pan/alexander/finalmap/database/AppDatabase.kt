package pan.alexander.finalmap.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pan.alexander.finalmap.domain.entities.MapMarker

@Database(entities = [MapMarker::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val markerDao: MarkerDao
}
