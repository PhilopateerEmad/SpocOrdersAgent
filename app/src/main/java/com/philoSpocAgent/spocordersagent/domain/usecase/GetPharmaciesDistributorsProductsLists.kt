package com.philoSpocAgent.spocordersagent.domain.usecase

import com.philoSpocAgent.spocordersagent.domain.model.PharmaDistributorsProductsListsDomainModel
import com.philoSpocAgent.spocordersagent.domain.repository.OrdersRepository
import javax.inject.Inject

class GetPharmaciesDistributorsProductsLists @Inject constructor(private val ordersRepository: OrdersRepository) {

    suspend fun execute(key: String,agentId: Int): PharmaDistributorsProductsListsDomainModel {

        return ordersRepository.getPharmaDistributorsProductsListsFromServer(key,agentId)
    }
}