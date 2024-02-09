package com.rodrigoschonardt.orderservice.data.item

data class AddItemFormData( val name : String, val info : String ) {
    init {
        require( name.length in 1..50 )
    }
}
