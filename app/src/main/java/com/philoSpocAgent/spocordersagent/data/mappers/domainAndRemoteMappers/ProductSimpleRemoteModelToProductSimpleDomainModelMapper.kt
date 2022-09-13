package com.philoSpocAgent.spocordersagent.data.mappers.domainAndRemoteMappers

import com.philoSpocAgent.spocordersagent.data.remote.models.ProductSimpleRemoteModel
import com.philoSpocAgent.spocordersagent.domain.mapper.IMapper
import com.philoSpocAgent.spocordersagent.domain.model.ProductSimpleDomainModel

class ProductSimpleRemoteModelToProductSimpleDomainModelMapper :
    IMapper<ProductSimpleRemoteModel, ProductSimpleDomainModel> {
    override fun map(input: ProductSimpleRemoteModel): ProductSimpleDomainModel {
        return ProductSimpleDomainModel(
            input.id?:0,
            input.name?:"",
            input.description?:"",
        )
    }

}