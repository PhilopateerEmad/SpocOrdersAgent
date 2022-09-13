package com.philoSpocAgent.spocordersagent.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigationrail.NavigationRailView
import com.philoSpocAgent.spocordersagent.R
import com.philoSpocAgent.spocordersagent.databinding.FragmentMainBinding

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_PARAM1 = "param1"


private var binding: FragmentMainBinding? = null

@AndroidEntryPoint
class MainFragment : Fragment() {


    private var apiKey: String? = null
    private val list = arrayListOf<String?>()
    private var agentId: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            it.getStringArrayList(ARG_PARAM1)?.let { it1 -> list.addAll(it1) }
            apiKey = list[0]
            agentId = list[1]
        }


    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentMainBinding.inflate(layoutInflater)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {}
        val navigationRail: NavigationRailView = binding!!.navigationRail

        println(navigationRail.selectedItemId)

        CoroutineScope(Dispatchers.IO).launch {

            withContext(Dispatchers.Main) {

                val bundle = Bundle()
                bundle.putStringArrayList(ARG_PARAM1, list)

                binding!!.fragmentContainerView3.findNavController().navigate(R.id.ordersListFragment, bundle)

            }
        }
        navigationRail.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.orders_list -> {
                    val bundle = Bundle()
                    bundle.putStringArrayList(ARG_PARAM1, list)
                    binding!!.fragmentContainerView3.findNavController()
                        .navigate(R.id.ordersListFragment, bundle)
                    true
                }

                R.id.new_order->{
                    val bundle = Bundle()
                    bundle.putStringArrayList(ARG_PARAM1, list)
                    binding!!.fragmentContainerView3.findNavController()
                        .navigate(R.id.newOrderFragment, bundle)
                    true
                }

                else -> {
                    false
                }
            }


        }



        return binding!!.root
    }


    fun hideNavRail(hide: Boolean){
        if(hide){
            binding!!.navigationRail.visibility = View.GONE
        }
        else{
            binding!!.navigationRail.visibility = View.VISIBLE
        }

    }


}