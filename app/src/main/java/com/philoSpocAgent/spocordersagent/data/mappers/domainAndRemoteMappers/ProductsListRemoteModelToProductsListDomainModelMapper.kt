package com.philoSpocAgent.spocordersagent.data.mappers.domainAndRemoteMappers

import com.philoSpocAgent.spocordersagent.data.remote.models.ProductSimpleRemoteModel
import com.philoSpocAgent.spocordersagent.domain.mapper.IMapper
import com.philoSpocAgent.spocordersagent.domain.model.ProductSimpleDomainModel
import javax.inject.Inject

class ProductsListRemoteModelToProductsListDomainModelMapper @Inject constructor(private val mapper: IMapper<ProductSimpleRemoteModel, ProductSimpleDomainModel>) :
    IMapper<List<ProductSimpleRemoteModel>, List<ProductSimpleDomainModel>> {

    override fun map(input: List<ProductSimpleRemoteModel>): List<ProductSimpleDomainModel> {
        return input.map {
            mapper.map(it)
        }
    }
}
