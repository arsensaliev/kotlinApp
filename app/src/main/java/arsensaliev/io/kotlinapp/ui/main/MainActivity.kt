package arsensaliev.io.kotlinapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import arsensaliev.io.kotlinapp.data.model.Note
import arsensaliev.io.kotlinapp.databinding.ActivityMainBinding
import arsensaliev.io.kotlinapp.ui.main.interfaces.OnItemClickListener
import arsensaliev.io.kotlinapp.ui.main.recyclerView.MainAdapter
import arsensaliev.io.kotlinapp.ui.note.NoteActivity
import arsensaliev.io.kotlinapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var adapter: MainAdapter
    lateinit var ui: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)

        setSupportActionBar(ui.toolbar)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        adapter = MainAdapter(object : OnItemClickListener {
            override fun onItemClick(note: Note) {
                openNoteScreen(note)
            }
        })

        ui.recyclerView.adapter = adapter

        viewModel.viewState().observe(this, {
            it?.let { adapter.notes = it.notes }
        })

        ui.fab.setOnClickListener {
            openNoteScreen()
        }
    }

    fun openNoteScreen(note: Note? = null) {
        startActivity(NoteActivity.getStartIntent(this, note))
    }
}