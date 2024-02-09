package com.rodrigoschonardt.orderservice.controller

import com.rodrigoschonardt.orderservice.data.order.AddOrderFormData
import com.rodrigoschonardt.orderservice.data.order.OrderDetailsData
import com.rodrigoschonardt.orderservice.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.util.*

@RestController
@RequestMapping( "orders" )
class OrderController( private val service : OrderService ) {
    @PostMapping
    fun create( @RequestBody data : AddOrderFormData, uriBuilder : UriComponentsBuilder ) : ResponseEntity<String> {

        val uuid = service.create( data )

        val uri : URI = uriBuilder.path( "orders/{uuid}" ).buildAndExpand( uuid ).toUri()

        return ResponseEntity.created( uri ).body( uuid.toString() )
    }

    @GetMapping( "/{uuid}" )
    fun fetchById( @PathVariable uuid : UUID) : ResponseEntity<OrderDetailsData> {
        val order : OrderDetailsData? = service.fetchById( uuid )

        return if ( order != null ) ResponseEntity.ok( order ) else ResponseEntity.noContent().build()
    }

    @GetMapping
    fun fetchAll() : ResponseEntity<List<OrderDetailsData>> {
        return ResponseEntity.ok( service.fetchAll() )
    }

    @GetMapping( "/text/{text}" )
    fun fetchByText( @PathVariable text : String ) : ResponseEntity<List<OrderDetailsData>> {
        return ResponseEntity.ok( service.fetchByText( text ) )
    }
}