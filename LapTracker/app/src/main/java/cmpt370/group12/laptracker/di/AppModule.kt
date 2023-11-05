package cmpt370.group12.laptracker.di

import android.app.Application
import android.util.Log
import androidx.room.Room

import cmpt370.group12.laptracker.data.database.LapTrackerDatabase
import cmpt370.group12.laptracker.data.repository.LapTrackerRepositoryImpl
import cmpt370.group12.laptracker.domain.repository.LapTrackerRepository
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
    fun provideLapTrackerDatabase(app: Application): LapTrackerDatabase {
        return Room.databaseBuilder(
            app,
            LapTrackerDatabase::class.java,
            "LapTracker.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideLapTrackerRepository(db: LapTrackerDatabase): LapTrackerRepository {
        Log.d("KRIS","GOT HERE")
        return LapTrackerRepositoryImpl(db.achievementDao,db.commentDao,db.mapPointDao,db.runsDao,db.runtimesDao,db.trackDao)
    }
}