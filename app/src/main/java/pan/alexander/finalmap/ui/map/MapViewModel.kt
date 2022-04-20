package pan.alexander.finalmap.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pan.alexander.finalmap.domain.MainInteractor
import pan.alexander.finalmap.domain.entities.MapMarker
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val mainInteractor: MainInteractor
) : ViewModel() {

    fun getAllMarkers() = mainInteractor.getAllMarkers().asLiveData()

    fun addMarker(marker: MapMarker) = viewModelScope.launch {
        mainInteractor.addMarker(marker)
    }

    fun deleteMarker(markerId: Int) = viewModelScope.launch {
        mainInteractor.deleteMarker(markerId)
    }
}
