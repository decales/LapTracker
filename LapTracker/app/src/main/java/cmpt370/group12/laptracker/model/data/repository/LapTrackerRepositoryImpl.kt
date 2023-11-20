package cmpt370.group12.laptracker.data.repository

import cmpt370.group12.laptracker.data.dao.AchievementDao
import cmpt370.group12.laptracker.data.dao.CommentDao
import cmpt370.group12.laptracker.data.dao.MapPointDao
import cmpt370.group12.laptracker.data.dao.RunsDao
import cmpt370.group12.laptracker.data.dao.RuntimesDao
import cmpt370.group12.laptracker.data.dao.TrackDao
import cmpt370.group12.laptracker.data.entities.AchievementEntity
import cmpt370.group12.laptracker.data.mapper.toAchievement
import cmpt370.group12.laptracker.data.mapper.toAchievementEntity
import cmpt370.group12.laptracker.data.mapper.toComment
import cmpt370.group12.laptracker.data.mapper.toCommentEntity
import cmpt370.group12.laptracker.data.mapper.toMapPoint
import cmpt370.group12.laptracker.data.mapper.toMapPointEntity
import cmpt370.group12.laptracker.data.mapper.toRunTimes
import cmpt370.group12.laptracker.data.mapper.toRunTimesEntity
import cmpt370.group12.laptracker.data.mapper.toRuns
import cmpt370.group12.laptracker.data.mapper.toRunsEntity
import cmpt370.group12.laptracker.data.mapper.toTrack
import cmpt370.group12.laptracker.data.mapper.toTrackEntity
import cmpt370.group12.laptracker.domain.model.Achievement
import cmpt370.group12.laptracker.domain.model.Comment
import cmpt370.group12.laptracker.domain.model.MapPoint
import cmpt370.group12.laptracker.domain.model.Runs
import cmpt370.group12.laptracker.domain.model.Runtimes
import cmpt370.group12.laptracker.domain.model.Track
import cmpt370.group12.laptracker.domain.repository.LapTrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class LapTrackerRepositoryImpl(

    private val achievementDao: AchievementDao,
    private val commentDao: CommentDao,
    private val mapPointDao: MapPointDao,
    private val runsDao: RunsDao,
    private val runtimesDao: RuntimesDao,
    private val trackDao: TrackDao

): LapTrackerRepository {


    override suspend fun MapPoint_insert(mappoint: MapPoint) {
        mapPointDao.MapPoint_insert(mappoint.toMapPointEntity())
    }

    override suspend fun MapPoint_delete(mappoint: MapPoint) {
        mapPointDao.MapPoint_delete(mappoint.toMapPointEntity())
    }

    override fun MapPoints_getByTrackId(fromTrackId: Int): Flow<List<MapPoint>> {
       return mapPointDao.MapPoints_getPointsByTrackId(fromTrackId).map { mappoint ->
      mappoint.map { it.toMapPoint() }
        }

    }
    override suspend fun MapPoints_getAll(): List<MapPoint>
    {
        return mapPointDao.MapPoints_getAll().map { it.toMapPoint()}

    }
    override fun MapPoints_getAllFlow(): Flow<List<MapPoint>> {
        return mapPointDao.MapPoints_getAllFlow().map { mappoint ->
            mappoint.map { it.toMapPoint() }
        }

    }

    override suspend fun Achievement_getAll(): List<Achievement>
    {
        return achievementDao.Achievement_getAll2().map{ it.toAchievement()}

    }


    override suspend fun Achievement_insert(achievement: Achievement){
        achievementDao.Achievement_insert(achievement.toAchievementEntity())
    }
    override suspend fun Achievement_delete(achievement: Achievement){
        achievementDao.Achievement_delete(achievement.toAchievementEntity())
    }

    override fun Achievement_getAllFlow(): Flow<List<Achievement>>{
        return achievementDao.Achievement_getAll().map { it ->
            it.map { it.toAchievement() }}
    }

    override suspend fun Comment_insert(comment: Comment){
        commentDao.Comment_insert(comment.toCommentEntity())
    }
    override suspend fun Comment_delete(comment: Comment){
        commentDao.Comment_delete(comment.toCommentEntity())
    }
    override fun Comments_getCommentsFromTrackId(trackId: Int): Flow<List<Comment>> {
        return commentDao.Comments_getCommentsFromTrackId(trackId).map { it ->
            it.map { it.toComment() }
        }
    }
    override fun Comments_getAllFlow(): Flow<List<Comment>>{
        return commentDao.Comments_getAllFlow().map { it ->
            it.map { it.toComment() }
        }
    }
    override suspend fun Comments_getAll(): List<Comment>{
        return commentDao.Comments_getAll().map { it.toComment() }

    }

    override suspend fun Runs_insert(runs: Runs){
        runsDao.Runs_insert(runs.toRunsEntity())
    }
    override suspend fun Runs_delete(runs: Runs){
        runsDao.Runs_delete(runs.toRunsEntity())
    }
    override fun Runs_getByTrackId(trackId: Int): Flow<List<Runs>>{
        return runsDao.Runs_getByTrackId(trackId).map { it ->
            it.map { it.toRuns() }
        }
    }
    override fun Runs_getAllFlow(): Flow<List<Runs>>{
        return runsDao.Runs_getAllFlow().map { it ->
            it.map { it.toRuns() }
        }
    }
    override suspend fun Runs_getAll(): List<Runs>{
        return runsDao.Runs_getAll().map {  it.toRuns() }
    }


    override suspend fun RunTimes_insert(runtimes: Runtimes){
        runtimesDao.RunTimes_insert(runtimes.toRunTimesEntity())
    }
    override suspend fun RunTimes_delete(runtimes: Runtimes){
            runtimesDao.RunTimes_delete(runtimes.toRunTimesEntity())
    }
    override fun RunTimes_getByRunId(runId: Int): Flow<List<Runtimes>>{
        return runtimesDao.RunTimes_getByRunId(runId).map { it ->
            it.map { it.toRunTimes() }
        }
    }
    override fun RunTimes_getAllFlow(): Flow<List<Runtimes>>{
        return runtimesDao.RunTimes_getAllFlow().map { it ->
            it.map { it.toRunTimes() }
        }
    }
    override suspend fun RunTimes_getAll(): List<Runtimes>{
        return runtimesDao.RunTimes_getAll().map { it.toRunTimes() }

    }

    override suspend fun Track_insert(track: Track){
        trackDao.Track_insert(track.toTrackEntity())
    }
    override suspend fun Track_delete(track: Track){
        trackDao.Track_delete(track.toTrackEntity())
    }
    override fun Track_getById(id: Int): Flow<List<Track>>{
        return trackDao.Track_getById(id).map { it ->
            it.map { it.toTrack() }
        }
    }
    override fun Track_getAllFlow(): Flow<List<Track>>{
        return trackDao.Track_getAllFlow().map { it ->
            it.map { it.toTrack() }
        }
    }
    override suspend fun Track_getAll(): List<Track>{
        return trackDao.Track_getAll().map { it.toTrack() }

    }


}