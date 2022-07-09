package com.zerowasteapp.zerowaste.adapters

data class OrderModel(
    var order_id: String = "",
    var user_id: String = "",
    var user_name: String = "",
    var order_date: String = "",
    var mo_number: String = "",
    var address: String = "",
    var zipcode: String = "",
    var order_details: String = "",
    var total_amount: String = "",
    var isAccepted: Boolean = false,
    var isDelivered:Boolean=false,
    var payment:String=""
)

