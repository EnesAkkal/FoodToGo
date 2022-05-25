package com.enesakkal.foodToGo.data.database

import androidx.room.TypeConverter
import com.enesakkal.foodToGo.models.FoodRecipe
import com.enesakkal.foodToGo.models.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {

    /*

    Why  we create this class ? TypeConverterClass

     We cannot store complex objects in our database directly, instead  we need to convert them into acceptable types (Int,String etc.)
    so we can actually store them in our Database that's why we use TypeConverter.

    We are going to convert our foodRecipe object (under RecipesEntity class) into a jason or a string when we are writing our database
     and convert back from that string or json back to our foodRecipe object when we are reading from our database.


     */

    var gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe) : String {
        return gson.toJson(foodRecipe)

    }
    @TypeConverter
    fun stringToFoodRecipe(data:String):FoodRecipe {
        var listType = object :TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(data,listType)
    }
    @TypeConverter
    fun resultToString(result: Result): String {
        return gson.toJson(result)
    }
    @TypeConverter
    fun stringToResult(data: String) : Result  {
        var listType = object :TypeToken<Result>() {}.type
        return gson.fromJson(data,listType)
    }

}