package com.philoSpocAgent.spocordersagent.presentation.ui


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.philoSpocAgent.spocordersagent.databinding.FragmentOrderDetailsBinding
import com.philoSpocAgent.spocordersagent.domain.model.OrderDetailsDomainModel
import com.philoSpocAgent.spocordersagent.domain.model.ProductDetailsDomainModel
import com.philoSpocAgent.spocordersagent.presentation.adapter.ProductsListAdapter
import com.philoSpocAgent.spocordersagent.presentation.viewmodel.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException


//private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


@AndroidEntryPoint
class OrderDetailsFragment : Fragment() {
    private var apiKey: String? = null
    private val list = arrayListOf<String?>()

    private var orderId: String? = null
    private var binding: FragmentOrderDetailsBinding? = null

    private lateinit var adapter: ProductsListAdapter

    private lateinit var ordersViewModel: OrdersViewModel
    private var response = OrderDetailsDomainModel(
        "", "", "", false, "", "", "", "", listOf(
            ProductDetailsDomainModel("", "", "", 0)
        ), ""
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            it.getStringArrayList(ARG_PARAM2)?.let { it1 ->
                list.addAll(it1)
                apiKey = list[0]
                orderId = list[1]
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Order Details"
        
        ordersViewModel = ViewModelProvider(this)[OrdersViewModel::class.java]
        binding = FragmentOrderDetailsBinding.inflate(layoutInflater)
        adapter = ProductsListAdapter()

        val fragment = MainFragment()
        fragment.hideNavRail(true)


        CoroutineScope(Dispatchers.IO).launch {

            getOrderDetails()

        }

        binding!!.orderDetailsSwipe.setOnRefreshListener {
            binding!!.progressBar.visibility =View.VISIBLE
            binding!!.wholeLayout.visibility = View.GONE
            binding?.constraintLayoutGre!!.visibility = View.GONE
            CoroutineScope(Dispatchers.IO).launch {
                getOrderDetails()
            }
            binding!!.orderDetailsSwipe.isRefreshing = false
        }


        return binding!!.root


    }


    private suspend fun getOrderDetails() {
        if (isOnline(activity!!)) {
            try {
                response = ordersViewModel.getOrderDetails(apiKey.toString(), orderId!!.toInt())
                withContext(Dispatchers.Main) {
                    binding!!.apply {
                        pharmacyNameTv.text = response.pharmacyName
                        orderDateTv.text = response.orderDate
                        distributorNameTv.text = response.distributorName
                        branchNameTv.text = response.branchName
                        pharmacyNameAtDistributorTv.text = response.pharmacyCode
                        if (response.expiryGoods) {
                            expiredGoodTv.text = "Yes"
                        } else {
                            expiredGoodTv.text = "No"
                        }
                        valueOfExpiredGoodsTv.text = response.valueOfExpiredGoods

                        println(response.productDetailsDomainModels)

                        adapter.submitList(response.productDetailsDomainModels.map { it.copy() }
                            .toMutableList())
                        binding?.productsRecyclerview!!.adapter = adapter
                        binding?.progressBar!!.visibility = View.GONE
                        binding?.wholeLayout!!.visibility = View.VISIBLE
                        binding?.constraintLayoutGre!!.visibility = View.VISIBLE
                    }
                }
            } catch (e: SocketTimeoutException) {
                withContext(Dispatchers.Main) {
                    binding?.progressBar!!.visibility = View.VISIBLE
                    binding?.wholeLayout!!.visibility = View.INVISIBLE
                    binding?.constraintLayoutGre!!.visibility = View.INVISIBLE
                    Toast.makeText(
                        activity!!.applicationContext,
                        "connection failure",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }


        } else {
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