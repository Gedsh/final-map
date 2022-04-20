package pan.alexander.finalmap.ui.markers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pan.alexander.finalmap.domain.MainInteractor
import javax.inject.Inject

class MarkersViewModel @Inject constructor(
    private val mainInteractor: MainInteractor
) : ViewModel() {

    fun getAllMarkers() = mainInteractor.getAllMarkers().asLiveData()

    fun deleteMarker(markerId: Int) = viewModelScope.launch {
        mainInteractor.deleteMarker(markerId)
    }
}
