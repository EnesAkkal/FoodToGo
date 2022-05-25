package com.enesakkal.foodToGo.data.database

import androidx.room.*
import com.enesakkal.foodToGo.data.database.entities.FavoritesEntity
import com.enesakkal.foodToGo.data.database.entities.RecipesEntity


@Dao
interface RecipesDataAccessObject {

//We need to define our queries, which we are going to use for our DataBase.

    @Insert(onConflict = OnConflictStrategy.REPLACE) //it means that whenever we fetch a new data from our API, we want to replace the old one with new
    suspend fun insertRecipes(recipesEntity: RecipesEntity)  // we used suspend because we will use coroutines  to run this query
    //so our database table will basically have  newest recipes in database everytime we request data from API.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")  // ASC ->Ascending order
    fun readRecipes(): kotlinx.coroutines.flow.Flow<List<RecipesEntity>>  //will be used for reading recipes from our database.

    @Query("SELECT * FROM favorite_recipes_table ORDER BY id ASC")  //We wrote our query here by using SQL.
    fun readFavoriteRecipes(): kotlinx.coroutines.flow.Flow<List<FavoritesEntity>>

    @Delete
    suspend fun deleteFavoriteRecipes(favoritesEntity: FavoritesEntity)  //we used this function to delete our favorite recipes from favourites

    @Query("DELETE  FROM favorite_recipes_table")                   // the same but now it deletes everything from favourite recipes section
    suspend fun deleteAllFavoriteRecipes()
}