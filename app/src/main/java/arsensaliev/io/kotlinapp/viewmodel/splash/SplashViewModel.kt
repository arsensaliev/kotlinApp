package arsensaliev.io.kotlinapp.viewmodel.splash

import arsensaliev.io.kotlinapp.data.Repository
import arsensaliev.io.kotlinapp.data.model.user.NoAuthException
import arsensaliev.io.kotlinapp.ui.splash.state.SplashViewState
import arsensaliev.io.kotlinapp.viewmodel.base.BaseViewModel

class SplashViewModel(private val repository: Repository = Repository) :
    BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        repository.getCurrentUser().observeForever {
            viewStateLiveData.value = it?.let {
                SplashViewState(isLoggedIn = true)
            } ?: SplashViewState(error = NoAuthException())
        }
    }
}

