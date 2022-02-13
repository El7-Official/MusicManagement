package com.factory.appsfactory.challenge.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.factory.appsfactory.challenge.framework.db.dao.AlbumDao
import com.factory.appsfactory.challenge.framework.db.dao.ArtistDao
import com.factory.appsfactory.challenge.framework.db.models.Album
import com.factory.appsfactory.challenge.framework.db.models.Artist
import com.factory.appsfactory.challenge.framework.db.models.Track
import com.factory.appsfactory.challenge.framework.db.utils.DbConstants
import com.factory.appsfactory.challenge.framework.db.utils.Migrations
import javax.inject.Inject


@Database(
    entities = [Album::class, Artist::class, Track:: class],
    version = Migrations.DB_VERSION,
    exportSchema = false
)
abstract class LastFMDatabase @Inject constructor() : RoomDatabase() {

    abstract fun albumDao(): AlbumDao

    abstract fun artistDao(): ArtistDao

    companion object {
        @Volatile
        private var INSTANCE: LastFMDatabase? = null

        fun getInstance(context: Context): LastFMDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            LastFMDatabase::class.java,
            DbConstants.DB_NAME
        ).build()
    }
}