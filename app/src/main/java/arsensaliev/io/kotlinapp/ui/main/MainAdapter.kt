package arsensaliev.io.kotlinapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import arsensaliev.io.kotlinapp.R
import arsensaliev.io.kotlinapp.data.model.Note

class MainAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<NoteViewHolder>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position], onItemClickListener)
    }

    override fun getItemCount() = notes.size
}