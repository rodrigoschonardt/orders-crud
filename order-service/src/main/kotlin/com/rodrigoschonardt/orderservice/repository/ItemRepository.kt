package com.rodrigoschonardt.orderservice.repository

import com.rodrigoschonardt.orderservice.data.item.AddItemFormData
import com.rodrigoschonardt.orderservice.data.item.ItemDetailsData
import com.rodrigoschonardt.orderservice.table.Items
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
@Transactional
class ItemRepository {
    fun create( data : AddItemFormData) : UUID {
        val uuid : EntityID<UUID> = Items.insertAndGetId{
            it[name] = data.name
            it[info] = data.info
        }

        return uuid.value
    }

    fun fetchAll() : List<ItemDetailsData> {
        val items : List<ItemDetailsData> = Items.selectAll().map {
            ItemDetailsData( id = it[Items.id].value,
                name = it[Items.name],
                info = it[Items.info] ) }

        return items
    }

    fun fetchById( uuid : UUID) : ItemDetailsData? {
        val item : ItemDetailsData? = Items.selectAll().where(Items.id eq uuid).firstOrNull()?.let {
            ItemDetailsData( id = it[Items.id].value,
                name = it[Items.name],
                info = it[Items.info] ) }

        return item
    }
}