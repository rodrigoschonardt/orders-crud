package com.rodrigoschonardt.orderservice.util

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory

object RabbitmqConnectionFactory {
    fun getConnection() : Connection {
        val factory = ConnectionFactory()

        factory.host = System.getenv( "RABBITMQ_HOST" ) ?: "localhost"
        factory.port = 5672
        factory.username = "guest"
        factory.password = "guest"

        return factory.newConnection()
    }
}