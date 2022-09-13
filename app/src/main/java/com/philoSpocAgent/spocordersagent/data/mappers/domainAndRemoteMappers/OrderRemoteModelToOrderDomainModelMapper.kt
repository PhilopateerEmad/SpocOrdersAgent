package com.raphel.spocagent.data.mappers.domainAndRemoteMappers
import com.philoSpocAgent.spocordersagent.data.remote.models.OrderRemoteModel
import com.philoSpocAgent.spocordersagent.domain.mapper.IMapper
import com.philoSpocAgent.spocordersagent.domain.model.OrderDomainModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class OrderRemoteModelToOrderDomainModelMapper : IMapper<OrderRemoteModel, OrderDomainModel> {
    override fun map(input: OrderRemoteModel): OrderDomainModel {
        return OrderDomainModel(
            input.id?.toString()?:"0",
            input.pharmacyName?:"",
            bindDateType(input.orderDate),

        )
    }

    private fun bindDateType(orderDate: String?): String {
        val str:String = try {
            val localDateTime: LocalDateTime = LocalDateTime.parse(orderDate?: "0000-00-00T00:00:00")
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            formatter.format(localDateTime)
        } catch (e: DateTimeParseException){
            ""
        }

        return str
    }



}