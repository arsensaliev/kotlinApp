package arsensaliev.io.kotlinapp.data.provider

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arsensaliev.io.kotlinapp.data.model.note.Note
import arsensaliev.io.kotlinapp.data.model.note.NoteResult
import arsensaliev.io.kotlinapp.data.model.user.NoAuthException
import arsensaliev.io.kotlinapp.data.model.user.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private const val NOTES_COLLECTION = "notes"
private const val USERS_COLLECTION = "users"

class FireStoreProvider : RemoteDataProvider {
    companion object {
        private val TAG = "${FireStoreProvider::class.java.simpleName} :"
    }

    private val db = FirebaseFirestore.getInstance()
    private val currentUser
        get() = FirebaseAuth.getInstance().currentUser

    override fun subscribeToAllNotes(): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            try {
                getUserNotesCollection().addSnapshotListener { snapshot, error ->
                    value = error?.let { NoteResult.Error(it) }
                        ?: snapshot?.let { res ->
                            val notes = res.documents.map { document ->
                                document.toObject(Note::class.java)
                            }
                            NoteResult.Success(notes)
                        }
                }
            } catch (e: Throwable) {
                value = NoteResult.Error(e)
            }
        }

    override fun getNoteById(id: String): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            try {
                getUserNotesCollection().document(id)
                    .get()
                    .addOnSuccessListener { res ->
                        value = NoteResult.Success(res.toObject(Note::class.java))
                    }
                    .addOnFailureListener { err ->
                        value = NoteResult.Error(err)
                    }
            } catch (e: Throwable) {
                value = NoteResult.Error(e)
            }
        }


    override fun saveNote(note: Note): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            try {
                getUserNotesCollection().document(note.id)
                    .set(note)
                    .addOnSuccessListener {
                        Log.d(TAG, "Note $note is saved")
                        value = NoteResult.Success(note)
                    }
                    .addOnFailureListener { err ->
                        Log.d(TAG, "Error saving $note message ${err.message}")
                        value = NoteResult.Error(err)
                    }
            } catch (e: Throwable) {
                value = NoteResult.Error(e)
            }
        }

    override fun getCurrentUser(): LiveData<User?> =
        MutableLiveData<User?>().apply {
            value = currentUser?.let {
                User(
                    it.displayName ?: "",
                    it.email ?: ""
                )
            }
        }


    private fun getUserNotesCollection() = currentUser?.let { firebaseUser ->
        db.collection(USERS_COLLECTION)
            .document(firebaseUser.uid)
            .collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()
}