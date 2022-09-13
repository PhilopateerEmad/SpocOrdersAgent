package com.philoSpocAgent.spocordersagent.data.remote.models

data class AddOrderRemoteModel(
    val PharmacyID:Int,
    val DistributorID:Int,
    val AgentID:Int,
    val BranchName:String,
    val PharmacyCode:String,
    val ExpiryGoods:Boolean,
    val ValueOfExpiredGoods:String,
    val Products:List<ProductNameQuantityRemoteModel>,


    )
