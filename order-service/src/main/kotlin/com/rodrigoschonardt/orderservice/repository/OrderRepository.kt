package com.rodrigoschonardt.orderservice.repository

import com.rodrigoschonardt.orderservice.data.item.ItemDetailsData
import com.rodrigoschonardt.orderservice.data.order.AddOrderFormData
import com.rodrigoschonardt.orderservice.data.order.OrderDetailsData
import com.rodrigoschonardt.orderservice.table.Items
import com.rodrigoschonardt.orderservice.table.OrderItems
import com.rodrigoschonardt.orderservice.table.Orders
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Component
@Transactional
class OrderRepository {
    fun create( data : AddOrderFormData ) : UUID {
        val uuid : EntityID<UUID> = Orders.insertAndGetId{
            it[name] = data.name
            it[info] = data.info
            it[email] = data.email
            it[dateRegister] = LocalDateTime.now()
        }

        OrderItems.batchInsert( data.items ) { itemId ->
            this[OrderItems.itemId] = itemId
            this[OrderItems.orderId] = uuid
        }

        return uuid.value
    }

    fun fetchById( id : UUID ) : OrderDetailsData? {
        val list = fetchOrders( Orders.id eq id )

        return if ( list.isEmpty() ) null else list[0]
    }

    fun fetchAll() : List<OrderDetailsData> {
        return fetchOrders( Op.TRUE )
    }

    fun fetchByText( text: String ): List<OrderDetailsData>? {
        return fetchOrders( Orders.searchable like "%$text%")
    }

    private fun fetchOrders( where : Op<Boolean> ) : List<OrderDetailsData> {
        val columns = mutableListOf<Expression<*>>()

        columns.addAll( Orders.readableColumns )
        columns.addAll( Items.columns )

        val map = mutableMapOf<UUID, OrderDetailsData>()

        Orders.join( OrderItems, JoinType.INNER, additionalConstraint = {
                    Orders.id eq OrderItems.orderId
                } ).join( Items, JoinType.INNER, additionalConstraint = {
                    OrderItems.itemId eq Items.id
                } ).select( columns ).where( where ).map {
            val id : UUID = it[Orders.id].value

            if ( !map.containsKey( id ) )
            {
                map[id] = OrderDetailsData( id, it[Orders.name],
                    it[Orders.email], it[Orders.info], it[Orders.dateRegister],
                    mutableListOf() )
            }

            map[id]?.items?.add( ItemDetailsData( it[Items.id].value,
                it[Items.name],
                it[Items.info] ) )
        }

        return map.values.toList()
    }
}