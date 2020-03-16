package br.com.mybank.ui.login

import android.os.Bundle
import br.com.mybank.BaseActivity
import br.com.mybank.R
import br.com.mybank.data.SessionUtil
import br.com.mybank.data.StateResponse
import br.com.mybank.data.StateSuccess
import br.com.mybank.domain.LoginResponseBO
import br.com.mybank.ui.dashboard.ActivityCurrency
import br.com.mybank.util.nonNullObserve
import br.com.mybank.util.validateCPF
import br.com.mybank.util.validateEmail
import br.com.mybank.util.validatePassword
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.koin.android.ext.android.inject

class ActivityLogin : BaseActivity(
    R.layout.activity_login,
    R.color.white
) {
    private val viewModel by inject<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        setupObserver()
    }

    override fun onResume() {
        super.onResume()
        hasCredentials()
    }

    private fun setupObserver() {
        viewModel.loginState.nonNullObserve(this) { state ->
            processLoginState(state)
        }
    }

    private fun processLoginState(state: StateResponse<LoginResponseBO>) {
        when (state) {
            is StateSuccess -> setupClient(state.data)
        }
    }

    private fun setupClient(data: LoginResponseBO) {
        data.userAccount?.let {
            SessionUtil.client = it
            startActivity<ActivityCurrency>()
        }
    }

    private fun hasCredentials() {
        if (viewModel.userHasLoggedIn()) {
            viewModel.getUserData().let { userData ->
                requestLogin(userData.user, userData.password)
            }
        }
    }

    private fun initViews() {
        setupButton()
    }

    private fun requestLogin(user: String?, password: String?) {
        viewModel.doLogin(
            user = user,
            password = password
        )
        viewModel.setUserData(user, password)
        viewModel.setLoggedUser()
    }

    private fun setupButton() {
        btDoLogin.setOnClickListener {
            if (isValidUserEntry())
                requestLogin(etUserLogin.text.toString(), etUserPassword.text.toString())
        }
    }

    private fun isValidUserEntry(): Boolean {
        return (etUserLogin.validateCPF() or etUserLogin.validateEmail()) and etUserPassword.validatePassword()
    }
}
