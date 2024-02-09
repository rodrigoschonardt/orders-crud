package com.rodrigoschonardt.orderservice.controller

import com.rodrigoschonardt.orderservice.data.item.AddItemFormData
import com.rodrigoschonardt.orderservice.data.item.ItemDetailsData
import com.rodrigoschonardt.orderservice.service.ItemService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.util.*

@RestController
@RequestMapping( "/items" )
class ItemController( private val service : ItemService ) {

    @PostMapping
    fun create( @RequestBody body : AddItemFormData, uriBuilder : UriComponentsBuilder ) : ResponseEntity<String> {
        val uuid : UUID = service.create( body )

        val uri : URI = uriBuilder.path( "items/{uuid}" ).buildAndExpand( uuid ).toUri()

        return ResponseEntity.created( uri ).body( uuid.toString() )
    }

    @GetMapping( "/{uuid}" )
    fun fetchItem( @PathVariable uuid : UUID ) : ResponseEntity<ItemDetailsData> {
        val item : ItemDetailsData? = service.fetchById( uuid )

        return if ( item != null ) ResponseEntity.ok( item ) else ResponseEntity.noContent().build()
    }

    @GetMapping
    fun fetchAll() : ResponseEntity<List<ItemDetailsData>> {
        return ResponseEntity.ok( service.fetchAll() )
    }
}