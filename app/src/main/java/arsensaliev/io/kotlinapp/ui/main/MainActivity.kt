package arsensaliev.io.kotlinapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import arsensaliev.io.kotlinapp.R
import arsensaliev.io.kotlinapp.databinding.ActivityMainBinding

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
        adapter = MainAdapter()
        ui.recyclerView.adapter = adapter
        viewModel.viewState().observe(this, Observer<MainViewState> { t ->
            t?.let {
                adapter.notes = it.notes
            }
        })

    }

    fun createUser(const: String) {

    }
}