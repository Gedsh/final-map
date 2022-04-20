package pan.alexander.finalmap.di

import dagger.Binds
import dagger.Module
import pan.alexander.finalmap.data.LocalRepositoryImpl
import pan.alexander.finalmap.domain.LocalRepository

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindLocalRepository(repository: LocalRepositoryImpl): LocalRepository
}
