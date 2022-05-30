package com.enesakkal.foodToGo.userInterface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.enesakkal.foodToGo.R
import com.enesakkal.foodToGo.databinding.FragmentSignBinding
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignFragment : Fragment()  {

    private var _binding: FragmentSignBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout {
        _binding = FragmentSignBinding.inflate(inflater, container, false)
        auth = Firebase.auth

        binding.loginButton.setOnClickListener {
            val emailSign = binding.emailTextSign.text.toString()
            val passwordSign = binding.passwordTextSign.text.toString()
            auth.createUserWithEmailAndPassword(emailSign,passwordSign)

            if (emailSign.isEmpty() && passwordSign.isEmpty()) {
                Toast.makeText(requireActivity(),"enter your password",Toast.LENGTH_LONG).show() //requireActivity is needed if you use fragment.
            }else {
                auth.createUserWithEmailAndPassword(emailSign, passwordSign).addOnSuccessListener {
                    //Success
                    findNavController().navigate(R.id.action_signFragment_to_recipiesFragment)
                }.addOnFailureListener {
                    Toast.makeText(requireActivity(),it.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}