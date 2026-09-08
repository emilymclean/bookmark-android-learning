package cl.emilym.bookmark.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookmarkEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}

