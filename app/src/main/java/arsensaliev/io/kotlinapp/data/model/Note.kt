package arsensaliev.io.kotlinapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Note(
    val id: String,
    val title: String,
    val note: String,
    val color: Color = Color.WHITE,
    val lastChanged: Date = Date()
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (id != other.id) return false
        if (title != other.title) return false
        if (note != other.note) return false
        if (color != other.color) return false
        if (lastChanged != other.lastChanged) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + note.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + lastChanged.hashCode()
        return result
    }
}

enum class Color {
    WHITE,
    YELLOW,
    GREEN,
    BLUE,
    RED,
    VIOLET,
    PINK
}