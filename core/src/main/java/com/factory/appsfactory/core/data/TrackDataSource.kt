package com.factory.appsfactory.core.data

import com.factory.appsfactory.core.domain.Track

interface TrackDataSource {

    suspend fun getTracks(): List<Track>
}