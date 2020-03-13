package br.com.mybank.ui.dashboard

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.mybank.BaseActivity
import br.com.mybank.R
import br.com.mybank.adapter.PaymentsAdapter
import br.com.mybank.data.SessionUtil
import br.com.mybank.data.StateResponse
import br.com.mybank.data.StateSuccess
import br.com.mybank.domain.PaymentsResponseBO
import br.com.mybank.util.formatValueToMonetary
import br.com.mybank.util.nonNullObserve
import kotlinx.android.synthetic.main.activity_currency.*
import org.koin.android.ext.android.inject

class ActivityCurrency : BaseActivity(
    layoutId = R.layout.activity_currency,
    statusBarColor = R.color.blue
) {
    private val viewModel by inject<CurrencyViewModel>()
    private lateinit var paymentsAdapter: PaymentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        setupObservable()
    }

    private fun setupObservable() {
        viewModel.paymentsResponse.nonNullObserve(this) { state ->
            processRequest(state)
        }
    }

    private fun processRequest(state: StateResponse<PaymentsResponseBO>) {
        when (state) {
            is StateSuccess -> setupAdapter(state.data)
        }
    }

    private fun setupAdapter(data: PaymentsResponseBO) {
        paymentsAdapter = PaymentsAdapter(
            data,
            this
        )

        rcPayments.run {
            layoutManager = LinearLayoutManager(this@ActivityCurrency)
            adapter = paymentsAdapter
        }
    }

    private fun initViews() {
        setupClientHeader()
        viewModel.getPaymentsList()
    }

    private fun setupClientHeader() {
        tvUserName.text = SessionUtil.client.name
        tvAccountNumber.text = SessionUtil.client.bankAccount
        tvCurrentBalance.text = SessionUtil.client.balance.formatValueToMonetary()
    }
}
