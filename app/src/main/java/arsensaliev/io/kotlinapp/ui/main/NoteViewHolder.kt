package arsensaliev.io.kotlinapp.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import arsensaliev.io.kotlinapp.R
import arsensaliev.io.kotlinapp.data.model.Color
import arsensaliev.io.kotlinapp.data.model.Note
import arsensaliev.io.kotlinapp.databinding.ItemNoteBinding

class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val ui: ItemNoteBinding = ItemNoteBinding.bind(itemView)

    fun bind(note: Note, onItemClickListener: OnItemClickListener) {
        ui.title.text = note.title
        ui.body.text = note.note

        val color = when (note.color) {
            Color.WHITE -> R.color.color_white
            Color.VIOLET -> R.color.color_violet
            Color.YELLOW -> R.color.color_yello
            Color.RED -> R.color.color_red
            Color.PINK -> R.color.color_pink
            Color.GREEN -> R.color.color_green
            Color.BLUE -> R.color.color_blue
        }

        itemView.setBackgroundResource(color)
        itemView.setOnClickListener { onItemClickListener.onItemClick(note) }
    }
}

