package adapters

import Models.CatagoryModel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nevermindapp.newproject1.R
import com.nevermindapp.newproject1.ui.rest.ProductsActivity
import database.CartModel


class CatagoryAdapter (val requireContext: Context) :
    RecyclerView.Adapter<CatagoryAdapter.CatagoryVH>() {

    private val listCatagories=ArrayList<CatagoryModel>()


    inner class CatagoryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivwallpaper = itemView.findViewById<ImageView>(R.id.categoryPic)
        val tvCatagory = itemView.findViewById<TextView>(R.id.categoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatagoryVH {
        return CatagoryVH(
            LayoutInflater.from(requireContext).inflate(R.layout.item_category_new, parent, false)
        )
    }


    override fun getItemCount(): Int {
        return listCatagories.size
    }

    fun updateList(newList: List<CatagoryModel>){
        listCatagories.clear()
        listCatagories.addAll(newList)

        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: CatagoryVH, position: Int) {
        Glide.with(requireContext).load(listCatagories[position].link).into(holder.ivwallpaper)
        holder.tvCatagory.text = listCatagories[position].name

        holder.itemView.setOnClickListener {
            val intent = Intent(requireContext, ProductsActivity::class.java)
            intent.putExtra("categoryId", listCatagories[position].id)
            Log.d("passedid",listCatagories[position].id)
            intent.putExtra("categoryName", listCatagories[position].name)
            Log.d("passedname",listCatagories[position].name)
            requireContext.startActivity(intent)
        }
    }
}