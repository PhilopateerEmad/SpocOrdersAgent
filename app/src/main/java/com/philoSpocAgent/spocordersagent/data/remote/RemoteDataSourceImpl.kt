package com.philoSpocAgent.spocordersagent.data.remote

import com.philoSpocAgent.spocordersagent.data.remote.models.*
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val spocApi: SpocApi): RemoteDataSource {
    override suspend fun login(login: LoginRemoteModel): LoginResponseRemoteModel {
        val response = spocApi.login(login)

        return if(response.isSuccessful) {
            response.body() ?: LoginResponseRemoteModel(false,"no message",0)

        }
        else{
            println("The error is ${response.errorBody().toString()}")
            LoginResponseRemoteModel(false,"no message",0)
        }
    }

    override suspend fun getOrders(key:String,id: Int): List<OrderRemoteModel> {
        val response = spocApi.getOrders(key,id)
        return if(response.isSuccessful) {
            response.body() ?: OrdersListRemoteModel()

        }
        else{
            println("The error is ${response.errorBody().toString()}")
            OrdersListRemoteModel()
        }
    }

    override suspend fun getOrderById(key: String, id: Int): OrderDetailsRemoteModel {
        val response = spocApi.getOrderDetailsById(key,id)
        println(response.body()?.products)
        return if(response.isSuccessful) {

            response.body() ?: OrderDetailsRemoteModel(

                0,
                "",
                "",
                "",
                "",
                "",
                "",
                false,
                "",
                listOf(ProductDetailsRemoteModel("",0,"")),
            )

        }
        else{
            println("The error is ${response.errorBody().toString()}")
            OrderDetailsRemoteModel(
                0,
                "",
                "",
                "",
                "",
                "",
                "",
                false,
                "",
                listOf(ProductDetailsRemoteModel("",0,"")),
            )
        }
    }

    override suspend fun getPharmaciesList(key: String, agentId: Int): PharmaciesListRemoteModel {
        val response = spocApi.getPharmaciesList(key,agentId)

        return if(response.isSuccessful) {
            response.body() ?: PharmaciesListRemoteModel()

        }
        else{
            println("The error is ${response.errorBody().toString()}")
            PharmaciesListRemoteModel()
        }
    }

    override suspend fun getDistributorsList(key: String, agentId: Int): DistributorsListRemoteModel {
        val response = spocApi.getDistributorsList(key,agentId)

        return if(response.isSuccessful) {
            response.body() ?: DistributorsListRemoteModel()

        }
        else{
            println("The error is ${response.errorBody().toString()}")
            DistributorsListRemoteModel()
        }
    }

    override suspend fun getProductsList(key: String): List<ProductSimpleRemoteModel> {
        val response = spocApi.getProductsList(key)

        return if(response.isSuccessful) {
            response.body() ?: listOf()

        }
        else{
            println("The error is ${response.errorBody().toString()}")
            listOf()
        }
    }

    override suspend fun addNewOrder(key: String, newOrder: AddOrderRemoteModel) {
        spocApi.addNewOrder(key,newOrder)

    }
}