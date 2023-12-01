package cmpt370.group12.laptracker.model.data.mapper

import cmpt370.group12.laptracker.model.domain.model.Achievement
import cmpt370.group12.laptracker.model.domain.model.Track

interface DaoRepository {

    suspend fun achievementUpdate(achievement: Achievement)
    suspend fun achievementGetAll(): List<Achievement>
    suspend fun trackInsert(track: Track)
    suspend fun trackDelete(track: Track)
    suspend fun trackGetAll(): List<Track>

}