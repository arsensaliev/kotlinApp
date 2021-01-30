package arsensaliev.io.kotlinapp.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import arsensaliev.io.kotlinapp.R
import arsensaliev.io.kotlinapp.data.model.Color
import arsensaliev.io.kotlinapp.data.model.Note
import arsensaliev.io.kotlinapp.databinding.ActivityNoteBinding
import arsensaliev.io.kotlinapp.ui.DATE_TIME_FORMAT
import arsensaliev.io.kotlinapp.ui.SAVE_DELAY
import arsensaliev.io.kotlinapp.ui.base.BaseActivity
import arsensaliev.io.kotlinapp.ui.note.state.NoteViewState
import arsensaliev.io.kotlinapp.viewmodel.note.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : BaseActivity<Note?, NoteViewState>() {
    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"

        fun getStartIntent(context: Context, noteId: String?): Intent {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, noteId)
            return intent
        }
    }

    override val viewModel: NoteViewModel by lazy {
        ViewModelProviders.of(this).get(NoteViewModel::class.java)
    }

    override val ui: ActivityNoteBinding by lazy { ActivityNoteBinding.inflate(layoutInflater) }

    private var note: Note? = null

    private val textChangeListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            triggerSaveNote()
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val noteId = intent.getStringExtra(EXTRA_NOTE)

        noteId?.let {
            viewModel.loadNote(it)
        }

        setSupportActionBar(ui.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = if (note != null) {
            SimpleDateFormat(
                DATE_TIME_FORMAT,
                Locale.getDefault()
            ).format(note!!.lastChanged)
        } else {
            getString(R.string.new_note_title)
        }

        initView()
    }

    private fun initView() {
        ui.titleEt.setText(note?.title ?: "")
        ui.bodyEt.setText(note?.note ?: "")

        // Тут проблема DRY - повторение кода, хочу совет как лучше сделать.
        val color = when (note?.color) {
            Color.WHITE -> R.color.color_white
            Color.VIOLET -> R.color.color_violet
            Color.YELLOW -> R.color.color_yello
            Color.RED -> R.color.color_red
            Color.PINK -> R.color.color_pink
            Color.GREEN -> R.color.color_green
            Color.BLUE -> R.color.color_blue
            else -> R.color.color_violet
        }

        // getColor устарел
        ui.toolbar.setBackgroundColor(resources.getColor(color))

        ui.titleEt.addTextChangedListener(textChangeListener)
        ui.bodyEt.addTextChangedListener(textChangeListener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun createNewNote(): Note =
        Note(
            id = UUID.randomUUID().toString(),
            title = ui.titleEt.text.toString(),
            note = ui.bodyEt.text.toString()
        )

    private fun triggerSaveNote() {
        if (ui.titleEt.text == null || ui.titleEt.text!!.length < 3) return

        // Handler тоже устарел, хотел бы хороший пример
        Handler(Looper.getMainLooper()).postDelayed({
            note = note?.copy(
                title = ui.titleEt.text.toString(),
                note = ui.bodyEt.text.toString(),
                lastChanged = Date()
            ) ?: createNewNote()

            note?.let { note ->
                viewModel.saveChanges(note)
            }
        }, SAVE_DELAY)


    }

    override fun renderData(data: Note?) {
        this.note = data
        initView()
    }

}