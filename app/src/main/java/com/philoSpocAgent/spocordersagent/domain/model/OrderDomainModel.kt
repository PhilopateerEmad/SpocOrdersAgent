package com.philoSpocAgent.spocordersagent.domain.model


data class OrderDomainModel(
    val orderId: String,
    val pharmacyName: String,
    val orderDate: String,
)