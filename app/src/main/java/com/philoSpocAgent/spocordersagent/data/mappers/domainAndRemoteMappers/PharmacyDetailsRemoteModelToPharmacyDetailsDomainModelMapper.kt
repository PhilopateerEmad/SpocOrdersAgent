package com.philoSpocAgent.spocordersagent.data.mappers.domainAndRemoteMappers
import com.philoSpocAgent.spocordersagent.data.remote.models.PharmacyDetailsRemoteModel
import com.philoSpocAgent.spocordersagent.domain.mapper.IMapper
import com.philoSpocAgent.spocordersagent.domain.model.PharmacyDetailsDomainModel

class PharmacyDetailsRemoteModelToPharmacyDetailsDomainModelMapper :
    IMapper<PharmacyDetailsRemoteModel, PharmacyDetailsDomainModel> {
    override fun map(input: PharmacyDetailsRemoteModel): PharmacyDetailsDomainModel {
        return PharmacyDetailsDomainModel(
            input.id?:0,
            input.name?:"",
            input.address?:"",
            input.phoneNumber?:"",
        )
    }

}