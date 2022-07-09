package com.nevermindapp.newproject1.ui.rest.fragments

import adapters.CatagoryAdapter
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.nevermindapp.newproject1.R
import com.nevermindapp.newproject1.adapters.SliderAdapterExample
import com.nevermindapp.newproject1.data.firebase.FirebaseSource
import com.nevermindapp.newproject1.data.repository.UserRepository
import com.nevermindapp.newproject1.databinding.FragmentHomeBinding
import com.nevermindapp.newproject1.models.SliderItem
import com.nevermindapp.newproject1.ui.auth.session.UserSessionUtils
import com.nevermindapp.newproject1.ui.rest.MainViewModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import net.simplifiedcoding.ui.auth.MainViewModelFactory


class HomeFragment : Fragment() {
    private lateinit var factory:MainViewModelFactory
    lateinit var adapterr:SliderAdapterExample
private lateinit var viewModel:MainViewModel

    private var binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding!!.root




        val util: UserSessionUtils = UserSessionUtils(requireContext())
        val firebase= FirebaseSource()
        val repository=UserRepository(firebase)
        factory=MainViewModelFactory(repository,util)
        viewModel= ViewModelProviders.of(this,factory).get(MainViewModel::class.java)


        if (requireContext()!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding!!.rvcategories.layoutManager=(LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false))
        } else {
            binding!!.rvcategories.layoutManager=(LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false))
        }
        var adapter = CatagoryAdapter(requireContext())
        binding!!.rvcategories.adapter=adapter

        viewModel.getCategories()
        viewModel.categories.observe(viewLifecycleOwner, Observer {
            Log.e("catlistsize","${it.size}  + 00")
            adapter.updateList(it)
        })


        val sliderView=view.findViewById<SliderView>(R.id.imageSlider)
         adapterr = SliderAdapterExample(requireContext())
        sliderView.setSliderAdapter(adapterr)
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!

        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH)
        sliderView.setIndicatorSelectedColor(Color.WHITE)
        sliderView.setIndicatorUnselectedColor(Color.GRAY)
        sliderView.setScrollTimeInSec(3)
        sliderView.setAutoCycle(true)
        sliderView.startAutoCycle()
        renewItems(sliderView)



        sliderView.setOnIndicatorClickListener(DrawController.ClickListener {
            Log.i(
                "GGG",
                "onIndicatorClicked: " + sliderView.getCurrentPagePosition()
            )
        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    fun renewItems(view: View?) {
        val sliderItemList: MutableList<SliderItem> = ArrayList()
        //dummy data
        for (i in 0..4) {
            val sliderItem = SliderItem()

            if (i % 2 == 0) {
                sliderItem.imageUrl =
                    "https://firebasestorage.googleapis.com/v0/b/ecomex-f148e.appspot.com/o/slider%2F0.89399200_1551782137_fast1.jpg?alt=media&token=c16da34c-6748-43bf-a574-98ce3daed818"
            } else {
                sliderItem.imageUrl =
                    "https://firebasestorage.googleapis.com/v0/b/ecomex-f148e.appspot.com/o/slider%2F1562744505809.jpg?alt=media&token=cb35093d-4773-4183-bf7c-552cac4125ce"
            }
            sliderItemList.add(sliderItem)
        }
        adapterr.renewItems(sliderItemList)
    }

    fun removeLastItem(view: View?) {
        if (adapterr.getCount() - 1 >= 0) adapterr.deleteItem(adapterr.getCount() - 1)
    }

    fun addNewItem(view: View?) {
        val sliderItem = SliderItem()
        sliderItem.description = "Slider Item Added Manually"
        sliderItem.imageUrl =
            "https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
        adapterr.addItem(sliderItem)
    }


}