package pan.alexander.finalmap.data

import kotlinx.coroutines.flow.Flow
import pan.alexander.finalmap.database.MarkerDao
import pan.alexander.finalmap.domain.entities.MapMarker
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val markerDao: MarkerDao
) : LocalDataSource {

    override fun getAllMarkers(): Flow<List<MapMarker>> =
        markerDao.getAll()

    override fun getMarkerById(markerId: Int) =
        markerDao.getMarkerById(markerId)

    override suspend fun addMarker(marker: MapMarker) {
        markerDao.insert(marker)
    }

    override suspend fun updateMarker(marker: MapMarker) {
        markerDao.update(marker)
    }

    override suspend fun deleteMarker(markerId: Int) {
        markerDao.delete(markerId)
    }
}
