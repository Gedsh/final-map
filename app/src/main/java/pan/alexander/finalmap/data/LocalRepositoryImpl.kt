package pan.alexander.finalmap.data

import kotlinx.coroutines.flow.Flow
import pan.alexander.finalmap.domain.LocalRepository
import pan.alexander.finalmap.domain.entities.MapMarker
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : LocalRepository {

    override fun getAllMarkers(): Flow<List<MapMarker>> =
        localDataSource.getAllMarkers()

    override fun getMarkerById(markerId: Int) =
        localDataSource.getMarkerById(markerId)

    override suspend fun addMarker(marker: MapMarker) {
        localDataSource.addMarker(marker)
    }

    override suspend fun updateMarker(marker: MapMarker) {
        localDataSource.updateMarker(marker)
    }

    override suspend fun deleteMarker(markerId: Int) {
        localDataSource.deleteMarker(markerId)
    }
}
