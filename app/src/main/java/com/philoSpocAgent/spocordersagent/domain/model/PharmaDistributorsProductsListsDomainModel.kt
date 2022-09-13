package com.philoSpocAgent.spocordersagent.domain.model

data class PharmaDistributorsProductsListsDomainModel(
    val pharmaciesList:List<PharmacyDetailsDomainModel>,
    val distributorsList:List<DistributorDetailsDomainModel>,
    val productsList:List<ProductSimpleDomainModel>,
)
