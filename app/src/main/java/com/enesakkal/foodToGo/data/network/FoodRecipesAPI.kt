package com.enesakkal.foodToGo.data.network

import com.enesakkal.foodToGo.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipesAPI {

    @GET("/recipes/complexSearch")
    suspend fun getRecipe(
        @QueryMap queries : Map<String, String>
    ):Response<FoodRecipe>
    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQuery: Map<String, String>
    ):Response<FoodRecipe>
}