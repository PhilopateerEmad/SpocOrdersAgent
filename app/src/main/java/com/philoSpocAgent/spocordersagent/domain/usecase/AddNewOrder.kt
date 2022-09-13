package com.philoSpocAgent.spocordersagent.domain.usecase


import com.philoSpocAgent.spocordersagent.domain.model.AddOrderDomainModel
import com.philoSpocAgent.spocordersagent.domain.repository.OrdersRepository
import javax.inject.Inject

class AddNewOrder @Inject constructor(private val ordersRepository: OrdersRepository) {

    suspend fun execute(key:String,newOrder: AddOrderDomainModel){
        ordersRepository.addNewOrder(key, newOrder)
    }
}