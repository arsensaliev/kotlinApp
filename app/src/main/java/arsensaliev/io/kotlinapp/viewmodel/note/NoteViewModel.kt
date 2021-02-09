package arsensaliev.io.kotlinapp.viewmodel.note

import androidx.lifecycle.Observer
import arsensaliev.io.kotlinapp.data.Repository
import arsensaliev.io.kotlinapp.data.model.note.Note
import arsensaliev.io.kotlinapp.data.model.note.NoteResult
import arsensaliev.io.kotlinapp.ui.note.state.NoteViewState
import arsensaliev.io.kotlinapp.viewmodel.base.BaseViewModel

class NoteViewModel(private val repository: Repository = Repository) :
    BaseViewModel<Note?, NoteViewState>() {

    private var pendingNote: Note? = null

    fun saveChanges(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            repository.saveNote(it)
        }
    }

    fun loadNote(noteId: String) {
        repository.getNoteById(noteId).observeForever(object : Observer<NoteResult> {
            override fun onChanged(t: NoteResult?) {
                if (t == null) return

                when (t) {
                    is NoteResult.Success<*> ->
                        viewStateLiveData.value = NoteViewState(note = t.data as? Note)
                    is NoteResult.Error ->
                        viewStateLiveData.value = NoteViewState(error = t.error)
                }
            }
        })
    }

}