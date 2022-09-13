package com.philoSpocAgent.spocordersagent.presentation.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.toLowerCase
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.philoSpocAgent.spocordersagent.R
import com.philoSpocAgent.spocordersagent.databinding.FragmentNewOrderBinding
import com.philoSpocAgent.spocordersagent.domain.model.*
import com.philoSpocAgent.spocordersagent.presentation.adapter.ProductsSimpleListAdapter
import com.philoSpocAgent.spocordersagent.presentation.viewmodel.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.util.*


private const val ARG_PARAM1 = "param1"


@AndroidEntryPoint
class NewOrderFragment : Fragment() {
    private var binding: FragmentNewOrderBinding? = null
    private lateinit var ordersViewModel: OrdersViewModel
    private var apiKey: String? = null
    private val list = arrayListOf<String?>()
    private var agentId: String? = null
    private lateinit var response: PharmaDistributorsProductsListsDomainModel

    private lateinit var pharmaciesAdapter: ArrayAdapter<PharmacyDetailsDomainModel>
    private lateinit var distributorsAdapter: ArrayAdapter<DistributorDetailsDomainModel>
    private lateinit var productsAdapter: ArrayAdapter<ProductSimpleDomainModel>
    private lateinit var productsListAdapter: ProductsSimpleListAdapter
    private var selectedProductsList = mutableListOf<ProductNameQuantityDomainModel>()
    private var selectedProductValue: ProductSimpleDomainModel? = null
    private var selectedPharmacyValue: PharmacyDetailsDomainModel? = null
    private var selectedDistributorValue: DistributorDetailsDomainModel? = null
    private val pharmaciesList = mutableListOf<String>()
    private val distributorsList = mutableListOf<String>()
    private val productsList = mutableListOf<String>()
    private var newOrder: AddOrderDomainModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            it.getStringArrayList(ARG_PARAM1)?.let { it1 -> list.addAll(it1) }
            apiKey = list[0]
            agentId = list[1]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ordersViewModel = ViewModelProvider(this)[OrdersViewModel::class.java]
        binding = FragmentNewOrderBinding.inflate(layoutInflater)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Add New Order"
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {}
        productsListAdapter = ProductsSimpleListAdapter()
        binding!!.productsRecyclerview.adapter = productsListAdapter
        CoroutineScope(Dispatchers.IO).launch {
            getLists()
        }


        binding!!.distributorNameTv.setOnFocusChangeListener { _, b ->
            if (!b && (!(distributorsList.contains(binding!!.distributorNameTv.text.toString())))) {
                binding!!.distributorNameTv.setText("")
            }
        }
        binding!!.productNameTv.setOnFocusChangeListener { _, b ->
            if (!b && (!(productsList.contains(binding!!.productNameTv.text.toString())))) {
                binding!!.productNameTv.setText("")
            }
        }
        binding!!.expiredGoodTv.setOnFocusChangeListener { _, b ->
            if (!b && (!(listOf("Yes".lowercase(Locale.getDefault()),"No".lowercase(Locale.getDefault())).contains(binding!!.expiredGoodTv.text.toString().lowercase(Locale.getDefault()))))) {
                binding!!.expiredGoodTv.setText("")
            }
        }

        binding!!.expiredGoodOutline.setOnClickListener {
            hideSoftKeyboard(activity!!)
        }
        binding!!.expiredGoodTv.setOnClickListener {
            hideSoftKeyboard(activity!!)
        }

        binding!!.saveProduct.setOnClickListener {
            val productId = selectedProductValue!!.productId
            val productName = selectedProductValue!!.productName
            val productQuantity = binding!!.productQuantityTv.text.toString()
            binding!!.productNameOutline.helperText = ""
            binding!!.productQuantityOutline.helperText =""
            hideSoftKeyboard(activity!!)

            if (binding!!.productNameTv.text.toString() == "") {
                binding!!.productNameOutline.helperText = "add product"
            } else {
                if (binding!!.productQuantityTv.text.toString() == "") {
                    binding!!.productQuantityOutline.helperText ="add quantity"
                } else {


                    CoroutineScope(Dispatchers.IO).launch {
                        selectedProductsList.add(ProductNameQuantityDomainModel(productId,productName,productQuantity))
                        withContext(Dispatchers.Main) {
                            productsListAdapter.submitList(selectedProductsList.map { it.copy() }.toMutableList())
                            binding!!.productQuantityTv.setText("")
                            binding!!.productNameTv.setText("")
                            binding!!.productQuantityTv.clearFocus()
                            binding!!.productNameTv.clearFocus()
                        }

                    }


                }
            }

        }

        binding!!.saveOrder.setOnClickListener {

            binding!!.pharmacyNameOutline.helperText = ""
            binding!!.distributorNameOutline.helperText = ""
            binding!!.branchNameOutline.helperText = ""
            binding!!.pharmacyNameAtDistributorOutline.helperText = ""
            binding!!.expiredGoodOutline.helperText = ""
            binding!!.valueOfExpiredGoodsOutline.helperText = ""
            binding!!.productNameOutline.helperText = ""


            if (binding!!.pharmacyNameTv.text.toString() == "") {
                binding!!.pharmacyNameOutline.helperText = "*choose pharmacy"
            } else {
                if (binding!!.distributorNameTv.text.toString() == "") {
                    binding!!.distributorNameOutline.helperText = "*choose distributor"
                } else {
                    if (binding!!.branchNameTv.text.toString() == "") {
                        binding!!.branchNameOutline.helperText = "*type branch name"
                    } else {
                        if (binding!!.pharmacyNameAtDistributorTv.text.toString() == "") {
                            binding!!.pharmacyNameAtDistributorOutline.helperText =
                                "*choose pharmacy code"
                        } else {
                            if (binding!!.expiredGoodTv.toString() == "") {
                                binding!!.expiredGoodOutline.helperText = "*choose Yes or No"
                            } else {
                                if (binding!!.valueOfExpiredGoodsTv.text.toString() == "") {
                                    binding!!.valueOfExpiredGoodsOutline.helperText =
                                        "*type expired goods value"
                                } else {
                                    if (selectedProductsList.isEmpty()) {
                                        binding!!.productNameOutline.helperText =
                                            "*add some products"
                                    } else {


                                        binding!!.apply {

                                            newOrder = AddOrderDomainModel(
                                                selectedPharmacyValue!!.id,
                                                selectedDistributorValue!!.id,
                                                agentId!!.toInt(),
                                                branchNameTv.text.toString(),
                                                pharmacyNameAtDistributorTv.text.toString(),
                                                expiredGoodTv.text.toString() == "Yes",
                                                valueOfExpiredGoodsTv.text.toString(),
                                                selectedProductsList,
                                            )
                                        }
                                        val alertDialogBuilder = AlertDialog.Builder(context)
                                        alertDialogBuilder.setMessage("Are Yoy Sure You Want to Submit this Order?")
                                            .setCancelable(false)
                                            .setPositiveButton("Yes") { _, _ ->
                                                CoroutineScope(Dispatchers.IO).launch {

                                                    addNewOrder()

                                                }
                                            }
                                            .setNegativeButton(
                                                "No"
                                            ) { _, _ -> //  Action for 'NO' Button
                                            }

                                        val alert: AlertDialog = alertDialogBuilder.create()
                                        alert.setTitle("Submit Order")
                                        alert.show()



                                    }
                                }
                            }
                        }
                    }
                }
            }


        }

        binding!!.cancelOrder.setOnClickListener {

            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setMessage("Are Yoy Sure You Want to Cancel this Order?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    clearOrder()
                }
                .setNegativeButton(
                    "No"
                ) { _, _ -> //  Action for 'NO' Button
                }

            val alert: AlertDialog = alertDialogBuilder.create()
            alert.setTitle("Cancel Order")
            alert.show()

        }

        binding!!.newOrderSwipe.setOnRefreshListener {

            binding!!.progressBar.visibility =View.VISIBLE
            binding!!.rightLayout.visibility = View.GONE
            binding!!.leftLayout!!.visibility = View.GONE
            CoroutineScope(Dispatchers.IO).launch {
                getLists()
            }
            binding!!.newOrderSwipe.isRefreshing = false
        }
        return binding!!.root
    }



    private fun clearOrder() {
        binding!!.pharmacyNameTv.setText("")
        binding!!.distributorNameTv.setText("")
        binding!!.branchNameTv.setText("")
        binding!!.pharmacyNameAtDistributorTv.setText("")
        binding!!.expiredGoodTv.setText("")
        binding!!.valueOfExpiredGoodsTv.setText("")
        binding!!.productNameTv.setText("")
        binding!!.productQuantityTv.setText("")
        selectedProductsList.clear()
        productsListAdapter.submitList(emptyList())
    }

    private fun hideSoftKeyboard(activity: Activity) {
        val view = activity.currentFocus
        if(view != null){
            val inputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager

            inputMethodManager?.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }

    }


    private suspend fun addNewOrder() {
        if (isOnline(activity!!)) {
            try {
                ordersViewModel.addNewOrder(apiKey!!, newOrder!!)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        activity!!.applicationContext,
                        "Added Successfully",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                    clearOrder()

                }


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

    private suspend fun getLists() {
        if (isOnline(activity!!)) {
            try {
                response = ordersViewModel.getPharmaciesDistributorsProductsLists(apiKey!!,agentId!!.toInt())
                withContext(Dispatchers.Main) {
                }

                for (data in response.pharmaciesList.indices) {
                    response.pharmaciesList[data].name.let { pharmaciesList.add(data, it) }
                }

                for (data in response.distributorsList.indices) {
                    response.distributorsList[data].name.let { distributorsList.add(data, it) }
                }
                for (data in response.productsList.indices) {
                    response.productsList[data].productName.let { productsList.add(data, it) }
                }


                withContext(Dispatchers.Main) {
                    binding!!.progressBar.visibility = View.INVISIBLE
                    binding!!.leftLayout.visibility = View.VISIBLE
                    binding!!.rightLayout.visibility = View.VISIBLE

                    pharmaciesAdapter = ArrayAdapter<PharmacyDetailsDomainModel>(
                        requireContext(),
                        R.layout.list_item,
                        response.pharmaciesList
                    )
                    (binding!!.pharmacyNameTv as? AutoCompleteTextView)?.setAdapter(pharmaciesAdapter)

                    distributorsAdapter = ArrayAdapter<DistributorDetailsDomainModel>(
                        requireContext(),
                        R.layout.list_item,
                        response.distributorsList
                    )
                    (binding!!.distributorNameTv as? AutoCompleteTextView)?.setAdapter(
                        distributorsAdapter
                    )

                    productsAdapter = ArrayAdapter<ProductSimpleDomainModel>(
                        requireContext(),
                        R.layout.list_item,
                        response.productsList
                    )
                    (binding!!.productNameTv as? AutoCompleteTextView)?.setAdapter(productsAdapter)

                    val expiredGoodAdapter =
                        ArrayAdapter(requireContext(), R.layout.list_item, listOf("Yes", "No"))
                    (binding!!.expiredGoodTv as? AutoCompleteTextView)?.setAdapter(expiredGoodAdapter)


                    (binding!!.pharmacyNameTv as? AutoCompleteTextView)?.onItemClickListener =
                        OnItemClickListener { _, _, position, _ ->
                            selectedPharmacyValue = pharmaciesAdapter.getItem(position)
                            hideSoftKeyboard(activity!!)
                            binding!!.pharmacyNameTv.clearFocus()

                        }

                    (binding!!.distributorNameTv as? AutoCompleteTextView)?.onItemClickListener =
                        OnItemClickListener { _, _, position, _ ->
                            selectedDistributorValue =
                                distributorsAdapter.getItem(position)
                            hideSoftKeyboard(activity!!)
                            binding!!.distributorNameTv.clearFocus()

                        }

                    (binding!!.productNameTv as? AutoCompleteTextView)?.onItemClickListener =
                        OnItemClickListener { _, _, position, _ ->
                            selectedProductValue = productsAdapter.getItem(position)
                            hideSoftKeyboard(activity!!)
                            binding!!.productNameTv.clearFocus()

                        }

                    binding!!.pharmacyNameTv.setOnFocusChangeListener { _, b ->
                        if (!b && (!(pharmaciesList.contains(binding!!.pharmacyNameTv.text.toString())))) {
                            binding!!.pharmacyNameTv.setText("")
                        }
                    }
                }
            } catch (e: SocketTimeoutException) {
                withContext(Dispatchers.Main) {
                    binding?.progressBar!!.visibility = View.VISIBLE
                    binding!!.leftLayout.visibility = View.INVISIBLE
                    binding!!.rightLayout.visibility = View.INVISIBLE


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







