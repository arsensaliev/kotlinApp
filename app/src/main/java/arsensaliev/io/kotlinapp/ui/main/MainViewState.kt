package arsensaliev.io.kotlinapp.ui.main

import arsensaliev.io.kotlinapp.data.model.Note

// Зачем нужны классы State ?????
data class MainViewState(val notes: List<Note>)