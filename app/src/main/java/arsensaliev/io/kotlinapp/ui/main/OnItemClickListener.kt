package arsensaliev.io.kotlinapp.ui.main

import arsensaliev.io.kotlinapp.data.model.Note

interface OnItemClickListener {
    fun onItemClick(note: Note)
}