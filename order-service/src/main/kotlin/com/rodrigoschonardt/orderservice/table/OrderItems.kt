package com.rodrigoschonardt.orderservice.table

import org.jetbrains.exposed.sql.Table

object OrderItems : Table("order_items") {
    val orderId = reference("ref_order", Orders).index( "order_idx" )
    val itemId = reference( "ref_item", Items ).index( "item_idx" )
}