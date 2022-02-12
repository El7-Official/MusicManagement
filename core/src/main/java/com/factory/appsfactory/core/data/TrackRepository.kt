package com.factory.appsfactory.core.data

import com.factory.appsfactory.core.domain.Track

class TrackRepository(var taskDataSource: TrackDataSource) {
    // Cache
    suspend fun getTracks(): List<Track> = taskDataSource.getTracks()
}