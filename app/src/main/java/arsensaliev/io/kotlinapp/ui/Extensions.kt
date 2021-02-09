package arsensaliev.io.kotlinapp.ui

import android.content.Context
import androidx.core.content.ContextCompat
import arsensaliev.io.kotlinapp.R
import arsensaliev.io.kotlinapp.data.model.note.Color
import java.text.SimpleDateFormat
import java.util.*

const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"

fun Date.format(): String = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(this)


val date: String = Date().format()

fun Color.getColorInt(context: Context): Int =
    ContextCompat.getColor(
        context, when (this) {
            Color.WHITE -> R.color.color_white
            Color.VIOLET -> R.color.color_violet
            Color.YELLOW -> R.color.color_yello
            Color.RED -> R.color.color_red
            Color.PINK -> R.color.color_pink
            Color.GREEN -> R.color.color_green
            Color.BLUE -> R.color.color_blue
        }
    )

