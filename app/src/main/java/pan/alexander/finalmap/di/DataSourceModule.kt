package pan.alexander.finalmap.di

import dagger.Binds
import dagger.Module
import pan.alexander.finalmap.data.LocalDataSource
import pan.alexander.finalmap.data.LocalDataSourceImpl

@Module
abstract class DataSourceModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource
}
