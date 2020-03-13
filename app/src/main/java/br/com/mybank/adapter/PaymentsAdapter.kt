package br.com.mybank.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.mybank.R
import br.com.mybank.domain.PaymentsResponseBO
import br.com.mybank.util.formatValueToMonetary
import kotlinx.android.synthetic.main.recycler_payments.view.*

class PaymentsAdapter(
    private val payments: PaymentsResponseBO,
    private val context: Context
) : RecyclerView.Adapter<PaymentsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_payments, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return payments.statementList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        payments.statementList[position]?.let { item ->
            holder.run {
                headingInfo.text = item.title
                paymentsType.text = item.desc
                paymentsValue.text = item.value?.formatValueToMonetary()
                dateHeading.text = item.date
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headingInfo = itemView.tvHeadingInfo
        val paymentsType = itemView.tvPaymentsType
        val paymentsValue = itemView.tvPaymentsValue
        val dateHeading = itemView.tvDateHeading
    }
}