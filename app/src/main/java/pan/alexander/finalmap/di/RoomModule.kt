package pan.alexander.finalmap.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import pan.alexander.finalmap.database.AppDatabase
import pan.alexander.finalmap.database.MarkerDao
import pan.alexander.finalmap.utils.Configuration.getAppDatabaseName
import javax.inject.Singleton

@Module
class RoomModule {
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase = Room
        .databaseBuilder(
            context,
            AppDatabase::class.java,
            getAppDatabaseName()
        )
        .fallbackToDestructiveMigration()
        .build()


    @Provides
    @Singleton
    fun provideMarkerDao(appDatabase: AppDatabase): MarkerDao = appDatabase.markerDao

}
