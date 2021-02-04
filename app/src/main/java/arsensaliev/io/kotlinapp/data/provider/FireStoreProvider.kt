package arsensaliev.io.kotlinapp.data.provider

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arsensaliev.io.kotlinapp.data.model.Note
import arsensaliev.io.kotlinapp.data.model.NoteResult
import com.google.firebase.firestore.FirebaseFirestore

private const val NOTES_COLLECTION = "notes"

class FireStoreProvider : RemoteDataProvider {
    companion object {
        private val TAG = "${FireStoreProvider::class.java.simpleName} :"
    }

    private val db = FirebaseFirestore.getInstance()
    private val notesReference = db.collection(NOTES_COLLECTION)

    override fun subscribeToAllNotes(): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            notesReference.addSnapshotListener { snapshot, error ->
                value = error?.let { NoteResult.Error(it) }
                    ?: snapshot?.let { res ->
                        val notes = res.documents.map { document ->
                            document.toObject(Note::class.java)
                        }
                        NoteResult.Success(notes)
                    }
            }


        }

    override fun getNoteById(id: String): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            notesReference.document(id)
                .get()
                .addOnSuccessListener { res ->
                    value = NoteResult.Success(res.toObject(Note::class.java))
                }
                .addOnFailureListener { err ->
                    value = NoteResult.Error(err)
                }
        }


    override fun saveNote(note: Note): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            notesReference.document(note.id)
                .set(note)
                .addOnSuccessListener {
                    Log.d(TAG, "Note $note is saved")
                    value = NoteResult.Success(note)
                }
                .addOnFailureListener { err ->
                    Log.d(TAG, "Error saving $note message ${err.message}")
                    value = NoteResult.Error(err)
                }
        }
}