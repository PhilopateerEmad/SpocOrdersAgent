package com.philoSpocAgent.spocordersagent.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.philoSpocAgent.spocordersagent.databinding.ProductsSimpleCardViewBinding
import com.philoSpocAgent.spocordersagent.domain.model.ProductNameQuantityDomainModel
import com.philoSpocAgent.spocordersagent.util.SimpleProductDiffUtil


class ProductsSimpleListAdapter: ListAdapter<ProductNameQuantityDomainModel, ProductsSimpleListAdapter.ViewHolder>(
    SimpleProductDiffUtil()
) {

    class ViewHolder(val binding: ProductsSimpleCardViewBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ProductsSimpleCardViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            productName.text = getItem(holder.adapterPosition).productName
            println(getItem(holder.adapterPosition).productName)
            quantity.text = "x"+getItem(holder.adapterPosition).quantity
        }
    }

}