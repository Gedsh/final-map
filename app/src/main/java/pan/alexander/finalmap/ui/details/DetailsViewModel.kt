package pan.alexander.finalmap.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pan.alexander.finalmap.domain.MainInteractor
import pan.alexander.finalmap.domain.entities.MapMarker
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val mainInteractor: MainInteractor
) : ViewModel() {

    fun getMarkerById(markerId: Int) = mainInteractor.getMarkerById(markerId).asLiveData()

    fun updateMarker(marker: MapMarker) = viewModelScope.launch {
        mainInteractor.updateMarker(marker)
    }

    fun deleteMarker(markerId: Int) = viewModelScope.launch {
        mainInteractor.deleteMarker(markerId)
    }

}
