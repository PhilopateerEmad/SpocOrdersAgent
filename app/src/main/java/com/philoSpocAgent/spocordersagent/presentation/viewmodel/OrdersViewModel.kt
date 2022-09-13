package com.philoSpocAgent.spocordersagent.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.philoSpocAgent.spocordersagent.domain.model.*
import com.philoSpocAgent.spocordersagent.domain.usecase.*
//import com.raphel.spoc.domain.usecase.AddOrder
//import com.raphel.spoc.domain.usecase.AddProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(

    private val logIn: LogIn,
    private val getOrdersList: GetOrdersList,
    private val getOrderDetails: GetOrderDetails,
    private val addNewOrder: AddNewOrder,
    private val getPharmaciesDistributorsProductsLists: GetPharmaciesDistributorsProductsLists,


    ):ViewModel() {


    suspend fun logIn(login: LoginDomainModel): LoginResponseDomainModel {
            return logIn.execute(login)
    }

    suspend fun getOrdersList(key:String,agentId: Int):List<OrderDomainModel>{
        return getOrdersList.execute(key,agentId)
    }

    suspend fun getOrderDetails(key:String,orderId: Int): OrderDetailsDomainModel {
        return getOrderDetails.execute(key,orderId)
    }


    suspend fun getPharmaciesDistributorsProductsLists(key:String,agentId: Int): PharmaDistributorsProductsListsDomainModel {
        return getPharmaciesDistributorsProductsLists.execute(key,agentId)
    }

    suspend fun addNewOrder(key:String,newOrder: AddOrderDomainModel){
        addNewOrder.execute(key,newOrder)
    }
}