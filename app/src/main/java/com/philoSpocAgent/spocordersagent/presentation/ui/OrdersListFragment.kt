package com.philoSpocAgent.spocordersagent.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.philoSpocAgent.spocordersagent.R
import com.philoSpocAgent.spocordersagent.databinding.FragmentMyOrdersListBinding
import com.philoSpocAgent.spocordersagent.domain.model.OrderDomainModel
import com.philoSpocAgent.spocordersagent.presentation.adapter.OrdersListAdapter
import com.philoSpocAgent.spocordersagent.presentation.viewmodel.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
@SuppressLint("StaticFieldLeak")
private  var binding: FragmentMyOrdersListBinding? = null

@AndroidEntryPoint
class OrdersListFragment : Fragment(), OrdersListAdapter.ItemClickListener{
    private var apiKey: String? = null
    private val list = arrayListOf<String?>()
    private var agentId: String? = null
    private lateinit var adapter: OrdersListAdapter


    private var response = mutableListOf(OrderDomainModel("","",""))

    private lateinit var ordersViewModel: OrdersViewModel

    private var itemsList = mutableListOf<OrderDomainModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { it.getStringArrayList(ARG_PARAM1)?.let { it1 -> list.addAll(it1) }
            apiKey = list[0]
            agentId = list[1]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ordersViewModel = ViewModelProvider(this)[OrdersViewModel::class.java]
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "My Orders List"
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {}
        binding = FragmentMyOrdersListBinding.inflate(layoutInflater)

        adapter = OrdersListAdapter(this)

       val  fragment = MainFragment()
        fragment.hideNavRail(false)

        CoroutineScope(Dispatchers.IO).launch{

            getOrders()

        }

        binding!!.swipeOrders.setOnRefreshListener {

            binding!!.progressBar2.visibility =View.VISIBLE
            binding!!.ordersRecyclerview.visibility = View.GONE
            CoroutineScope(Dispatchers.IO).launch {
                getOrders()
            }
            binding!!.swipeOrders.isRefreshing = false

        }

        binding!!.ordersRecyclerview.setOnScrollChangeListener { view, _, _, _, _ ->
            binding!!.swipeOrders.isEnabled = view.scrollY == 0

        }



        return binding!!.root
    }

    override fun orderItem(orderId:String) {
        val bundle = Bundle()
        val list = arrayListOf<String>()
        list.addAll(listOf(apiKey.toString(),orderId))
        bundle.putStringArrayList(ARG_PARAM2,list)
        findNavController().navigate(R.id.action_ordersListFragment_to_orderDetailsFragment,bundle)

    }

    private suspend fun getOrders() {

        if (isOnline(activity!!)) {
            try {
                response = ordersViewModel.getOrdersList(
                    apiKey ?: "0",
                    agentId?.toIntOrNull() ?: 0
                ) as MutableList<OrderDomainModel>

            } catch (e: SocketTimeoutException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        activity!!.applicationContext,
                        "connection failure",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }


            itemsList.clear()


            for (data in response.indices) {
                itemsList.add(data, response[data])

            }


            withContext(Dispatchers.Main) {
                adapter.submitList(itemsList.map { it.copy() }.toMutableList())
                binding?.ordersRecyclerview!!.adapter = adapter
                binding?.progressBar2!!.visibility = View.GONE
                binding?.ordersRecyclerview!!.visibility = View.VISIBLE
            }
        }
        else{
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    activity!!.applicationContext,
                    "No Internet",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
        return false
    }



}