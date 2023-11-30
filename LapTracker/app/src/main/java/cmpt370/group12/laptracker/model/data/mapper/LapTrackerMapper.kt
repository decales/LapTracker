package cmpt370.group12.laptracker.model.data.mapper

import cmpt370.group12.laptracker.model.data.entities.AchievementEntity
import cmpt370.group12.laptracker.model.data.entities.CommentEntity
import cmpt370.group12.laptracker.model.data.entities.MapPointEntity
import cmpt370.group12.laptracker.model.data.entities.RunTimesEntity
import cmpt370.group12.laptracker.model.data.entities.RunsEntity
import cmpt370.group12.laptracker.model.data.entities.TrackEntity
import cmpt370.group12.laptracker.model.domain.model.Achievement
import cmpt370.group12.laptracker.model.domain.model.Comment
import cmpt370.group12.laptracker.model.domain.model.MapPoint
import cmpt370.group12.laptracker.model.domain.model.Runs
import cmpt370.group12.laptracker.model.domain.model.Runtimes
import cmpt370.group12.laptracker.model.domain.model.Track

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
        id = id!!,
        name = name,
        location = location,
        comment = comment,
        mapImage = mapImage
    )
}

fun TrackEntity.toTrack(): Track {
    return Track(
        id = id,
        name = name,
        location = location,
        comment = comment,
        mapImage = mapImage
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
fun RunTimesEntity.toRunTimes(): Runtimes {
    return Runtimes(
        id = id,
        fromRunId = fromRunId,
        fromMapPointId = fromMapPointId,
        timestamp = timestamp
    )
}