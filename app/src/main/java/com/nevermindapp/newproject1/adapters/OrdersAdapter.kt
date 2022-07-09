package com.zerowasteapp.zerowaste.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nevermindapp.newproject1.R
import java.text.SimpleDateFormat
import java.util.*


class OrdersAdapter(val requireContext: Context, val listOrder: List<OrderModel>) :
    RecyclerView.Adapter<OrdersAdapter.CartVH>() {

    inner class CartVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderNo=itemView.findViewById<TextView>(R.id.tvordernumber)
        val tvtotalprice=itemView.findViewById<TextView>(R.id.tvtotalamt)
        val tvorderDetails=itemView.findViewById<TextView>(R.id.tvorderDetails)
        val tvorderdate=itemView.findViewById<TextView>(R.id.tvorderDatetxt)
        val ordrStatus=itemView.findViewById<TextView>(R.id.tvorderStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartVH {
        return CartVH(
            LayoutInflater.from(requireContext).inflate(R.layout.order_item, parent, false)
        )
    }


    override fun getItemCount(): Int {
        return listOrder.size
    }


    override fun onBindViewHolder(holder: CartVH, position: Int) {
             val ordr=listOrder[position]

        holder.orderNo.text=ordr.order_id
        holder.tvorderDetails.text=ordr.order_details.replace("\n",",  ")

        holder.tvtotalprice.text="$ "+ordr.total_amount

        try {
            val timeStamp = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(ordr.order_date.toLong())
            holder.tvorderdate.text=timeStamp
        }catch (e: Exception){
            Log.e("exception", e.printStackTrace().toString())
        }



        if(ordr.isAccepted){
            holder.ordrStatus.text="Accepted"
        }else if(ordr.isAccepted && ordr.isDelivered){
            holder.ordrStatus.text="Delivered"
        }else{
            holder.ordrStatus.text="Received"
        }


    }
}

