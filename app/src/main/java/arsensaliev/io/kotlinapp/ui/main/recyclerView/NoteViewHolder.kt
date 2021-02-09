package arsensaliev.io.kotlinapp.ui.main.recyclerView

import android.view.View
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import arsensaliev.io.kotlinapp.data.model.note.Note
import arsensaliev.io.kotlinapp.databinding.ItemNoteBinding
import arsensaliev.io.kotlinapp.ui.getColorInt
import arsensaliev.io.kotlinapp.ui.main.interfaces.OnItemClickListener

class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val ui: ItemNoteBinding = ItemNoteBinding.bind(itemView)

    fun bind(note: Note, onItemClickListener: OnItemClickListener) {
        ui.title.text = note.title
        ui.body.text = note.note

        (itemView as CardView).setCardBackgroundColor(note.color.getColorInt(itemView.context))
        itemView.setOnClickListener { onItemClickListener.onItemClick(note) }
    }
}

