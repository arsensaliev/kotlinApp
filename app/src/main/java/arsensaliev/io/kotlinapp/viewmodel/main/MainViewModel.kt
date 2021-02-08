package arsensaliev.io.kotlinapp.viewmodel.main

import androidx.lifecycle.Observer
import arsensaliev.io.kotlinapp.data.Repository
import arsensaliev.io.kotlinapp.data.model.note.Note
import arsensaliev.io.kotlinapp.data.model.note.NoteResult
import arsensaliev.io.kotlinapp.ui.main.state.MainViewState
import arsensaliev.io.kotlinapp.viewmodel.base.BaseViewModel

class MainViewModel(repository: Repository = Repository) :
    BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = object : Observer<NoteResult> {
        override fun onChanged(t: NoteResult?) {
            if (t == null) return

            when (t) {
                is NoteResult.Success<*> -> {
                    viewStateLiveData.value = MainViewState(notes = t.data as? List<Note>)
                }
                is NoteResult.Error -> {
                    viewStateLiveData.value = MainViewState(error = t.error)
                }
            }
        }
    }

    private val repositoryNotes = repository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)
    }

    override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
    }
}