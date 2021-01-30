package arsensaliev.io.kotlinapp.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import arsensaliev.io.kotlinapp.data.model.Note
import arsensaliev.io.kotlinapp.databinding.ActivityMainBinding
import arsensaliev.io.kotlinapp.ui.base.BaseActivity
import arsensaliev.io.kotlinapp.ui.main.interfaces.OnItemClickListener
import arsensaliev.io.kotlinapp.ui.main.recyclerView.MainAdapter
import arsensaliev.io.kotlinapp.ui.main.state.MainViewState
import arsensaliev.io.kotlinapp.ui.note.NoteActivity
import arsensaliev.io.kotlinapp.viewmodel.main.MainViewModel

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    override val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    lateinit var adapter: MainAdapter

    override lateinit var ui: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)

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

}