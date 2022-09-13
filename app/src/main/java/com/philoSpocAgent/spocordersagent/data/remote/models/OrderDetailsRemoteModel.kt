package com.philoSpocAgent.spocordersagent.data.remote.models

data class OrderDetailsRemoteModel(
    val id: Int?,
    val pharmacyName: String?,
    val distributorName: String?,
    val agentName: String?,
    val orderDate: String?,
    val branchName: String?,
    val pharmacyCode: String?,
    val expiryGoods: Boolean?,
    val valueOfExpiredGoods: String?,
    val products: List<ProductDetailsRemoteModel>?,

    )
