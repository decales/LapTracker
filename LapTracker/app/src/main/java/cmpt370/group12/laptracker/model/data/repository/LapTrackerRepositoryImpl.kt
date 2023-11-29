package cmpt370.group12.laptracker.model.data.repository

import cmpt370.group12.laptracker.model.data.dao.AchievementDao
import cmpt370.group12.laptracker.model.data.dao.CommentDao
import cmpt370.group12.laptracker.model.data.dao.MapPointDao
import cmpt370.group12.laptracker.model.data.dao.RunsDao
import cmpt370.group12.laptracker.model.data.dao.RuntimesDao
import cmpt370.group12.laptracker.model.data.dao.TrackDao
import cmpt370.group12.laptracker.model.data.mapper.toAchievement
import cmpt370.group12.laptracker.model.data.mapper.toAchievementEntity
import cmpt370.group12.laptracker.model.data.mapper.toComment
import cmpt370.group12.laptracker.model.data.mapper.toCommentEntity
import cmpt370.group12.laptracker.model.data.mapper.toMapPoint
import cmpt370.group12.laptracker.model.data.mapper.toMapPointEntity
import cmpt370.group12.laptracker.model.data.mapper.toRunTimes
import cmpt370.group12.laptracker.model.data.mapper.toRunTimesEntity
import cmpt370.group12.laptracker.model.data.mapper.toRuns
import cmpt370.group12.laptracker.model.data.mapper.toRunsEntity
import cmpt370.group12.laptracker.model.data.mapper.toTrack
import cmpt370.group12.laptracker.model.data.mapper.toTrackEntity
import cmpt370.group12.laptracker.model.domain.model.Achievement
import cmpt370.group12.laptracker.model.domain.model.Comment
import cmpt370.group12.laptracker.model.domain.model.MapPoint
import cmpt370.group12.laptracker.model.domain.model.Runs
import cmpt370.group12.laptracker.model.domain.model.Runtimes
import cmpt370.group12.laptracker.model.domain.model.Track
import cmpt370.group12.laptracker.model.domain.repository.LapTrackerRepository
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

    // ###################### MAP FUNCTIONS ######################
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

    override suspend fun MapPoints_getAll(): List<MapPoint> {
        return mapPointDao.MapPoints_getAll().map { it.toMapPoint()}
    }

    override fun MapPoints_getAllFlow(): Flow<List<MapPoint>> {
        return mapPointDao.MapPoints_getAllFlow().map { mappoint ->
            mappoint.map { it.toMapPoint() }
        }
    }


    // ###################### ACHIEVEMENT FUNCTIONS ######################
    override suspend fun Achievement_getAll(): List<Achievement> {
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
            it.map { it.toAchievement() }
        }
    }


    // ###################### COMMENT FUNCTIONS ######################
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


    // ###################### RUN FUNCTIONS ######################
    override suspend fun Runs_insert(runs: Runs){
        runsDao.Runs_insert(runs.toRunsEntity())
    }

    override suspend fun Runs_delete(runs: Runs){
        runsDao.Runs_delete(runs.toRunsEntity())
    }

    override suspend fun Runs_getByTrackId(trackId: Int): List<Runs>{
        return runsDao.Runs_getByTrackId(trackId).map { it.toRuns() }
    }

    override fun Runs_getAllFlow(): Flow<List<Runs>>{
        return runsDao.Runs_getAllFlow().map { it ->
            it.map { it.toRuns() }
        }
    }

    override suspend fun Runs_getAll(): List<Runs>{
        return runsDao.Runs_getAll().map {  it.toRuns() }
    }
    override suspend fun Runs_getMax(): Runs {
        return runsDao.Runs_getMax().toRuns()
    }
    override suspend fun Runs_getMin(): Runs {
        return runsDao.Runs_getMin().toRuns()
    }


    // ###################### RUNTIME FUNCTIONS ######################
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


    // ###################### TRACK FUNCTIONS ######################
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