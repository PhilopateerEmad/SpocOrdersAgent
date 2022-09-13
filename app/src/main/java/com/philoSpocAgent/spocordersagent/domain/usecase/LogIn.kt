package com.philoSpocAgent.spocordersagent.domain.usecase

import com.philoSpocAgent.spocordersagent.domain.model.LoginDomainModel
import com.philoSpocAgent.spocordersagent.domain.model.LoginResponseDomainModel
import com.philoSpocAgent.spocordersagent.domain.repository.OrdersRepository
import javax.inject.Inject

class LogIn @Inject constructor(private val ordersRepository: OrdersRepository) {

    suspend fun execute(login: LoginDomainModel): LoginResponseDomainModel {

        return ordersRepository.login(login)
    }
}