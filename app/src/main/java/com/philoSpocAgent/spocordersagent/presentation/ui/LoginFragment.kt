package com.philoSpocAgent.spocordersagent.presentation.ui


import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.philoSpocAgent.spocordersagent.R
import com.philoSpocAgent.spocordersagent.databinding.FragmentLoginBinding

import com.philoSpocAgent.spocordersagent.domain.model.LoginDomainModel
import com.philoSpocAgent.spocordersagent.domain.model.LoginResponseDomainModel
import com.philoSpocAgent.spocordersagent.presentation.viewmodel.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"




@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var emailPattern:String = "[a-zA-Z0-9._-]+@[a-z]+[.]+[a-z]+"
    private  var binding: FragmentLoginBinding? = null
    private lateinit var ordersViewModel: OrdersViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ordersViewModel = ViewModelProvider(this)[OrdersViewModel::class.java]
        binding = FragmentLoginBinding.inflate(layoutInflater)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding!!.loginButton.setOnClickListener {
            hideSoftKeyboard(activity!!)
            binding!!.emailLayout.helperText = ""
            binding!!.passwordLayout.helperText = ""

            val isValidInput = validateLogin(
                binding!!.emailTextField.text.toString(),
                binding!!.passwordTextField.text.toString()
            )
            CoroutineScope(Dispatchers.IO).launch {
                if (isValidInput) {
                    val loginResponse = login(
                        activity!!,
                        binding!!.emailTextField.text.toString(),
                        binding!!.passwordTextField.text.toString()
                    )

                    if (loginResponse.isSuccess) {
                        goToOrdersListScreen(loginResponse)
                    }
                }


            }

        }
        return binding!!.root
    }

    private suspend fun goToOrdersListScreen(response: LoginResponseDomainModel) {

        if (response.isSuccess) {

            val bundle = Bundle()
            val list = arrayListOf<String>()
            list.addAll(listOf(response.message, response.data))
            bundle.putStringArrayList(ARG_PARAM1, list)
            withContext(Dispatchers.Main) {
                findNavController().navigate(
                    R.id.action_loginFragment_to_mainFragment,
                    bundle
                )
            }
        }


    }

    private suspend fun login(
        context: Context,
        email: String,
        password: String
    ): LoginResponseDomainModel {
        var response = LoginResponseDomainModel(false, "", "")




        if (isOnline(context)) {

            withContext(Dispatchers.Main) {
                binding!!.loginButton.isEnabled = false
            }
            try {
                response = ordersViewModel.logIn(
                    LoginDomainModel(
                        email,
                        password,
                    )
                )
                if (!response.isSuccess) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            activity!!.applicationContext,
                            "Invalid Access",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding!!.loginButton.isEnabled = true
                    }

                }


            } catch (e: SocketTimeoutException) {

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        activity!!.applicationContext,
                        "Connection failure",
                        Toast.LENGTH_SHORT
                    ).show()

                    binding!!.loginButton.isEnabled = true
                }


            }


        } else {
            withContext(Dispatchers.Main){
                binding!!.loginButton.isEnabled = true

                Toast.makeText(
                    activity!!.applicationContext,
                    "No Internet",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }
        return response
    }


    private fun validateLogin(emailText: String, passwordText: String): Boolean {
        if (emailText.isEmpty() || !(emailText.trim().matches(emailPattern.toRegex()))) {


            binding!!.emailLayout.helperText = "Invalid E-mail Address"

            return false

        } else if (passwordText.isEmpty() || passwordText.length < 8) {


            binding!!.passwordLayout.helperText = "Password must be at least 8 characters"

            return false


        }
        return true


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

    private fun hideSoftKeyboard(activity: Activity) {
        val view = activity.currentFocus
        if(view != null){
            val inputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager

            inputMethodManager?.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }

    }

}