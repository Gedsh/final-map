package pan.alexander.finalmap

import android.app.Application
import pan.alexander.finalmap.di.AppComponent
import pan.alexander.finalmap.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        initDaggerComponent()
    }

    private fun initDaggerComponent() {
        appComponent = DaggerAppComponent
            .builder()
            .appContext(applicationContext)
            .build()
    }
}
