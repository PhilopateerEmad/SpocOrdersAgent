package com.philoSpocAgent.spocordersagent.domain.usecase

import com.philoSpocAgent.spocordersagent.domain.model.OrderDetailsDomainModel
import com.philoSpocAgent.spocordersagent.domain.repository.OrdersRepository
import javax.inject.Inject

class GetOrderDetails @Inject constructor(private val ordersRepository: OrdersRepository) {

    suspend fun execute(key: String,orderId: Int): OrderDetailsDomainModel {

        return ordersRepository.getOrderDetailsFromServer(key,orderId)
    }
}