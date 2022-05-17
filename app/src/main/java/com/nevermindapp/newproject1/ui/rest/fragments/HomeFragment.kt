package com.nevermindapp.newproject1.ui.rest.fragments

import adapters.CatagoryAdapter
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.nevermindapp.newproject1.data.firebase.FirebaseSource
import com.nevermindapp.newproject1.data.repository.UserRepository
import com.nevermindapp.newproject1.databinding.FragmentHomeBinding
import com.nevermindapp.newproject1.ui.auth.session.UserSessionUtils
import com.nevermindapp.newproject1.ui.rest.MainViewModel
import net.simplifiedcoding.ui.auth.MainViewModelFactory


class HomeFragment : Fragment() {
    private lateinit var factory:MainViewModelFactory
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
            binding!!.rvcategories.layoutManager=(GridLayoutManager(requireContext(), 2))
        } else {
            binding!!.rvcategories.layoutManager=(GridLayoutManager(requireContext(), 4))
        }

        val adapter = CatagoryAdapter(requireContext())
        binding!!.rvcategories.adapter=adapter

        viewModel.getCategories()
        viewModel.categories.observe(viewLifecycleOwner, Observer {
            Log.e("catlistsize","${it.size}  + 00")
            adapter.updateList(it)
        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


}