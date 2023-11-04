package cmpt370.group12.laptracker.data
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MapPointEntity::class],
    version = 1
)
abstract class MapPointDatabase: RoomDatabase() {

    abstract val dao: MapPointDao
}