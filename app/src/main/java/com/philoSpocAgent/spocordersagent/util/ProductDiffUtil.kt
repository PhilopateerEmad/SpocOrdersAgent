package com.philoSpocAgent.spocordersagent.util

import androidx.recyclerview.widget.DiffUtil

import com.philoSpocAgent.spocordersagent.domain.model.ProductDetailsDomainModel


class ProductDiffUtil: DiffUtil.ItemCallback<ProductDetailsDomainModel>()
{
    override fun areItemsTheSame(oldItem: ProductDetailsDomainModel, newItem: ProductDetailsDomainModel): Boolean {
        return oldItem.name ==newItem.name
    }

    override fun areContentsTheSame(oldItem: ProductDetailsDomainModel, newItem: ProductDetailsDomainModel): Boolean {
        return oldItem == newItem
    }
}