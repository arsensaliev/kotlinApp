package arsensaliev.io.kotlinapp.data.model.note

sealed class NoteResult {

    data class Success<out T>(val data: T) : NoteResult()

    data class Error(val error: Throwable) : NoteResult()

}