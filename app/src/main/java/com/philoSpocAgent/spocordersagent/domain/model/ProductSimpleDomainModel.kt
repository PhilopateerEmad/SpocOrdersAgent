package com.philoSpocAgent.spocordersagent.domain.model

data class ProductSimpleDomainModel(val productId:Int, val productName: String, val productDescription:String, ){
    override fun toString(): String {
        return productName
    }
}


