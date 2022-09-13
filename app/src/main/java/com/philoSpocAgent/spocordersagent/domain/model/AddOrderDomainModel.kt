package com.philoSpocAgent.spocordersagent.domain.model

data class AddOrderDomainModel(
    val PharmacyID: Int,
    val DistributorID: Int,
    val AgentID: Int,
    val BranchName: String,
    val PharmacyCode: String,
    val ExpiryGoods: Boolean,
    val ValueOfExpiredGoods: String,
    val Products: List<ProductNameQuantityDomainModel>,

    )
