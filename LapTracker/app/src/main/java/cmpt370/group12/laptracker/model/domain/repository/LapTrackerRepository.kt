package cmpt370.group12.laptracker.model.domain.repository

import cmpt370.group12.laptracker.model.domain.model.Achievement
import cmpt370.group12.laptracker.model.domain.model.Comment
import cmpt370.group12.laptracker.model.domain.model.MapPoint
import cmpt370.group12.laptracker.model.domain.model.Runs
import cmpt370.group12.laptracker.model.domain.model.Runtimes
import cmpt370.group12.laptracker.model.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface LapTrackerRepository {

    suspend fun MapPoint_insert(mappoint: MapPoint)
    suspend fun MapPoint_delete(mappoint: MapPoint)
    fun MapPoints_getByTrackId(fromTrackId: Int): Flow<List<MapPoint>>
    suspend fun MapPoints_getAll(): List<MapPoint>
    fun MapPoints_getAllFlow(): Flow<List<MapPoint>>


    suspend fun Achievement_insert(achievement: Achievement)
    suspend fun Achievement_delete(achievement: Achievement)
    fun Achievement_getAllFlow(): Flow<List<Achievement>>
    suspend fun Achievement_getAll(): List<Achievement>


    suspend fun Comment_insert(comment: Comment)
    suspend fun Comment_delete(comment: Comment)
    fun Comments_getCommentsFromTrackId(trackId: Int): Flow<List<Comment>>
    suspend fun Comments_getAll(): List<Comment>
    fun Comments_getAllFlow(): Flow<List<Comment>>


    suspend fun Runs_insert(runs: Runs) : Long
    suspend fun Runs_delete(runs: Runs)
    suspend fun Runs_getByTrackId(trackId: Long?): List<Runs>
    fun Runs_getAllFlow(): Flow<List<Runs>>
    suspend fun Runs_getAll(): List<Runs>
    suspend fun Runs_getMax(): Runs
    suspend fun Runs_getMin(): Runs

    suspend fun RunTimes_insert(runtimes: Runtimes)
    suspend fun RunTimes_delete(runtimes: Runtimes)
    fun RunTimes_getByRunId(runId: Int): Flow<List<Runtimes>>
    fun RunTimes_getAllFlow(): Flow<List<Runtimes>>
    suspend fun RunTimes_getAll(): List<Runtimes>


    suspend fun Track_insert(track: Track): Long
    suspend fun Track_delete(track: Track)
    fun Track_getById(id: Int): Flow<List<Track>>
    fun Track_getAllFlow(): Flow<List<Track>>
    suspend fun Track_getAll(): List<Track>
}