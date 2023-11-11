package cmpt370.group12.laptracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import cmpt370.group12.laptracker.data.entities.AchievementEntity
import cmpt370.group12.laptracker.data.entities.CommentEntity
import cmpt370.group12.laptracker.data.entities.MapPointEntity
import cmpt370.group12.laptracker.data.entities.RunTimesEntity
import cmpt370.group12.laptracker.data.entities.RunsEntity
import cmpt370.group12.laptracker.data.entities.TrackEntity
import cmpt370.group12.laptracker.data.dao.AchievementDao
import cmpt370.group12.laptracker.data.dao.CommentDao
import cmpt370.group12.laptracker.data.dao.MapPointDao
import cmpt370.group12.laptracker.data.dao.RunsDao
import cmpt370.group12.laptracker.data.dao.RuntimesDao
import cmpt370.group12.laptracker.data.dao.TrackDao

@Database(
    entities = [MapPointEntity::class, CommentEntity::class, AchievementEntity::class,
               TrackEntity::class, RunsEntity::class, RunTimesEntity::class],
    version = 1
)
abstract class LapTrackerDatabase: RoomDatabase() {

    abstract val mapPointDao: MapPointDao
    abstract val commentDao: CommentDao
    abstract val achievementDao: AchievementDao
    abstract val trackDao: TrackDao
    abstract val runsDao: RunsDao
    abstract val runtimesDao : RuntimesDao

}