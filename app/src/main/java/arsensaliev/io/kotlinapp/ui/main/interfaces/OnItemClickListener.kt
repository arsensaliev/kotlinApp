package arsensaliev.io.kotlinapp.ui.main.interfaces

import arsensaliev.io.kotlinapp.data.model.Note


interface OnItemClickListener {
    fun onItemClick(note: Note)
}