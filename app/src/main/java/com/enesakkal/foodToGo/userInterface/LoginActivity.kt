package com.enesakkal.foodToGo.userInterface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.enesakkal.foodToGo.R
import com.enesakkal.foodToGo.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity: Fragment() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
     private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = ActivityLoginBinding.inflate(inflater, container, false)
        auth = Firebase.auth


        return binding.root
    }


    fun onSignUp(view : View) {
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (email.isEmpty() && password.isEmpty()) {
                Toast.makeText(requireActivity(),"enter your password",Toast.LENGTH_LONG).show() //requireActivity is needed if you use fragment.
            }else {
                auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                    //Success
                    findNavController().navigate(R.id.action_loginActivity_to_recipiesFragment)
                }.addOnFailureListener {
                   Toast.makeText(requireActivity(),it.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }



    }

    fun onLogin (view : View) {

    }



}