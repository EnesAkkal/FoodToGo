package com.enesakkal.foodToGo.userInterface.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesakkal.foodToGo.adapters.IngredientsAdapter
import com.enesakkal.foodToGo.databinding.FragmentIngredientsBinding
import com.enesakkal.foodToGo.models.Result
import com.enesakkal.foodToGo.util.Constants.Companion.RECIPE_RESULT_KEY


class IngredientsFragment : Fragment() {

    private val mAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }
    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle : Result = args!!.getParcelable(RECIPE_RESULT_KEY,)!!
        setupRecyclerView()

        myBundle?.extendedIngredients?.let { mAdapter.setData(it) }


        return binding.root
    }

        private fun setupRecyclerView() {
        binding.IngredientsRecyclerview.adapter = mAdapter
        binding.IngredientsRecyclerview.layoutManager = LinearLayoutManager(requireContext())


    }

}
