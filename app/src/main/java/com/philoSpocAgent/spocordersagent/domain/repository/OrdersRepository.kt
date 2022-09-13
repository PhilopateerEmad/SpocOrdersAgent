package com.philoSpocAgent.spocordersagent.domain.repository

import com.philoSpocAgent.spocordersagent.domain.model.*


interface OrdersRepository {


    suspend fun login(login: LoginDomainModel): LoginResponseDomainModel
    suspend fun getOrdersListFromServer(key:String,agentId:Int):List<OrderDomainModel>
    suspend fun getOrderDetailsFromServer(key:String,orderId:Int): OrderDetailsDomainModel
    suspend fun getPharmaciesListFromServer(key:String,agentId:Int): List<PharmacyDetailsDomainModel>
    suspend fun getDistributorsListFromServer(key:String,agentId:Int): List<DistributorDetailsDomainModel>
    suspend fun getPharmaDistributorsProductsListsFromServer(key:String,agentId:Int): PharmaDistributorsProductsListsDomainModel
    suspend fun addNewOrder(key:String,newOrder: AddOrderDomainModel)



}