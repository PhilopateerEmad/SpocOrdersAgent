package com.philoSpocAgent.spocordersagent.data.mappers.domainAndRemoteMappers
import com.philoSpocAgent.spocordersagent.data.remote.models.LoginResponseRemoteModel
import com.philoSpocAgent.spocordersagent.domain.mapper.IMapper
import com.philoSpocAgent.spocordersagent.domain.model.LoginResponseDomainModel

class LoginResponseRemoteModelToLoginResponseDomainModelMapper :
    IMapper<LoginResponseRemoteModel, LoginResponseDomainModel> {
    override fun map(input: LoginResponseRemoteModel): LoginResponseDomainModel {
        return LoginResponseDomainModel(
            input.isSuccess?:false,
            input.token?:"no message",
            input.data?.toString()?:"no data",

        )


    }
}