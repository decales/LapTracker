import cmpt370.group12.laptracker.model.data.dao.AchievementDao
import cmpt370.group12.laptracker.model.data.dao.TrackDao
import cmpt370.group12.laptracker.model.data.entities.AchievementEntity
import cmpt370.group12.laptracker.model.data.entities.TrackEntity
import cmpt370.group12.laptracker.model.data.mapper.DaoRepository
import cmpt370.group12.laptracker.model.domain.model.Achievement
import cmpt370.group12.laptracker.model.domain.model.Track
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class DaoRepositoryImplementation(
    private val achievementDao: AchievementDao,
    private val trackDao: TrackDao
): DaoRepository {
    override suspend fun achievementUpdate(achievement: Achievement) {
        achievementDao.Achievement_insert(achievement.toAchievementEntity())
    }

    override suspend fun achievementGetAll(): List<Achievement> {
        return achievementDao.Achievement_getAll().map { it.toAchievement() }
    }

    override suspend fun trackInsert(track: Track) {
        trackDao.Track_insert(track.toTrackEntity())
    }

    override suspend fun trackDelete(track: Track) {
        trackDao.Track_delete(track.toTrackEntity())
    }

    override suspend fun trackGetAll(): List<Track> {
        return trackDao.Track_getAll().map { it.toTrack() }
    }


    private fun AchievementEntity.toAchievement(): Achievement {
        return Achievement(
            id = id,
            name = name,
            description = description,
            achieved = achieved,
            iconID = iconID,
            timestamp = timestamp
        )
    }


    private fun Achievement.toAchievementEntity(): AchievementEntity {
        return AchievementEntity(
            id = id,
            name = name,
            description = description,
            achieved = achieved,
            iconID = iconID,
            timestamp = timestamp
        )
    }


    private fun Track.toTrackEntity() : TrackEntity {
        return TrackEntity(
            id = id,
            name = name,
            location = location,
            comment = comment,
            mapImage = mapImage,
            points = Json.encodeToString(points),
            lapTimes = Json.encodeToString(lapTimes)
        )
    }


    private fun TrackEntity.toTrack(): Track {
        return Track(
            id = id,
            name = name,
            location = location,
            comment = comment,
            mapImage = mapImage,
            points = Json.decodeFromString(points),
            lapTimes = Json.decodeFromString(lapTimes)
        )
    }
}