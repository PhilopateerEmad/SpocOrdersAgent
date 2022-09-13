package com.philoSpocAgent.spocordersagent.data.mappers.domainAndRemoteMappers

import com.philoSpocAgent.spocordersagent.data.remote.models.OrderDetailsRemoteModel
import com.philoSpocAgent.spocordersagent.data.remote.models.ProductDetailsRemoteModel
import com.philoSpocAgent.spocordersagent.domain.mapper.IMapper
import com.philoSpocAgent.spocordersagent.domain.model.OrderDetailsDomainModel
import com.philoSpocAgent.spocordersagent.domain.model.ProductDetailsDomainModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


class OrderDetailsRemoteModelOrderDomainModelMapper:
    IMapper<OrderDetailsRemoteModel, OrderDetailsDomainModel> {
    override fun map(input: OrderDetailsRemoteModel): OrderDetailsDomainModel {
        return OrderDetailsDomainModel(
            input.agentName?:"",
            input.branchName?:"",
            input.distributorName?:"",
            input.expiryGoods?:false,
            input.id?.toString()?:"",
            bindDateType(input.orderDate),
            input.pharmacyCode?:"",
            input.pharmacyName?:"",
            bindProductsRemoteToDomainModel(input.products,input.id),
            input.valueOfExpiredGoods?:"",
        )
    }

    private fun bindProductsRemoteToDomainModel(productDetailsRemoteModel: List<ProductDetailsRemoteModel>?, orderId:Int?): List<ProductDetailsDomainModel> {

        val productsList = mutableListOf<ProductDetailsDomainModel>()

        if (productDetailsRemoteModel != null) {
            for (product in productDetailsRemoteModel.indices) {

                productsList.add(
                    product, ProductDetailsDomainModel(

                        bindDateType(productDetailsRemoteModel[product].expiryDate),
                        orderId?.toString()?:"",
                        productDetailsRemoteModel[product].name ?: "",
                        productDetailsRemoteModel[product].quantity ?: 0,
                    )
                )
            }
        }
        else{
            productsList.add(0, ProductDetailsDomainModel(
                "",
                "",
                "",
                0,
            )
            )
        }

        return productsList
    }

    private fun bindDateType(orderDate: String?): String {

        val str:String = try {
            val localDateTime: LocalDateTime = LocalDateTime.parse(orderDate?: "0000-00-00T00:00:00")
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            formatter.format(localDateTime)
        } catch (e: DateTimeParseException){
            ""
        }

        return str

    }


}