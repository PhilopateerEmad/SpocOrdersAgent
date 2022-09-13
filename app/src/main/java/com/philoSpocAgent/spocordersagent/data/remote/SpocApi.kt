package com.philoSpocAgent.spocordersagent.data.remote

import com.philoSpocAgent.spocordersagent.data.remote.models.*
import retrofit2.Response
import retrofit2.http.*

interface SpocApi {

    @POST("api/Admin/LoginAgent")
    suspend fun login(@Body login: LoginRemoteModel): Response<LoginResponseRemoteModel>

    @GET("api/Order/GetOrdersAgent")
    suspend fun getOrders(@Header("ApiKey")key:String,@Query("AgentId")id:Int): Response<List<OrderRemoteModel>>

    @GET("api/Order/GetOrder")
    suspend fun getOrderDetailsById(@Header("ApiKey")key:String,@Query("OrderId")id:Int): Response<OrderDetailsRemoteModel>

    @GET("api/Order/GetPharmacies")
    suspend fun getPharmaciesList(@Header("ApiKey")key:String,@Query("AgentId")id:Int): Response<PharmaciesListRemoteModel>

    @GET("api/Order/GetDistributors")
    suspend fun getDistributorsList(@Header("ApiKey")key:String,@Query("AgentId")id:Int): Response<DistributorsListRemoteModel>

    @GET("api/Order/GetProducts")
    suspend fun getProductsList(@Header("ApiKey")key:String): Response<List<ProductSimpleRemoteModel>>

    @POST("api/Order/AddOrder")
    suspend fun addNewOrder(@Header("ApiKey")key:String,@Body newOrder: AddOrderRemoteModel)



}
