package com.philoSpocAgent.spocordersagent.data.mappers.domainAndRemoteMappers

import com.philoSpocAgent.spocordersagent.data.remote.models.PharmacyDetailsRemoteModel
import com.philoSpocAgent.spocordersagent.domain.mapper.IMapper
import com.philoSpocAgent.spocordersagent.domain.model.PharmacyDetailsDomainModel
import javax.inject.Inject

class PharmaciesListRemoteModelToPharmaciesListDomainModelMapper @Inject constructor(private val mapper: IMapper<PharmacyDetailsRemoteModel, PharmacyDetailsDomainModel>) :
    IMapper<List<PharmacyDetailsRemoteModel>, List<PharmacyDetailsDomainModel>> {

    override fun map(input: List<PharmacyDetailsRemoteModel>): List<PharmacyDetailsDomainModel> {
        return input.map {
            mapper.map(it)
        }
    }
}
