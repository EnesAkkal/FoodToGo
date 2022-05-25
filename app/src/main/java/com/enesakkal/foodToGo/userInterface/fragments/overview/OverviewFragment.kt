package com.enesakkal.foodToGo.userInterface.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import coil.load
import com.enesakkal.foodToGo.bindingAdapters.RecipesRowBinding
import com.enesakkal.foodToGo.R
import com.enesakkal.foodToGo.databinding.FragmentOverviewBinding
import com.enesakkal.foodToGo.models.Result

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle : Result = args!!.getParcelable("recipeBundle",)!!

        binding.mainImageView.load(myBundle.image)
        binding.titleTextView.text = myBundle.title
        binding.likesTextView.text = myBundle.aggregateLikes.toString()
        binding.timeTextView.text = myBundle.readyInMinutes.toString()
        RecipesRowBinding.parseHtml(binding.summaryTextView, myBundle.summary)

        updateColors(myBundle.vegetarian!!, binding.vegetarianTextView, binding.vegetarianImageView)
        updateColors(myBundle.vegan!!, binding.veganTextView, binding.veganImageView)
        updateColors(myBundle.cheap!!, binding.cheapTextView, binding.cheapImageView)
        updateColors(myBundle.dairyFree!!, binding.diaryFreeTextView, binding.diaryFreeImageView)
        updateColors(myBundle.glutenFree!!, binding.glutenfreeTextView, binding.glutenFreeImageView)
        updateColors(myBundle.veryHealthy!!, binding.healtyTextView, binding.healthyImageView)



        return binding.root

    }

    private fun updateColors(stateIsOn: Boolean, textView: TextView, imageView: ImageView) {
        if (stateIsOn) {
            imageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
    }

}

