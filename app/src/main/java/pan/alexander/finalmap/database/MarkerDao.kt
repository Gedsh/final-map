package pan.alexander.finalmap.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pan.alexander.finalmap.domain.entities.MapMarker

@Dao
interface MarkerDao {

    @Query("SELECT * FROM markers")
    fun getAll(): Flow<List<MapMarker>>

    @Query("SELECT * FROM markers WHERE id=:markerId LIMIT 1")
    fun getMarkerById(markerId: Int): Flow<MapMarker>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(marker: MapMarker)

    @Update
    suspend fun update(marker: MapMarker)

    @Query("DELETE FROM markers WHERE id=:markerId")
    suspend fun delete(markerId: Int)
}
