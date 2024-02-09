package com.rodrigoschonardt.orderservice.table

import org.jetbrains.exposed.dao.id.UUIDTable

object Items : UUIDTable() {
    val name = varchar( "name", 50 )
    val info = varchar( "info", 400 )
}