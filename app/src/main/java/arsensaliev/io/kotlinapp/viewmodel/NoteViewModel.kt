package arsensaliev.io.kotlinapp.viewmodel

import androidx.lifecycle.ViewModel
import arsensaliev.io.kotlinapp.data.model.Note
import arsensaliev.io.kotlinapp.data.model.Repository

class NoteViewModel(private val repository: Repository = Repository) : ViewModel() {

    private var pendingNote: Note? = null

    fun saveChanges(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        if (pendingNote != null) {
            repository.saveNote(pendingNote!!)
        }
    }
}