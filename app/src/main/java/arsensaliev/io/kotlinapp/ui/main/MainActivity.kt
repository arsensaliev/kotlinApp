package arsensaliev.io.kotlinapp.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import arsensaliev.io.kotlinapp.data.model.note.Note
import arsensaliev.io.kotlinapp.databinding.ActivityMainBinding
import arsensaliev.io.kotlinapp.ui.base.BaseActivity
import arsensaliev.io.kotlinapp.ui.main.interfaces.OnItemClickListener
import arsensaliev.io.kotlinapp.ui.main.recyclerView.MainAdapter
import arsensaliev.io.kotlinapp.ui.main.state.MainViewState
import arsensaliev.io.kotlinapp.ui.note.NoteActivity
import arsensaliev.io.kotlinapp.viewmodel.main.MainViewModel

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override val ui: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(ui.toolbar)

        adapter = MainAdapter(object : OnItemClickListener {
            override fun onItemClick(note: Note) {
                openNoteScreen(note)
            }
        })

        ui.recyclerView.adapter = adapter

        ui.fab.setOnClickListener { openNoteScreen(null) }
    }

    fun openNoteScreen(note: Note?) {
        val intent = NoteActivity.getStartIntent(this, note?.id)
        startActivity(intent)
    }

    override fun renderData(data: List<Note>?) {
        if (data == null) return
        adapter.notes = data
    }


    companion object {
        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}