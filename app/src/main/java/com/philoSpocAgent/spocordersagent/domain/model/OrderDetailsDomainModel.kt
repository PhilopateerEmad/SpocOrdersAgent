package com.philoSpocAgent.spocordersagent.domain.model


data class OrderDetailsDomainModel(
    val agentName: String,
    val branchName: String,
    val distributorName: String,
    val expiryGoods: Boolean,
    val orderId: String,
    val orderDate: String,
    val pharmacyCode: String,
    val pharmacyName: String,
    val productDetailsDomainModels: List<ProductDetailsDomainModel>,
    val valueOfExpiredGoods: String,
)
