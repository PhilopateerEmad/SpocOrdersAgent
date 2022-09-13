package com.philoSpocAgent.spocordersagent.domain.usecase

import com.philoSpocAgent.spocordersagent.domain.model.OrderDomainModel
import com.philoSpocAgent.spocordersagent.domain.repository.OrdersRepository
import javax.inject.Inject

class GetOrdersList @Inject constructor(private val ordersRepository: OrdersRepository) {

    suspend fun execute(key: String,agentId: Int): List<OrderDomainModel> {

        return ordersRepository.getOrdersListFromServer(key,agentId)
    }
}