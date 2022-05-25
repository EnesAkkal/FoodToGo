package com.enesakkal.foodToGo.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.enesakkal.foodToGo.models.Result
import com.enesakkal.foodToGo.util.Constants.Companion.FAVORITE_RECIPES_TABLE

/*

We have to modify our class which is RecipesTypeConverter so that we can store our Result when we write or read from our database.

 @TypeConverter
    fun resultToString(result: Result): String {
        return gson.toJson(result)
    }                                                       --------------> We've added those 2 functions to store our Result inside RecipesTypeConverter class.
    @TypeConverter
    fun stringToResult(data: String) : Result  {
        var listType = object :TypeToken<Result>() {}.type
        return gson.fromJson(data,listType)
    }

 */

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result //we cannot store complex object like this one so we need to convert result to string and vice versa.
) {
}