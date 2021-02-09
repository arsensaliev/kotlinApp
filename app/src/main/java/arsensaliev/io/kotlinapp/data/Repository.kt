package arsensaliev.io.kotlinapp.data

import arsensaliev.io.kotlinapp.data.model.note.Note
import arsensaliev.io.kotlinapp.data.provider.FireStoreProvider
import arsensaliev.io.kotlinapp.data.provider.RemoteDataProvider

object Repository {

    private val remoteDataProvider: RemoteDataProvider = FireStoreProvider()

    fun getNotes() = remoteDataProvider.subscribeToAllNotes()

    fun saveNote(note: Note) = remoteDataProvider.saveNote(note)

    fun getNoteById(id: String) = remoteDataProvider.getNoteById(id)

    fun getCurrentUser() = remoteDataProvider.getCurrentUser()
}