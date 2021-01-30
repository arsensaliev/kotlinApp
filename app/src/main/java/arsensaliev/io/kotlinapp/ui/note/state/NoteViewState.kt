package arsensaliev.io.kotlinapp.ui.note.state

import arsensaliev.io.kotlinapp.data.model.Note
import arsensaliev.io.kotlinapp.ui.base.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null) :
    BaseViewState<Note?>(note, error)