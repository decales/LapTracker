package cmpt370.group12.laptracker.data.mapper

import cmpt370.group12.laptracker.data.entities.AchievementEntity
import cmpt370.group12.laptracker.data.entities.CommentEntity
import cmpt370.group12.laptracker.data.entities.MapPointEntity
import cmpt370.group12.laptracker.data.entities.RunTimesEntity
import cmpt370.group12.laptracker.data.entities.RunsEntity
import cmpt370.group12.laptracker.data.entities.TrackEntity
import cmpt370.group12.laptracker.domain.model.Achievement
import cmpt370.group12.laptracker.domain.model.Comment
import cmpt370.group12.laptracker.domain.model.MapPoint
import cmpt370.group12.laptracker.domain.model.Runs
import cmpt370.group12.laptracker.domain.model.Runtimes
import cmpt370.group12.laptracker.domain.model.Track

fun MapPointEntity.toMapPoint(): MapPoint {
    return MapPoint(
        id = id,
        fromTrackId = fromTrackId,
        latitude = latitude,
        longitude = longitude,
        name = name,
        sequenceNumber = sequenceNumber

    )
}

fun MapPoint.toMapPointEntity(): MapPointEntity {
    return MapPointEntity(
        id = id,
        fromTrackId = fromTrackId,
        latitude = latitude,
        longitude = longitude,
        name = name,
        sequenceNumber = sequenceNumber
    )
}

fun CommentEntity.toComment(): Comment {
    return Comment(
        id = id,
        fromTrackId = fromTrackId,
        comment = comment,
        timestamp = timestamp
    )
}

fun Comment.toCommentEntity(): CommentEntity {
    return CommentEntity(
        id = id,
        fromTrackId = fromTrackId,
        comment = comment,
        timestamp = timestamp
    )
}
fun AchievementEntity.toAchievement(): Achievement {
    return Achievement(
        id = id,
        name = name,
        description = description,
        achieved = achieved,
        iconID = iconID,
        timestamp = timestamp
    )
}
fun Achievement.toAchievementEntity(): AchievementEntity {
    return AchievementEntity(
        id = id,
        name = name,
        description = description,
        achieved = achieved,
        iconID = iconID,
        timestamp = timestamp
    )
}

fun Track.toTrackEntity() : TrackEntity {
    return TrackEntity(
        id = id,
        name = name
    )
}

fun TrackEntity.toTrack(): Track {
    return Track(
        id = id,
        name = name
    )
}

fun Runs.toRunsEntity(): RunsEntity {
    return RunsEntity(
        id = id,
        fromTrackId = fromTrackId,
        startTime = startTime,
        endTime = endTime
    )
}
fun RunsEntity.toRuns(): Runs {
    return Runs(
        id = id,
        fromTrackId = fromTrackId,
        startTime = startTime,
        endTime = endTime
    )
}

fun Runtimes.toRunTimesEntity(): RunTimesEntity {
    return RunTimesEntity(
        id = id,
        fromRunId = fromRunId,
        fromMapPointId = fromMapPointId,
        timestamp = timestamp
    )
}
fun RunTimesEntity.toRunTimes(): Runtimes{
    return Runtimes(
        id = id,
        fromRunId = fromRunId,
        fromMapPointId = fromMapPointId,
        timestamp = timestamp
    )
}