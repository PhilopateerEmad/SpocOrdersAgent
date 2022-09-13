package com.philoSpocAgent.spocordersagent.util

import androidx.recyclerview.widget.DiffUtil

import com.philoSpocAgent.spocordersagent.domain.model.ProductNameQuantityDomainModel


class SimpleProductDiffUtil: DiffUtil.ItemCallback<ProductNameQuantityDomainModel>()
{
    override fun areItemsTheSame(oldItem: ProductNameQuantityDomainModel, newItem: ProductNameQuantityDomainModel): Boolean {
        return oldItem.productId ==newItem.productId
    }

    override fun areContentsTheSame(oldItem: ProductNameQuantityDomainModel, newItem: ProductNameQuantityDomainModel): Boolean {
        return oldItem == newItem
    }
}