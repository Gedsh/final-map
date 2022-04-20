package pan.alexander.finalmap.data

import kotlinx.coroutines.flow.Flow
import pan.alexander.finalmap.domain.entities.MapMarker

interface LocalDataSource {
    fun getAllMarkers(): Flow<List<MapMarker>>
    fun getMarkerById(markerId: Int): Flow<MapMarker>
    suspend fun addMarker(marker: MapMarker)
    suspend fun updateMarker(marker: MapMarker)
    suspend fun deleteMarker(markerId: Int)
}
