package com.rodrigoschonardt.orderservice.data.order

import com.rodrigoschonardt.orderservice.data.item.ItemDetailsData
import java.time.LocalDateTime
import java.util.*

data class OrderDetailsData(val id : UUID, val name : String, val email : String, val info : String,
                            val dateRegister : LocalDateTime, val items : MutableList<ItemDetailsData> )
