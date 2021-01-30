package arsensaliev.io.kotlinapp.data.provider

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arsensaliev.io.kotlinapp.data.model.Note
import arsensaliev.io.kotlinapp.data.model.NoteResult
import arsensaliev.io.kotlinapp.data.provider.RemoteDataProvider
import com.google.firebase.firestore.*

private const val NOTES_COLLECTION = "notes"

class FireStoreProvider : RemoteDataProvider {
    companion object {
        private val TAG = "${FireStoreProvider::class.java.simpleName} :"
    }

    private val db = FirebaseFirestore.getInstance()
    private val notesReference = db.collection(NOTES_COLLECTION)

    override fun subscribeToAllNotes(): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        notesReference.addSnapshotListener { value, error ->
            if (error != null) {
                result.value = NoteResult.Error(error)
            } else if (value != null) {
                val notes = mutableListOf<Note>()

                for (doc: QueryDocumentSnapshot in value) {
                    notes.add(doc.toObject(Note::class.java))
                }

                result.value = NoteResult.Success(notes)
            }
        }

        return result
    }

    override fun getNoteById(id: String): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        notesReference.document(id)
            .get()
            .addOnSuccessListener { res ->
                result.value = NoteResult.Success(res.toObject(Note::class.java))
            }
            .addOnFailureListener { err ->
                result.value = NoteResult.Error(err)
            }

        return result
    }

    override fun saveNote(note: Note): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        notesReference.document(note.id)
            .set(note)
            .addOnSuccessListener {
                Log.d(TAG, "Note $note is saved")
                result.value = NoteResult.Success(note)
            }
            .addOnFailureListener { err ->
                Log.d(TAG, "Error saving $note message ${err.message}")
                result.value = NoteResult.Error(err)
            }

        return result
    }
}