package arsensaliev.io.kotlinapp.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import arsensaliev.io.kotlinapp.R
import arsensaliev.io.kotlinapp.data.model.note.Note
import arsensaliev.io.kotlinapp.databinding.ActivityNoteBinding
import arsensaliev.io.kotlinapp.ui.base.BaseActivity
import arsensaliev.io.kotlinapp.ui.format
import arsensaliev.io.kotlinapp.ui.getColorInt
import arsensaliev.io.kotlinapp.ui.note.state.NoteViewState
import arsensaliev.io.kotlinapp.viewmodel.note.NoteViewModel
import java.util.*

const val SAVE_DELAY = 2000L

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
        ViewModelProvider(this).get(NoteViewModel::class.java)
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
        setSupportActionBar(ui.toolbar)

        val noteId = intent.getStringExtra(EXTRA_NOTE)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        noteId?.let {
            viewModel.loadNote(it)
        } ?: run {
            supportActionBar?.title = getString(R.string.new_note_title)
        }

        initView()
    }

    private fun initView() {
        note?.run {
            ui.toolbar.setBackgroundColor(color.getColorInt(this@NoteActivity))
            ui.titleEt.setText(title)
            ui.bodyEt.setText(note)

            supportActionBar?.title = lastChanged.format()
        }


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