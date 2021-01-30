package arsensaliev.io.kotlinapp.ui.main.state

import arsensaliev.io.kotlinapp.data.model.Note
import arsensaliev.io.kotlinapp.ui.base.BaseViewState

// Зачем нужны классы State ?????
class MainViewState(notes: List<Note>? = null, error: Throwable? = null) :
    BaseViewState<List<Note>?>(notes, error)