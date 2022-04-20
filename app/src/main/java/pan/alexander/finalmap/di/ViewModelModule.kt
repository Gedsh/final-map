package pan.alexander.finalmap.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ObsoleteCoroutinesApi
import pan.alexander.finalmap.ui.details.DetailsViewModel
import pan.alexander.finalmap.ui.map.MapViewModel
import pan.alexander.finalmap.ui.markers.MarkersViewModel

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun provideViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    abstract fun bindMapViewModel(
        mapViewModel: MapViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MarkersViewModel::class)
    abstract fun bindMarkersViewModel(
        markersViewModel: MarkersViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsViewModel(
        detailsViewModel: DetailsViewModel
    ): ViewModel
}
