package cmpt370.group12.laptracker.data
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MapPointEntity::class,CommentEntity::class,AchievementEntity::class,
               TrackEntity::class,RunEntity::class,RunTimeEntity::class],
    version = 1
)
abstract class LapTrackerDatabase: RoomDatabase() {

    abstract val dao: LapTrackerDao
}