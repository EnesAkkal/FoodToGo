package com.enesakkal.foodToGo.userInterface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.enesakkal.foodToGo.R
import com.enesakkal.foodToGo.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment: Fragment() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = ActivityLoginBinding.inflate(inflater, container, false)
        auth = Firebase.auth

        binding.loginButton.setOnClickListener {
            val email = binding.emailText.text.toString()
            val password = binding.passwordText.text.toString()

            if (email.equals("") || password.equals("")) {
                Toast.makeText(requireActivity(),"Enter your Email and Password!",Toast.LENGTH_LONG).show()
            }else {
                auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    findNavController().navigate(R.id.action_loginActivity_to_recipiesFragment)
                }.addOnFailureListener {
                    Toast.makeText(requireActivity(),it.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.signButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginActivity_to_signFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    }



