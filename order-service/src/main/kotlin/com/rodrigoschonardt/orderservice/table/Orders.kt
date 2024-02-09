package com.rodrigoschonardt.orderservice.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.javatime.datetime

object Orders : UUIDTable() {
    val readableColumns = mutableListOf<Expression<*>>()

    val name = varchar( "name", 50 )
    val info = varchar( "info", 400 )
    val email = varchar( "email", 50 )
    val dateRegister = datetime( "dt_register" )

    // used for full text search
    val searchable = text( "searchable" )

    init {
        readableColumns.addAll( this.columns )
        readableColumns.remove( searchable )
    }
}