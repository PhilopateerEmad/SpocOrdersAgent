package com.philoSpocAgent.spocordersagent.data.mappers.domainAndRemoteMappers

import com.philoSpocAgent.spocordersagent.data.remote.models.OrderRemoteModel
import com.philoSpocAgent.spocordersagent.domain.mapper.IMapper
import com.philoSpocAgent.spocordersagent.domain.model.OrderDomainModel
import javax.inject.Inject

class OrdersListRemoteModelToOrdersListDomainModelMapper @Inject constructor(private val mapper: IMapper<OrderRemoteModel, OrderDomainModel>) :
    IMapper<List<OrderRemoteModel>, List<OrderDomainModel>> {

    override fun map(input: List<OrderRemoteModel>): List<OrderDomainModel> {
        return input.map {
            mapper.map(it)
        }
    }
}
