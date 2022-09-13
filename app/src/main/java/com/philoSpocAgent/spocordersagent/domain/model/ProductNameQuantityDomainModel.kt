package com.philoSpocAgent.spocordersagent.domain.model

data class ProductNameQuantityDomainModel(val productId:Int, val productName: String, val quantity:String, ){
    override fun toString(): String {
        return productName
    }
}
