package com.philoSpocAgent.spocordersagent.data.mappers.domainAndRemoteMappers

import com.philoSpocAgent.spocordersagent.data.remote.models.DistributorDetailsRemoteModel
import com.philoSpocAgent.spocordersagent.domain.mapper.IMapper
import com.philoSpocAgent.spocordersagent.domain.model.DistributorDetailsDomainModel
import javax.inject.Inject

class DistributorsListRemoteModelToDistributorsListDomainModelMapper @Inject constructor(private val mapper: IMapper<DistributorDetailsRemoteModel, DistributorDetailsDomainModel>) :
    IMapper<List<DistributorDetailsRemoteModel>, List<DistributorDetailsDomainModel>> {

    override fun map(input: List<DistributorDetailsRemoteModel>): List<DistributorDetailsDomainModel> {
        return input.map {
            mapper.map(it)
        }
    }
}
