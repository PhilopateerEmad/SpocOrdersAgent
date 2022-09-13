package com.philoSpocAgent.spocordersagent.util

import androidx.recyclerview.widget.DiffUtil

import com.philoSpocAgent.spocordersagent.domain.model.OrderDomainModel


class OrderDiffUtil: DiffUtil.ItemCallback<OrderDomainModel>()
{
    override fun areItemsTheSame(oldItem: OrderDomainModel, newItem: OrderDomainModel): Boolean {
        return oldItem.orderId ==newItem.orderId
    }

    override fun areContentsTheSame(oldItem: OrderDomainModel, newItem: OrderDomainModel): Boolean {
        return oldItem == newItem
    }
}