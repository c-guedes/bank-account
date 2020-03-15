package br.com.mybank.ui.login

import android.os.Bundle
import br.com.mybank.BaseActivity
import br.com.mybank.R
import br.com.mybank.data.SessionUtil
import br.com.mybank.data.StateResponse
import br.com.mybank.data.StateSuccess
import br.com.mybank.domain.LoginresponseBO
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

    private fun setupObserver() {
        viewModel.loginState.nonNullObserve(this) { state ->
            processLoginState(state)
        }
    }

    private fun processLoginState(state: StateResponse<LoginresponseBO>) {
        when (state) {
            is StateSuccess -> setupClient(state.data)
        }
    }

    private fun setupClient(data: LoginresponseBO) {
        data.userAccount?.let {
            SessionUtil.client = it
            startActivity<ActivityCurrency>()
        }
    }

    private fun initViews() {
        validateUserEntry()
        setupButton()
    }

    private fun setupButton() {
        btDoLogin.setOnClickListener {
            validateUserEntry()
            viewModel.doLogin(
                user = etUserLogin.text.toString(),
                password = etUserPassword.text.toString()
            )
        }
    }

    private fun validateUserEntry(): Boolean {
        return (etUserLogin.validateCPF() or etUserLogin.validateEmail()) and etUserPassword.validatePassword()
    }
}
