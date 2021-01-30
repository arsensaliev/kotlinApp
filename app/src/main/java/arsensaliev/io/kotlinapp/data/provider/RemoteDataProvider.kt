package arsensaliev.io.kotlinapp.data.provider

import androidx.lifecycle.LiveData
import arsensaliev.io.kotlinapp.data.model.Note
import arsensaliev.io.kotlinapp.data.model.NoteResult

interface RemoteDataProvider {

    fun subscribeToAllNotes(): LiveData<NoteResult>

    fun getNoteById(note: String): LiveData<NoteResult>

    fun saveNote(note: Note): LiveData<NoteResult>

}