package cl.emilym.bookmark.data

import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException

/** Manages saving and retrieving bookmarks */
class BookmarkRepository @Inject constructor(
    private val bookmarkDao: BookmarkDao
) {

    /**
     * Save the current page count
     */
    suspend fun save(bookmark: Bookmark): Int {
        return bookmarkDao.insert(BookmarkEntity(
            id = bookmark.id,
            title = bookmark.title,
            page = bookmark.page
        )).toInt()
    }

    /**
     * Get the current page count
     */
    fun get(): Flow<List<Bookmark>> = bookmarkDao.getBookmarks().map {
        it.map { Bookmark(
            id = it.id,
            title = it.title,
            page = it.page
        ) }
    }

    /**
     * Get the current page count
     */
    fun get(id: Int): Flow<Bookmark?> = bookmarkDao.getBookmark(id).map {
        it?.let {
            Bookmark(
                id = it.id,
                title = it.title,
                page = it.page
            )
        }
    }

}
