package cmpt370.group12.laptracker.domain.repository

import cmpt370.group12.laptracker.domain.model.Achievement
import cmpt370.group12.laptracker.domain.model.Comment
import cmpt370.group12.laptracker.domain.model.MapPoint
import cmpt370.group12.laptracker.domain.model.Runs
import cmpt370.group12.laptracker.domain.model.Runtimes
import cmpt370.group12.laptracker.domain.model.Track
import kotlinx.coroutines.flow.Flow
interface LapTrackerRepository {

    suspend fun MapPoint_insert(mappoint: MapPoint)
    suspend fun MapPoint_delete(mappoint: MapPoint)
    fun MapPoint_getByTrackId(fromTrackId: Int): Flow<List<MapPoint>>
    fun MapPoint_getAll(): Flow<List<MapPoint>>


    suspend fun Achievement_insert(achievement: Achievement)
    suspend fun Achievement_delete(achievement: Achievement)
    fun Achievement_getFinished(): Flow<List<Achievement>>
    fun Achievement_getUnFinished(): Flow<List<Achievement>>
    fun Achievement_getAll(): Flow<List<Achievement>>

    suspend fun Comment_insert(comment: Comment)
    suspend fun Comment_delete(comment: Comment)
    fun Comment_getCommentsFromTrackId(trackId: Int): Flow<List<Comment>>
    fun Comment_getAllComments(): Flow<List<Comment>>


    suspend fun Runs_insert(runs: Runs)
    suspend fun Runs_delete(runs: Runs)
    fun Runs_getByTrackId(trackId: Int): Flow<List<Runs>>
    fun Runs_getAll(): Flow<List<Runs>>


    suspend fun RunTimes_insert(runtimes: Runtimes)
    suspend fun RunTimes_delete(runtimes: Runtimes)
    fun RunTimes_getByRunId(runId: Int): Flow<List<Runtimes>>

    suspend fun Track_insert(track: Track)
    suspend fun Track_delete(track: Track)
    fun Track_getById(id: Int): Flow<List<Track>>
    fun Track_getAll(): Flow<List<Track>>
}