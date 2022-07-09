package com.nevermindapp.newproject1.ui.rest.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nevermindapp.newproject1.R
import com.nevermindapp.newproject1.data.firebase.FirebaseSource
import com.nevermindapp.newproject1.data.repository.UserRepository
import com.nevermindapp.newproject1.databinding.FragmentOrdersBinding
import com.nevermindapp.newproject1.ui.ProductDetailViewModel
import com.zerowasteapp.zerowaste.adapters.OrderModel
import com.zerowasteapp.zerowaste.adapters.OrdersAdapter
import database.AppDatabase
import net.simplifiedcoding.ui.auth.ProductDetailViewModelFactory


class OrdersFragment : Fragment() {
    private var mContext: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        super.onAttach(requireActivity()!!)
        mContext = context
    }

    private var binding: FragmentOrdersBinding? = null
    private lateinit var factory: ProductDetailViewModelFactory
    private lateinit var viewModel: ProductDetailViewModel
    var first_date=""
    var second_date=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding= FragmentOrdersBinding.inflate(inflater,container,false)
        val view=binding!!.root
        //   return inflater.inflate(R.layout.fragment_orders, container, false)

        val toolbar: Toolbar = view.findViewById<View>(R.id.toolbar) as Toolbar
        setHasOptionsMenu(true);
        val activity = activity as AppCompatActivity?
        activity!!.setSupportActionBar(toolbar)



        val firebase= FirebaseSource()
        val repository= UserRepository(firebase)
        val db= AppDatabase.getDatabase(requireContext())
        factory=ProductDetailViewModelFactory(repository,db)
        viewModel= ViewModelProviders.of(this,factory).get(ProductDetailViewModel::class.java)


        binding!!.btnfromdate.setOnClickListener {
            // Initiation date picker with
            // MaterialDatePicker.Builder.datePicker()
            // and building it using build()
            val datePicker = MaterialDatePicker.Builder.dateRangePicker().setTheme(R.style.MaterialCalendarTheme).build()
            datePicker.show(requireActivity().supportFragmentManager, "DatePicker")

            // Setting up the event for when ok is clicked
            datePicker.addOnPositiveButtonClickListener {
                 first_date= it.first.toString()
                 second_date= it.second.toString()
                getOrdersDate(first_date,second_date)
            }

            // Setting up the event for when cancelled is clicked
            datePicker.addOnNegativeButtonClickListener {
              //  Toast.makeText(requireContext(), "${datePicker.headerText} is cancelled", Toast.LENGTH_LONG).show()
            }

            // Setting up the event for when back button is pressed
            datePicker.addOnCancelListener {
               // Toast.makeText(requireContext(), "Date Picker Cancelled", Toast.LENGTH_LONG).show()
            }
        }

        var layoutManager = LinearLayoutManager(requireContext())
        binding!!.rvorders.layoutManager=layoutManager


        val  FirebaseDb= FirebaseFirestore.getInstance()
        val userId= FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDb.collection("Users").document(userId).collection("Myorders")
            .addSnapshotListener { value, error ->
                val data = value?.toObjects(OrderModel::class.java)
                val adapter=OrdersAdapter(requireContext(), data!!)
                Log.d("listOrders",data.toString())
                binding!!.rvorders.adapter=adapter
            }
        return  view
    }




   override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu!!, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.all ->  getOrders("all")
            R.id.accepted -> getOrders("accepted")
            R.id.delivered -> getOrders("delivered")
            R.id.cancelled-> getOrders("isCancelled")
        }
        return super.onOptionsItemSelected(item)
    }




    private fun getOrders(s: String) {
        val  FirebaseDb= FirebaseFirestore.getInstance()
        val userId= FirebaseAuth.getInstance().currentUser!!.uid

        if (first_date.isNullOrBlank()) {
            if (s.equals("all")) {
                FirebaseDb.collection("Users").document(userId).collection("Myorders")
                    .addSnapshotListener { value, error ->
                        val data = value?.toObjects(OrderModel::class.java)
                        val adapter = OrdersAdapter(mContext!!, data!!)
                        binding!!.rvorders.adapter = adapter
                    }
            } else {
                FirebaseDb.collection("Users").document(userId).collection("Myorders")
                    .whereEqualTo(s, true)
                    .addSnapshotListener { value, error ->
                        val data = value?.toObjects(OrderModel::class.java)
                        val adapter = OrdersAdapter(mContext!!, data!!)
                        binding!!.rvorders.adapter = adapter
                    }
            }
        }else{
            if (s.equals("all")) {
                FirebaseDb.collection("Users").document(userId).collection("Myorders")
                      .whereGreaterThan("order_date",first_date)
                .whereLessThan("order_date",second_date)
                    .addSnapshotListener { value, error ->
                        val data = value?.toObjects(OrderModel::class.java)
                      attachAdapter(data)
                    }
            } else {
                FirebaseDb.collection("Users").document(userId).collection("Myorders")
                    .whereEqualTo(s, true)
                     .whereGreaterThan("order_date",first_date)
                .whereLessThan("order_date",second_date)
                    .addSnapshotListener { value, error ->
                        val data = value?.toObjects(OrderModel::class.java)
                        attachAdapter(data)
                    }
            }
        }
    }


    private fun getOrdersDate(d1: String,d2:String) {
        val  FirebaseDb= FirebaseFirestore.getInstance()
        val userId= FirebaseAuth.getInstance().currentUser!!.uid
            FirebaseDb.collection("Users").document(userId).collection("Myorders")
                .whereGreaterThan("order_date",d1)
                .whereLessThan("order_date",d2)
                .addSnapshotListener { value, error ->
                    val data = value?.toObjects(OrderModel::class.java)
                    attachAdapter(data)
                }
        }


    fun attachAdapter(data: MutableList<OrderModel>?){
        val adapter= mContext?.let { OrdersAdapter(it, data!!) }
        binding!!.rvorders.adapter=adapter
    }



}


