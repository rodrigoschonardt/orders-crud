package com.rodrigoschonardt.orderservice.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rabbitmq.client.Channel
import com.rodrigoschonardt.orderservice.data.order.AddOrderFormData
import com.rodrigoschonardt.orderservice.data.order.OrderDetailsData
import com.rodrigoschonardt.orderservice.repository.OrderRepository
import com.rodrigoschonardt.orderservice.util.LocalDateTimeAdapter
import com.rodrigoschonardt.orderservice.util.RabbitmqConnectionFactory
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class OrderService( private val repository : OrderRepository ) {
    fun create( data : AddOrderFormData ) : UUID {
        val id : UUID = repository.create( data )

        val details : OrderDetailsData? = fetchById( id );

        if ( details != null )
        {
            RabbitmqConnectionFactory.getConnection().use { c ->
                val channel : Channel = c.createChannel()

                channel.queueDeclare( "order-email", false, false, false, null )

                val gson : Gson = GsonBuilder().registerTypeAdapter( LocalDateTime::class.java, LocalDateTimeAdapter() ).create();

                channel.basicPublish( "", "order-email", null, gson.toJson( details ).toByteArray() )
            }
        }

        return id;
    }

    fun fetchById( id : UUID ) : OrderDetailsData? {
        return repository.fetchById( id )
    }

    fun fetchAll() : List<OrderDetailsData> {
        return repository.fetchAll()
    }

    fun fetchByText( text: String ): List<OrderDetailsData>? {
        return repository.fetchByText( text )
    }
}