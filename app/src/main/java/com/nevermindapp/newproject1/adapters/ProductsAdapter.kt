package adapters

import Models.ProductsModel
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nevermindapp.newproject1.R
import com.nevermindapp.newproject1.ui.rest.ProductDetailActivity


class ProductsAdapter (val requireContext: Context) :
    RecyclerView.Adapter<ProductsAdapter.ProductsVH>() {
    private val productsList=ArrayList<ProductsModel>()

    inner class ProductsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivwallpaper = itemView.findViewById<ImageView>(R.id.productpic)
        val tvTitle = itemView.findViewById<TextView>(R.id.tvproducttitle)
        val price=itemView.findViewById<TextView>(R.id.productPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsVH {
        return ProductsVH(
            LayoutInflater.from(requireContext).inflate(R.layout.item_product, parent, false)
        )
    }


    override fun getItemCount(): Int {
        return productsList.size
    }


    fun updateList(newList: List<ProductsModel>){
        productsList.clear()
        productsList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ProductsVH, position: Int) {
        Glide.with(requireContext).load(productsList[position].link).into(holder.ivwallpaper)
        holder.tvTitle.text=productsList[position].title
        holder.price.text="₹ "+productsList[position].price

        holder.itemView.setOnClickListener {
            val intent = Intent(requireContext, ProductDetailActivity::class.java)
            intent.putExtra("ProductId", productsList[position].id)
            intent.putExtra("ProductTitle", productsList[position].title)
            intent.putExtra("ProductPrice", productsList[position].price)
            intent.putExtra("Link", productsList[position].link)
            requireContext.startActivity(intent)
        }
    }
}