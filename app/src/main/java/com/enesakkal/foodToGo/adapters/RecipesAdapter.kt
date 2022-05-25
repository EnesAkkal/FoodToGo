package com.enesakkal.foodToGo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.enesakkal.foodToGo.databinding.RecipesRowLayoutBinding
import com.enesakkal.foodToGo.models.FoodRecipe
import com.enesakkal.foodToGo.models.Result
import com.enesakkal.foodToGo.util.RecipesDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

        private var recipes = emptyList<Result>()

    class MyViewHolder(private val binding:RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(result: Result) {

            binding.result = result
            binding.executePendingBindings()

        }

        companion object {

            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater =LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)

            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setData(newData:FoodRecipe) {  //Notifying us and calling this function when there is a new data.

        val recipesDiffUtil = RecipesDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
     //   notifyDataSetChanged()  //Bu kod bütün datayı değişip değişmesine bakmaksızın hepsını yenıledıgı ıcın tercıh edılmıyor performans dusurdugu ıcın
        //Bu yüzden diffutil kullanacağız.Böylelikle daha efektif bir app geliştirmiş olacağız.
    }
}