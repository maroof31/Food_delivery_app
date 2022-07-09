package com.nevermindapp.newproject1.ui.rest.fragments

import adapters.CartItemsAdapters
import adapters.ClickListener
import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nevermindapp.newproject1.R
import com.nevermindapp.newproject1.data.firebase.FirebaseSource
import com.nevermindapp.newproject1.data.repository.UserRepository
import com.nevermindapp.newproject1.databinding.FragmentCartBinding
import com.nevermindapp.newproject1.ui.ProductDetailViewModel
import com.nevermindapp.newproject1.ui.rest.PaymentListener
import com.razorpay.Checkout
import com.zerowasteapp.zerowaste.adapters.OrderModel
import database.AppDatabase
import database.CartModel
import net.simplifiedcoding.ui.auth.ProductDetailViewModelFactory
import org.json.JSONException
import org.json.JSONObject


class Fragment_cart : Fragment(), ClickListener,PaymentListener{
    lateinit var progressdialog: ProgressDialog
    private lateinit var factory: ProductDetailViewModelFactory
    private lateinit var viewModel: ProductDetailViewModel
    val PAY_NOW="Pay Now"
    val COD="Cash on Delivery"
    var totalprice:Int=0
    private var binding: FragmentCartBinding? = null


    var name=""
    var mobile=""
    var area=""
    var city=""
    var state=""
    var pincode=""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
         ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_cart, container, false)
        binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding!!.root


        progressdialog = ProgressDialog(requireContext())
        val firebase= FirebaseSource()
        val repository= UserRepository(firebase)
        val db=AppDatabase.getDatabase(requireContext())
        factory=ProductDetailViewModelFactory(repository,db)
        viewModel= ViewModelProviders.of(this,factory).get(ProductDetailViewModel::class.java)


        var layoutManager = LinearLayoutManager(requireContext())
        binding!!.rvcartview.layoutManager=layoutManager
        val adapter = CartItemsAdapters(requireContext(),this)
        binding!!.rvcartview.adapter=adapter

        viewModel.products.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
            totalprice=0
            calculateTotal(it)
        })



        Log.e("totalprice",totalprice.toString()+"ss")

        binding!!.btncheckout.setOnClickListener {
            var list= viewModel.products.value
            var orderDetails=getOrderDetails(list)
            showDialog(orderDetails,totalprice)
        }

        return view
    }



    private fun showDialog(orderDetails: String, totalAmount: Int) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.address_details)

        val yesBtn = dialog.findViewById(R.id.btnproceed) as Button


        val textField=dialog.findViewById<AutoCompleteTextView>(R.id.datesFilterSpinner)
        val items = listOf( "Pay Now","Cash on Delivery")
        val adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, items)
        textField.setAdapter(adapter)


        yesBtn.setOnClickListener {
           // dialog.dismiss()

            val ename=dialog.findViewById<EditText>(R.id.edtName)
            val emobile=dialog.findViewById<EditText>(R.id.edtmobile)
            val earea=dialog.findViewById<EditText>(R.id.edtarea)
            val estate=dialog.findViewById<EditText>(R.id.edtstate)
            val ecity=dialog.findViewById<EditText>(R.id.edtcity)
            val epincode=dialog.findViewById<EditText>(R.id.edtpincode)

            name= ename.text.toString()
            mobile= emobile.text.toString()
            area= earea.text.toString()
            state=estate.text.toString()
            city=ecity.text.toString()
            pincode=epincode.text.toString()

            val selectedValue: String = textField.text.toString()

            if (name.isNullOrBlank() || mobile.length<10 || area.isNullOrBlank() || state.isNullOrBlank() || city.isNullOrBlank() ||
                !pincode.isDigitsOnly() || pincode.isNullOrBlank()){
                showtoast("Enter correct details")
            }else{
                if (selectedValue.isNullOrBlank()){
                    showtoast("select payment mode")
                }else{
                    if (selectedValue.equals(PAY_NOW)){
                        /**
                         *  start razor pay
                         */
                        startPayment(totalprice.toString())
                    }else if (selectedValue.equals(COD)){
                        /**
                         * place order now
                         */

                        dialog.dismiss()
                        progressdialog!!.setMessage("Placing order")
                        progressdialog!!.setCancelable(false)
                        progressdialog!!.show()

                        viewModel.placeOrder(name,mobile,area+" "+city,pincode,orderDetails,totalAmount,false,false,"COD")
                            .addOnSuccessListener {
                            viewModel.savetoAdmin().addOnSuccessListener {
                                progressdialog.setMessage("Order Placed Successfully")
                                Handler(Looper.getMainLooper()).postDelayed(
                                    {
                                        // This method will be executed once the timer is over
                                     progressdialog.dismiss()
                                    },
                                    2000 // value in milliseconds
                                )
                          }
                      }
                          .addOnFailureListener {
                              progressdialog!!.dismiss()
                              showtoast("failed to place order")
                          }

                    }
                }

            }

           Log.e("nameedt"+ selectedValue,ename.text.toString())



        }



        dialog.show()

    }

    private fun showtoast(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }


    override fun deleteproduct(item: CartModel){
        viewModel.deleteProduct(item)
    }

    override fun minusClick(item: CartModel) {
        if(item.quantity>1){
            viewModel.decreaseqtyundb(item)
            updatePrice(-item.price.toInt())
        }
    }

    override fun plusClick(item: CartModel) {
        viewModel.increaseqtyindb(item)
        updatePrice(item.price.toInt())
    }

    fun updatePrice(vlue: Int) {
        totalprice+=vlue
        binding!!.totalFeeTxt.text=totalprice.toString()
        binding!!.totalTxt.text=totalprice.toString()
    }

   fun  calculateTotal(list: List<CartModel>): Int {
       list.forEach {
           totalprice+= it.price.trim().toInt()*it.quantity
           Log.e("price",it.price + totalprice.toString())
           binding!!.totalFeeTxt.text=totalprice.toString()
           binding!!.totalTxt.text=totalprice.toString()
       }
      return totalprice
    }

    fun startPayment(amount:String){
        var samount="100"
        var checkout= Checkout()
        checkout.setKeyID("rzp_test_BFCRdt88wxDuLr")

        var objectt= JSONObject()

        try{
            objectt.put("name","maroof ansari")
            objectt.put("description","Test payment")
            objectt.put("currency","INR")
            objectt.put("amount",samount.toInt())

            checkout.open(requireActivity(),objectt)

        }catch(e: JSONException){
            e.printStackTrace()
        }
    }

    override fun onSuccess() {
        
    }

    override fun onFailure() {

    }


    fun getOrderDetails(list: List<CartModel>?): String {
        var order_details=""
        var sb=StringBuilder()
            for(i in list!!){
                sb.append("product :"+ i.title+" ₹${i.price} * ${i.quantity} ,\n")
             order_details=sb.toString()
            Log.e("order details", order_details)
        }
        return order_details
    }




}