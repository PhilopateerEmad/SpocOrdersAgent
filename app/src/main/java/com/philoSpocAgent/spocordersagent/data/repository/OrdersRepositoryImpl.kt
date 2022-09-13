package com.philoSpocAgent.spocordersagent.data.repository

import com.philoSpocAgent.spocordersagent.domain.model.*
import com.philoSpocAgent.spocordersagent.data.remote.RemoteDataSource
import com.philoSpocAgent.spocordersagent.data.remote.models.*
import com.philoSpocAgent.spocordersagent.domain.mapper.IMapper
import com.philoSpocAgent.spocordersagent.domain.repository.OrdersRepository
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor (
    private val remoteDataSource: RemoteDataSource,

    private val loginDomainModelToLoginRemoteModelMapper: IMapper<LoginDomainModel, LoginRemoteModel>,
    private val loginResponseRemoteModelToLoginResponseDomainModelMapper: IMapper<LoginResponseRemoteModel, LoginResponseDomainModel>,
    private val addNewOrderDomainModelToAddNewOrderRemoteModelMapper: IMapper<AddOrderDomainModel, AddOrderRemoteModel>,
    private val orderDetailsRemoteModelOrderDomainModelMapper: IMapper<OrderDetailsRemoteModel, OrderDetailsDomainModel>,

    private val ordersListRemoteModelToOrdersListDomainModelMapper : IMapper<List<OrderRemoteModel>, List<OrderDomainModel>>,
    private val pharmaciesListRemoteModelToPharmaciesListDomainModelMapper : IMapper<List<PharmacyDetailsRemoteModel>, List<PharmacyDetailsDomainModel>>,
    private val distributorsListRemoteModelToDistributorsListDomainModelMapper: IMapper<List<DistributorDetailsRemoteModel>, List<DistributorDetailsDomainModel>>,
    private val productsListRemoteModelToProductsListDomainModelMapper: IMapper<List<ProductSimpleRemoteModel>, List<ProductSimpleDomainModel>>,





    ): OrdersRepository {


    override suspend fun login(login: LoginDomainModel): LoginResponseDomainModel {
        val loginResponseRemoteModel = remoteDataSource.login(loginDomainModelToLoginRemoteModelMapper.map(login))
        return loginResponseRemoteModelToLoginResponseDomainModelMapper.map(loginResponseRemoteModel)
    }

    override suspend fun getOrdersListFromServer(key: String, agentId: Int): List<OrderDomainModel> {
        val ordersList = remoteDataSource.getOrders(key,agentId)
        return ordersListRemoteModelToOrdersListDomainModelMapper.map(ordersList)
    }

    override suspend fun getOrderDetailsFromServer(key:String,orderId: Int): OrderDetailsDomainModel {
        val orderDetails = remoteDataSource.getOrderById(key,orderId)
        return orderDetailsRemoteModelOrderDomainModelMapper.map(orderDetails)
    }

    override suspend fun getPharmaciesListFromServer(key: String, agentId: Int): List<PharmacyDetailsDomainModel> {
        val pharmaciesList = remoteDataSource.getPharmaciesList(key,agentId)
        return pharmaciesListRemoteModelToPharmaciesListDomainModelMapper.map(pharmaciesList)
    }


    override suspend fun getPharmaDistributorsProductsListsFromServer(key: String, agentId: Int): PharmaDistributorsProductsListsDomainModel {
        val pharmaciesList = remoteDataSource.getPharmaciesList(key,agentId)
        val distributorsList = remoteDataSource.getDistributorsList(key,agentId)
        val productsList = remoteDataSource.getProductsList(key)
        return PharmaDistributorsProductsListsDomainModel(
            pharmaciesListRemoteModelToPharmaciesListDomainModelMapper.map(pharmaciesList),
            distributorsListRemoteModelToDistributorsListDomainModelMapper.map(distributorsList),
            productsListRemoteModelToProductsListDomainModelMapper.map(productsList)
        )

    }

    override suspend fun addNewOrder(key: String, newOrder: AddOrderDomainModel) {
        remoteDataSource.addNewOrder(key,addNewOrderDomainModelToAddNewOrderRemoteModelMapper.map(newOrder))
    }


    override suspend fun getDistributorsListFromServer(key: String, agentId: Int): List<DistributorDetailsDomainModel> {
        val distributorsList = remoteDataSource.getDistributorsList(key,agentId)
        return distributorsListRemoteModelToDistributorsListDomainModelMapper.map(distributorsList)
    }





}
