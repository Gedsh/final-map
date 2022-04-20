package pan.alexander.finalmap.domain

import kotlinx.coroutines.flow.Flow
import pan.alexander.finalmap.domain.entities.MapMarker
import javax.inject.Inject

class MainInteractor @Inject constructor(
    private val localRepository: LocalRepository
) {
    fun getAllMarkers(): Flow<List<MapMarker>> = localRepository.getAllMarkers()

    fun getMarkerById(markerId: Int) = localRepository.getMarkerById(markerId)

    suspend fun addMarker(marker: MapMarker) {
        localRepository.addMarker(marker)
    }

    suspend fun updateMarker(marker: MapMarker) {
        localRepository.updateMarker(marker)
    }

    suspend fun deleteMarker(markerId: Int) {
        localRepository.deleteMarker(markerId)
    }
}
