package arsensaliev.io.kotlinapp.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import arsensaliev.io.kotlinapp.R
import arsensaliev.io.kotlinapp.data.model.note.Note
import arsensaliev.io.kotlinapp.databinding.ActivityMainBinding
import arsensaliev.io.kotlinapp.ui.base.BaseActivity
import arsensaliev.io.kotlinapp.ui.common.LogoutDialog
import arsensaliev.io.kotlinapp.ui.main.interfaces.OnItemClickListener
import arsensaliev.io.kotlinapp.ui.main.recyclerView.MainAdapter
import arsensaliev.io.kotlinapp.ui.main.state.MainViewState
import arsensaliev.io.kotlinapp.ui.note.NoteActivity
import arsensaliev.io.kotlinapp.ui.splash.SplashActivity
import arsensaliev.io.kotlinapp.viewmodel.main.MainViewModel
import com.firebase.ui.auth.AuthUI

class MainActivity : BaseActivity<List<Note>?, MainViewState>(), LogoutDialog.LogoutListener {

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
        MenuInflater(this).inflate(R.menu.menu_main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.logout -> showLogoutDialog().let { true }
            else -> false
        }

    private fun showLogoutDialog() {
        supportFragmentManager.findFragmentByTag(LogoutDialog.TAG) ?: LogoutDialog.createInstance()
            .show(supportFragmentManager, LogoutDialog.TAG)
    }


    companion object {
        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    override fun onLogout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                startActivity(Intent(this, SplashActivity::class.java))
                finish()
            }
    }
}