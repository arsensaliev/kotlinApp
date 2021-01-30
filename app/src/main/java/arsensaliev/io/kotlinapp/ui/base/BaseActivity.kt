package arsensaliev.io.kotlinapp.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import arsensaliev.io.kotlinapp.viewmodel.base.BaseViewModel
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<T, VS : BaseViewState<T>> : AppCompatActivity() {

    abstract val viewModel: BaseViewModel<T, VS>
    abstract val ui: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.root)

        viewModel.getViewState().observe(this, object : Observer<VS> {
            override fun onChanged(t: VS?) {
                if (t == null) return
                if (t.data != null) renderData(t.data)
                if (t.error != null) renderError(t.error)
            }
        })

    }

    protected fun renderError(error: Throwable) {
        if (error.message != null) showError(error.message!!)
    }

    abstract fun renderData(data: T)

    protected fun showError(error: String) {
        val snackbar = Snackbar.make(ui.root, error, Snackbar.LENGTH_INDEFINITE)
        snackbar.show()
    }

}