package arsensaliev.io.kotlinapp.ui.splash

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import arsensaliev.io.kotlinapp.databinding.ActivitySplashBinding
import arsensaliev.io.kotlinapp.ui.base.BaseActivity
import arsensaliev.io.kotlinapp.ui.main.MainActivity
import arsensaliev.io.kotlinapp.ui.splash.state.SplashViewState
import arsensaliev.io.kotlinapp.viewmodel.splash.SplashViewModel

private const val START_DELAY = 1000L

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {

    override val viewModel: SplashViewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    override val ui: ActivitySplashBinding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({ viewModel.requestUser() }, START_DELAY)
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }
}