package arsensaliev.io.kotlinapp.ui.main.state

import arsensaliev.io.kotlinapp.data.model.note.Note
import arsensaliev.io.kotlinapp.ui.base.BaseViewState

class MainViewState(notes: List<Note>? = null, error: Throwable? = null) :
    BaseViewState<List<Note>?>(notes, error)