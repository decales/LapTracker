package cmpt370.group12.laptracker.di

import android.app.Application
import androidx.room.Room

import cmpt370.group12.laptracker.data.database.LapTrackerDatabase
import cmpt370.group12.laptracker.data.repository.LapTrackerRepositoryImpl
import cmpt370.group12.laptracker.domain.repository.LapTrackerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
const val DEBUGDB: Boolean = false
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
private var DBNAME:String = "LapTrackerDB.db"




    @Singleton
    @Provides
    fun provideLapTrackerDatabase(app: Application): LapTrackerDatabase {
        if (DEBUGDB) {
            val rnd = (1000..5000).random().toString()
            DBNAME = "$rnd.db"
        }
        return Room.databaseBuilder(
            app,
            LapTrackerDatabase::class.java,
            DBNAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideLapTrackerRepository(db: LapTrackerDatabase): LapTrackerRepository {
        return LapTrackerRepositoryImpl(db.achievementDao,db.commentDao,db.mapPointDao,db.runsDao,db.runtimesDao,db.trackDao)
    }
}