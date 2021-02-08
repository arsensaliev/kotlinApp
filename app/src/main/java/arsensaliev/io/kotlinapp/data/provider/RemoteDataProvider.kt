package arsensaliev.io.kotlinapp.data.provider

import androidx.lifecycle.LiveData
import arsensaliev.io.kotlinapp.data.model.note.Note
import arsensaliev.io.kotlinapp.data.model.note.NoteResult
import arsensaliev.io.kotlinapp.data.model.user.User

interface RemoteDataProvider {

    fun subscribeToAllNotes(): LiveData<NoteResult>

    fun getNoteById(note: String): LiveData<NoteResult>

    fun saveNote(note: Note): LiveData<NoteResult>

    fun getCurrentUser(): LiveData<User?>

}