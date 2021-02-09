package arsensaliev.io.kotlinapp.ui.splash.state

import arsensaliev.io.kotlinapp.ui.base.BaseViewState

class SplashViewState(isLoggedIn: Boolean? = null, error: Throwable? = null) :
    BaseViewState<Boolean?>(isLoggedIn, error)