package com.philoSpocAgent.spocordersagent.di

import com.philoSpocAgent.spocordersagent.data.mappers.domainAndRemoteMappers.*
import com.philoSpocAgent.spocordersagent.domain.model.*

import com.raphel.spocagent.data.mappers.domainAndRemoteMappers.*
import com.philoSpocAgent.spocordersagent.data.remote.RemoteDataSource
import com.philoSpocAgent.spocordersagent.data.remote.RemoteDataSourceImpl
import com.philoSpocAgent.spocordersagent.data.remote.SpocApi
import com.philoSpocAgent.spocordersagent.data.remote.models.*
import com.philoSpocAgent.spocordersagent.data.repository.OrdersRepositoryImpl
import com.philoSpocAgent.spocordersagent.domain.mapper.IMapper
import com.philoSpocAgent.spocordersagent.domain.repository.OrdersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Provides
    @Singleton
    fun provideSpocApi(): SpocApi {
        return Retrofit.Builder()
            .baseUrl("http://spocorder-001-site1.atempurl.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpocApi::class.java)
    }



    @Provides
    @Singleton
    fun provideRemoteSource(spocApi: SpocApi): RemoteDataSource {
        return RemoteDataSourceImpl(spocApi)
    }

    @Provides
    @Singleton
    fun provideRepository(remoteDataSource: RemoteDataSource,

                          loginDomainModelToLoginRemoteModelMapper: IMapper<LoginDomainModel, LoginRemoteModel>,
                          loginResponseRemoteModelToLoginResponseDomainModelMapper: IMapper<LoginResponseRemoteModel, LoginResponseDomainModel>,
                          addNewOrderDomainModelToAddNewOrderRemoteModelMapper: IMapper<AddOrderDomainModel, AddOrderRemoteModel>,

                          orderDetailsRemoteModelOrderDomainModelMapper: IMapper<OrderDetailsRemoteModel, OrderDetailsDomainModel>,

                          ordersListRemoteModelToOrdersListDomainModelMapper : IMapper<List<OrderRemoteModel>, List<OrderDomainModel>>,
                          pharmaciesListRemoteModelToPharmaciesListDomainModelMapper : IMapper<List<PharmacyDetailsRemoteModel>, List<PharmacyDetailsDomainModel>>,
                          distributorsListRemoteModelToDistributorsListDomainModelMapper: IMapper<List<DistributorDetailsRemoteModel>, List<DistributorDetailsDomainModel>>,
                          productsListRemoteModelToProductsListDomainModelMapper: IMapper<List<ProductSimpleRemoteModel>, List<ProductSimpleDomainModel>>,


                          ): OrdersRepository {
        return OrdersRepositoryImpl(

            remoteDataSource,

            loginDomainModelToLoginRemoteModelMapper,
            loginResponseRemoteModelToLoginResponseDomainModelMapper,
            addNewOrderDomainModelToAddNewOrderRemoteModelMapper,
            orderDetailsRemoteModelOrderDomainModelMapper,
            ordersListRemoteModelToOrdersListDomainModelMapper,
            pharmaciesListRemoteModelToPharmaciesListDomainModelMapper,
            distributorsListRemoteModelToDistributorsListDomainModelMapper,
            productsListRemoteModelToProductsListDomainModelMapper,
        )

    }



    @Provides
    @Singleton
    fun provideOrderDetailsRemoteModelOrderDomainModelMapper(): IMapper<OrderDetailsRemoteModel, OrderDetailsDomainModel>
    {
        return OrderDetailsRemoteModelOrderDomainModelMapper()
    }

    @Provides
    @Singleton
    fun provideOrderRemoteModelToOrderDomainModelMapper(): IMapper<OrderRemoteModel, OrderDomainModel>
    {
        return OrderRemoteModelToOrderDomainModelMapper()
    }


    @Provides
    @Singleton
    fun provideOrdersListRemoteModelToOrdersListDomainModelMapper(mapper: IMapper<OrderRemoteModel, OrderDomainModel>): IMapper<List<OrderRemoteModel>, List<OrderDomainModel>>
    {
        return OrdersListRemoteModelToOrdersListDomainModelMapper(mapper)
    }


    /***********************************/

//    @Provides
//    @Singleton
//    fun provideProductDataModelToProductDomainModelMapper(): IMapper<ProductDataModel, ProductDetailsDomainModel>
//    {
//        return ProductDataModelToProductDomainModelMapper()
//    }

//    @Provides
//    @Singleton
//    fun provideProductDomainModelToProductDataModelMapper(): IMapper<ProductDetailsDomainModel, ProductDataModel>
//    {
//        return ProductDomainModelToProductDataModelMapper()
//    }


//    @Provides
//    @Singleton
//    fun provideProductDataListToProductDomainListMapper(mapper: IMapper<ProductDataModel, ProductDetailsDomainModel>): IMapper<List<ProductDataModel>, List<ProductDetailsDomainModel>>
//    {
//        return ProductListDataModelToProductListDomainModelMapper(mapper)
//    }

//    @Provides
//    @Singleton
//    fun provideProductDomainListToProductDataListMapper(mapper: IMapper<ProductDetailsDomainModel, ProductDataModel>): IMapper<List<ProductDetailsDomainModel>, List<ProductDataModel>>
//    {
//        return ProductListDomainModelToProductListDataModelMapper(mapper)
//    }


    @Provides
    @Singleton
    fun provideLoginDomainModelToLoginRemoteModelMapper(): IMapper<LoginDomainModel, LoginRemoteModel> {
        return LoginDomainModelToLoginRemoteModelMapper()
    }

    @Provides
    @Singleton
    fun provideLoginResponseRemoteModelToLoginResponseDomainModelMapper(): IMapper<LoginResponseRemoteModel, LoginResponseDomainModel> {
        return LoginResponseRemoteModelToLoginResponseDomainModelMapper()
    }

    @Provides
    @Singleton
    fun provideAddOrderDomainModelToAddOrderRemoteModelMapper(): IMapper<AddOrderDomainModel, AddOrderRemoteModel> {
        return AddNewOrderDomainModelToAddNewOrderRemoteModelMapper()
    }



    @Provides
    @Singleton
    fun providePharmacyDetailsRemoteModelToPharmacyDetailsDomainModelMapper() : IMapper<PharmacyDetailsRemoteModel, PharmacyDetailsDomainModel> {
        return PharmacyDetailsRemoteModelToPharmacyDetailsDomainModelMapper()
    }

    @Provides
    @Singleton
    fun provideDistributorDetailsRemoteModelToDistributorDetailsDomainModelMapper(): IMapper<DistributorDetailsRemoteModel, DistributorDetailsDomainModel> {
        return DistributorDetailsRemoteModelToDistributorDetailsDomainModelMapper()
    }

    @Provides
    @Singleton
    fun provideProductSimpleDetailsRemoteModelToProductsDetailsDomainModelMapper(): IMapper<ProductSimpleRemoteModel, ProductSimpleDomainModel> {
        return ProductSimpleRemoteModelToProductSimpleDomainModelMapper()
    }



    @Provides
    @Singleton
    fun providePharmaciesListRemoteModelPharmaciesListDomainModelMapper(mapper: IMapper<PharmacyDetailsRemoteModel, PharmacyDetailsDomainModel>): IMapper<List<PharmacyDetailsRemoteModel>, List<PharmacyDetailsDomainModel>>
    {
        return PharmaciesListRemoteModelToPharmaciesListDomainModelMapper(mapper)
    }

    @Provides
    @Singleton
    fun provideDistributorsListRemoteModelDistributorsListDomainModelMapper(mapper: IMapper<DistributorDetailsRemoteModel, DistributorDetailsDomainModel>): IMapper<List<DistributorDetailsRemoteModel>, List<DistributorDetailsDomainModel>>
    {
        return DistributorsListRemoteModelToDistributorsListDomainModelMapper(mapper)
    }


    @Provides
    @Singleton
    fun provideProductsListRemoteModelProductsListDomainModelMapper(mapper: IMapper<ProductSimpleRemoteModel, ProductSimpleDomainModel>): IMapper<List<ProductSimpleRemoteModel>, List<ProductSimpleDomainModel>>
    {
        return ProductsListRemoteModelToProductsListDomainModelMapper(mapper)
    }







}