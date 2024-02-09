package com.rodrigoschonardt.orderservice.service

import com.rabbitmq.client.ConnectionFactory
import com.rodrigoschonardt.orderservice.data.item.AddItemFormData
import com.rodrigoschonardt.orderservice.data.item.ItemDetailsData
import com.rodrigoschonardt.orderservice.repository.ItemRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class ItemService( private val repository : ItemRepository ) {
    fun create( data : AddItemFormData ) : UUID {
        return repository.create( data )
    }

    fun fetchAll() : List<ItemDetailsData> {
        return repository.fetchAll()
    }

    fun fetchById( uuid : UUID ) : ItemDetailsData? {
        return repository.fetchById(uuid)
    }
}