package com.enesakkal.foodToGo.adapters

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.enesakkal.foodToGo.R
import com.enesakkal.foodToGo.userInterface.fragments.FavoriteRecipesFragmentDirections
import com.enesakkal.foodToGo.data.database.entities.FavoritesEntity
import com.enesakkal.foodToGo.databinding.FavoriteRecipesRowLayoutBinding
import com.enesakkal.foodToGo.util.RecipesDiffUtil
import com.enesakkal.foodToGo.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar

class FavoriteRecipesAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
):RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(),ActionMode.Callback {
    private var favoriteRecipes = emptyList<FavoritesEntity>()
    private var myViewHolders = arrayListOf<MyViewHolder>()
    private var multiSelection = false
    private lateinit var mActionMode: ActionMode
    private lateinit var rootView: View
    private var selectedRecipes = arrayListOf<FavoritesEntity>()

    class MyViewHolder(val binding: FavoriteRecipesRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from (parent: ViewGroup) : MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoriteRecipesRowLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myViewHolders.add(holder)
        rootView = holder.itemView.rootView
        val currentRecipe = favoriteRecipes[position]
        holder.bind(currentRecipe)
        saveItemStateOnScroll(currentRecipe,holder)

        /**  SINGLE CLICK LISTENER  **/
        holder.binding.favoriteRecipesRowLayout.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentRecipe)
            }else {
                val action = FavoriteRecipesFragmentDirections.actionFavoriteRecipiesFragmentToDetailsActivity(
                    currentRecipe.result
                )
                holder.itemView.findNavController().navigate(action)
            }
        }
        /** LONG CLICK LISTENER **/
        holder.binding.favoriteRecipesRowLayout.setOnClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder,currentRecipe)
                true
            }else{
                applySelection(holder, currentRecipe)
                true
            }
        }
    }
    private fun saveItemStateOnScroll(currentRecipe: FavoritesEntity,holder: MyViewHolder){
        if (selectedRecipes.contains(currentRecipe)) {
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
        }else {
            changeRecipeStyle(holder,R.color.cardBackgroundColor,R.color.strokeColor)
        }
    }
    private fun applySelection(holder: MyViewHolder,currentRecipe : FavoritesEntity) {

        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder,R.color.cardBackgroundColor,R.color.strokeColor)
            applyActionModeTitle()
        }else{
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder,R.color.cardBackgroundLightColor,R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(holder:MyViewHolder,backgroundColor: Int, strokeColor: Int) {
        holder.binding.favoriteRecipesRowLayout.setBackgroundColor(
            ContextCompat.getColor(requireActivity,backgroundColor)
        )
        holder.binding.favoriteRowCardView.strokeColor=
            ContextCompat.getColor(requireActivity,strokeColor)
    }

    private fun applyActionModeTitle() {
        when(selectedRecipes.size) {
            0 ->{
                mActionMode.finish()
                multiSelection = false
            }
            1->{ //if we choose only 1 recipe from our favoriteRecipe fragment then we will change the title of action mode
                mActionMode.title="${selectedRecipes.size} item selected"
            }
            else -> {  //if we have more than 1 title, we will have different title
                mActionMode.title="${selectedRecipes.size} items selected"
            }
        }
    }


    override fun getItemCount(): Int {
        return favoriteRecipes.size
    }



    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorites_contextual_menu,menu)
        mActionMode = actionMode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true

    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if(menu?.itemId == R.id.delete_favorite_recipe_menu){
            selectedRecipes.forEach {
                mainViewModel.deleteFavoriteRecipe(it)
            //we will go through each and every item in selectedRecipes Array and delete everythıng whıch are selected
            }
            showSnackBar("${selectedRecipes.size} Recipe/s removed.")

            multiSelection =false
            selectedRecipes.clear()
            actionMode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        myViewHolders.forEach {  holder ->
            changeRecipeStyle(holder,R.color.cardBackgroundColor,R.color.strokeColor)
        }
        multiSelection = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor =
            ContextCompat.getColor(requireActivity,color)
    }

    fun setData(newFavoritesRecipes: List<FavoritesEntity>) {
        val favoriteRecipesDiffUtil = RecipesDiffUtil(favoriteRecipes, newFavoritesRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoriteRecipes = newFavoritesRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT

        ).setAction("Okay"){}
            .show()
    }

    fun clearContextualActionMode() {
        if (this::mActionMode.isInitialized){
            mActionMode.finish()
        }
        }
    }



