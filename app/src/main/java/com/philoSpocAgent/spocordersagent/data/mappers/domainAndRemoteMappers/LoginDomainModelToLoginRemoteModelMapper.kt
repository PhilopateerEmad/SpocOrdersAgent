package com.philoSpocAgent.spocordersagent.data.mappers.domainAndRemoteMappers
import com.philoSpocAgent.spocordersagent.data.remote.models.LoginRemoteModel
import com.philoSpocAgent.spocordersagent.domain.mapper.IMapper
import com.philoSpocAgent.spocordersagent.domain.model.LoginDomainModel



class LoginDomainModelToLoginRemoteModelMapper : IMapper<LoginDomainModel, LoginRemoteModel> {
    override fun map(input: LoginDomainModel): LoginRemoteModel {
        return LoginRemoteModel(
            input.email,
            input.password,
        )
    }
}