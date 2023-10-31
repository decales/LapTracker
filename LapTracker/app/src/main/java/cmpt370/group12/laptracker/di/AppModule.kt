package cmpt370.group12.laptracker.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import cmpt370.group12.laptracker.data.MapPointDatabase
import cmpt370.group12.laptracker.data.MapPointRepositoryImpl
import cmpt370.group12.laptracker.domain.repository.MapPointRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMapPointDatabase(app: Application): MapPointDatabase {
        return Room.databaseBuilder(
            app,
            MapPointDatabase::class.java,
            "mapmpoints.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideMapPointRepository(db: MapPointDatabase): MapPointRepository {
        Log.d("KRIS","GOT HERE")
        return MapPointRepositoryImpl(db.dao)
    }
}