package cl.emilym.bookmark.di

import android.content.Context
import androidx.room.Room
import cl.emilym.bookmark.data.AppDatabase
import cl.emilym.bookmark.data.BookmarkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "bookmark_db"
        ).build()
    }

    @Provides
    fun provideBookmarkDao(database: AppDatabase): BookmarkDao {
        return database.bookmarkDao()
    }
}
