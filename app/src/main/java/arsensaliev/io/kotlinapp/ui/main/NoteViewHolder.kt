package arsensaliev.io.kotlinapp.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import arsensaliev.io.kotlinapp.R
import arsensaliev.io.kotlinapp.data.model.Note
import com.google.android.material.textview.MaterialTextView

class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title = itemView.findViewById<MaterialTextView>(R.id.title)
    private val body = itemView.findViewById<MaterialTextView>(R.id.body)

    fun bind(note: Note) {
        title.text = note.title
        body.text = note.note
        itemView.setBackgroundColor(note.color)
    }
}