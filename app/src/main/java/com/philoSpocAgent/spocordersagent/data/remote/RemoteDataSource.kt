package com.philoSpocAgent.spocordersagent.data.remote
import com.philoSpocAgent.spocordersagent.data.remote.models.*


interface RemoteDataSource {
    suspend fun login(login: LoginRemoteModel): LoginResponseRemoteModel
    suspend fun getOrders(key:String,id:Int): List<OrderRemoteModel>
    suspend fun getOrderById(key:String,id:Int): OrderDetailsRemoteModel
    suspend fun getPharmaciesList(key:String,agentId:Int): PharmaciesListRemoteModel
    suspend fun getDistributorsList(key:String,agentId:Int): DistributorsListRemoteModel
    suspend fun getProductsList(key:String): List<ProductSimpleRemoteModel>
    suspend fun addNewOrder(key:String,newOrder: AddOrderRemoteModel)
}