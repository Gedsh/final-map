package pan.alexander.finalmap.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import pan.alexander.finalmap.ui.details.DetailsFragment
import pan.alexander.finalmap.ui.map.MapFragment
import pan.alexander.finalmap.ui.markers.MarkersFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class,
        RoomModule::class,
        RepositoryModule::class,
        DataSourceModule::class,
        LocationModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(context: Context): Builder
        fun build(): AppComponent
    }

    fun inject(fragment: MapFragment)
    fun inject(fragment: MarkersFragment)
    fun inject(fragment: DetailsFragment)
}
