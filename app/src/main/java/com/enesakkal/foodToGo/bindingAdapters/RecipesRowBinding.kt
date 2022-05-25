package com.enesakkal.foodToGo.bindingAdapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.enesakkal.foodToGo.R
import com.enesakkal.foodToGo.userInterface.fragments.RecipesFragmentDirections
import com.enesakkal.foodToGo.models.Result
import org.jsoup.Jsoup

class RecipesRowBinding {

    companion object {

        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeRowLayout : ConstraintLayout, result: Result) {
            recipeRowLayout.setOnClickListener {
                try {
                    val action =
                        RecipesFragmentDirections.actionRecipiesFragmentToDetailsActivity(result)
                        recipeRowLayout.findNavController().navigate(action)

                }catch (e:Exception) {
                    Log.d("onRecipeClickListener",e.toString())
                }
            }
        }

        @JvmStatic
        @BindingAdapter("loadImageFromUrl")
        fun loadImageFromUrl(imageView: ImageView,imageUrl: String){
            imageView.load(imageUrl){
                crossfade(600)
                error(R.drawable.ic_error_placeholder) //bu kod ile internete bağlı olmadıgımız zaman ınmeyen resımlerın gozukmemesını sagladık.
                //drawable klasorune vector asset olusturup bunu kullandık.

            }
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view : View, vegan:Boolean) {
            if (vegan){
                when(view){
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }

                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                }
            }
        }

        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(textView: TextView, description : String?) {

            if (description !=null) {
                val desc = Jsoup.parse(description).text()
                textView.text = desc
            }

        }
    }
}