package com.philoSpocAgent.spocordersagent.data.mappers.domainAndRemoteMappers
import com.philoSpocAgent.spocordersagent.data.remote.models.DistributorDetailsRemoteModel
import com.philoSpocAgent.spocordersagent.domain.mapper.IMapper
import com.philoSpocAgent.spocordersagent.domain.model.DistributorDetailsDomainModel

class DistributorDetailsRemoteModelToDistributorDetailsDomainModelMapper :
    IMapper<DistributorDetailsRemoteModel, DistributorDetailsDomainModel> {
    override fun map(input: DistributorDetailsRemoteModel): DistributorDetailsDomainModel {
        return DistributorDetailsDomainModel(
            input.id?:0,
            input.name?:"",

        )
    }

}