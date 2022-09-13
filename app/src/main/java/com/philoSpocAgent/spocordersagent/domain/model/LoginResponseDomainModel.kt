package com.philoSpocAgent.spocordersagent.domain.model

data class LoginResponseDomainModel(
    val isSuccess: Boolean,
    val message: String,
    val data: String,
)
