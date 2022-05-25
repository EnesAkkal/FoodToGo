package com.enesakkal.foodToGo.data

import com.enesakkal.foodToGo.data.database.RecipesDataAccessObject
import com.enesakkal.foodToGo.data.database.entities.FavoritesEntity
import com.enesakkal.foodToGo.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDataAccessObject  //We need to inject our recipesdao to create our local database.
) {
     fun readRecipes() :Flow<List<RecipesEntity>> {
         return recipesDao.readRecipes()
    }

    fun readFavoriteRecipes() : kotlinx.coroutines.flow.Flow<List<FavoritesEntity>> {
        return recipesDao.readFavoriteRecipes()
    }
    suspend fun insertRecipes(recipesEntity: RecipesEntity) { //we need a function for inserting the recipes into the  database
        recipesDao.insertRecipes(recipesEntity) //we call this dao, and we call insertRecipes function from our dao interface
    }

    suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        recipesDao.insertFavoriteRecipe(favoritesEntity)
    }

    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        recipesDao.deleteFavoriteRecipes(favoritesEntity)
    }

    suspend fun deleteAllFavoriteRecipes() {
        recipesDao.deleteAllFavoriteRecipes()
    }

}