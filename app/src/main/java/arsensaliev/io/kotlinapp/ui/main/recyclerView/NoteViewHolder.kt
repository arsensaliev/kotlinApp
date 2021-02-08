package arsensaliev.io.kotlinapp.ui.main.recyclerView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import arsensaliev.io.kotlinapp.R
import arsensaliev.io.kotlinapp.data.model.note.Color
import arsensaliev.io.kotlinapp.data.model.note.Note
import arsensaliev.io.kotlinapp.databinding.ItemNoteBinding
import arsensaliev.io.kotlinapp.ui.main.interfaces.OnItemClickListener

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


        itemView.setBackgroundColor(itemView.context.resources.getColor(color))
        itemView.setOnClickListener { onItemClickListener.onItemClick(note) }
    }
}

