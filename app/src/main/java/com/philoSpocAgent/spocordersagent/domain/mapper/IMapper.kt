package com.philoSpocAgent.spocordersagent.domain.mapper

interface IMapper<F,T> {

    fun map(input:F):T

}