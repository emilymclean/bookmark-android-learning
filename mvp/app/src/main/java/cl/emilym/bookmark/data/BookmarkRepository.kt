package cl.emilym.bookmark.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import java.io.File

/** Manages saving and retrieving bookmarks */
class BookmarkRepository @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    /**
     * Save the current page count
     *
     * @throws java.io.IOException
     */
    suspend fun save(page: Int) {
        val file = getFile()
        context.filesDir.mkdirs()

        file.writeText(page.toString())
    }

    /**
     * Get the current page count
     *
     * @throws java.io.IOException
     * @throws NumberFormatException
     */
    suspend fun get(): Int? {
        val file = getFile()

        if (!file.exists()) return null

        return file.readText().toInt()
    }

    private fun getFile(): File = File(context.filesDir, "bookmark.txt")

}