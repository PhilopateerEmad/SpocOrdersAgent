package com.philoSpocAgent.spocordersagent.presentation.adapter

import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.philoSpocAgent.spocordersagent.databinding.OrdersCardViewBinding

import com.philoSpocAgent.spocordersagent.domain.model.OrderDomainModel


import com.philoSpocAgent.spocordersagent.util.OrderDiffUtil


class OrdersListAdapter(private val itemClickListener: ItemClickListener) : ListAdapter<OrderDomainModel, OrdersListAdapter.ViewHolder>(
    OrderDiffUtil()
) {

    class ViewHolder(val binding: OrdersCardViewBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OrdersCardViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            pharmacyName.text = getItem(holder.adapterPosition).pharmacyName
            orderDate.text =getItem(holder.adapterPosition).orderDate

            card.setOnClickListener {
                getItem(holder.adapterPosition).orderId.let {
                    itemClickListener.orderItem(
                        getItem(holder.adapterPosition).orderId
                    )
                }
            }
        }
    }



    interface ItemClickListener{
        fun orderItem(orderId:String)

    }
}