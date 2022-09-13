package com.philoSpocAgent.spocordersagent.data.mappers.domainAndRemoteMappers
import com.philoSpocAgent.spocordersagent.data.remote.models.AddOrderRemoteModel
import com.philoSpocAgent.spocordersagent.data.remote.models.ProductNameQuantityRemoteModel
import com.philoSpocAgent.spocordersagent.domain.mapper.IMapper
import com.philoSpocAgent.spocordersagent.domain.model.AddOrderDomainModel
import com.philoSpocAgent.spocordersagent.domain.model.ProductNameQuantityDomainModel


class AddNewOrderDomainModelToAddNewOrderRemoteModelMapper :
    IMapper<AddOrderDomainModel, AddOrderRemoteModel> {
    override fun map(input: AddOrderDomainModel): AddOrderRemoteModel {
        return AddOrderRemoteModel(
            input.PharmacyID,
            input.DistributorID,
            input.AgentID,
            input.BranchName,
            input.PharmacyCode,
            input.ExpiryGoods,
            input.ValueOfExpiredGoods,
            bindProductsList(input.Products),

        )
    }
    private fun bindProductsList(products: List<ProductNameQuantityDomainModel>): List<ProductNameQuantityRemoteModel> {
        val productsList = mutableListOf<ProductNameQuantityRemoteModel>()

        for(product in products){
            productsList.add(ProductNameQuantityRemoteModel(product.productId,product.quantity.toInt()))
        }

        return productsList
    }
}