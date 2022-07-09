package adapters

import Models.CatagoryModel
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nevermindapp.newproject1.R
import database.CartModel



class CartItemsAdapters(val requireContext: Context,  val clickListener: ClickListener) :
    RecyclerView.Adapter<CartItemsAdapters.CartVH>() {

    val listCartProducts= ArrayList<CartModel>()


    fun updateList(newList: List<CartModel>){
        listCartProducts.clear()
        listCartProducts.addAll(newList)

        notifyDataSetChanged()
    }
    inner class CartVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productimage = itemView.findViewById<ImageView>(R.id.picproduct)
        val producttitle = itemView.findViewById<TextView>(R.id.tvproducttitle)
        val btnminus = itemView.findViewById<ImageView>(R.id.minusCardBtnitem)
        val btnplus = itemView.findViewById<ImageView>(R.id.plusCardBtnitem)
        val productquantity = itemView.findViewById<TextView>(R.id.tvproductquantity)
        val totalproductprice = itemView.findViewById<TextView>(R.id.tvproducttotal)
        val productprice = itemView.findViewById<TextView>(R.id.tvproductpriceitem)
        val btndeletefromcart = itemView.findViewById<ImageView>(R.id.btndeletefromcart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartVH {
        return CartVH(
            LayoutInflater.from(requireContext).inflate(R.layout.item_cart, parent, false)
        )
    }


    override fun getItemCount(): Int {
        return listCartProducts.size
    }


    override fun onBindViewHolder(holder: CartVH, position: Int) {
        Glide.with(requireContext).load(listCartProducts[position].link).into(holder.productimage)
        holder.producttitle.text = listCartProducts[position].title
        holder.productprice.text = listCartProducts[position].price
        holder.productquantity.text = listCartProducts[position].quantity.toString()
        holder.totalproductprice.text =
            ((Integer.parseInt(listCartProducts[position].price)) * listCartProducts[position].quantity).toString()

        holder.btnminus.setOnClickListener {
            clickListener.minusClick(listCartProducts[position])
        }

        holder.btnplus.setOnClickListener {
           clickListener.plusClick(listCartProducts[position])
        }



        holder.btndeletefromcart.setOnClickListener {
            clickListener.deleteproduct(listCartProducts[position])

        }

    }
}
interface ClickListener {
    fun deleteproduct(item: CartModel)
    fun minusClick(item: CartModel)
    fun plusClick(item: CartModel)


}
