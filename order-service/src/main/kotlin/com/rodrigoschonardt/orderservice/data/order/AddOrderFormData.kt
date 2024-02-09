package com.rodrigoschonardt.orderservice.data.order

import java.util.*

data class AddOrderFormData( val name : String, val info : String, val email : String, val items : List<UUID> ) {
     init {
         require( name.length in 1..50 )
         require( email.length in 1..50 )
         require( items.isNotEmpty() )
     }
}
