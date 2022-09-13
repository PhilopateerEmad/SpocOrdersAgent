package com.philoSpocAgent.spocordersagent.domain.model


data class PharmacyDetailsDomainModel( val id:Int, val name:String, val address:String, val phoneNumber:String) {

    override fun toString(): String {
        return name
    }
}
