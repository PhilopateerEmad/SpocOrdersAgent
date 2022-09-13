package com.philoSpocAgent.spocordersagent.domain.model

data class DistributorDetailsDomainModel(val id:Int, val name:String, ){
    override fun toString(): String {
        return name
    }
}
