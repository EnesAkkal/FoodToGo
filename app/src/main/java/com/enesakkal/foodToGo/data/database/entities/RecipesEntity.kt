package com.enesakkal.foodToGo.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.enesakkal.foodToGo.models.FoodRecipe
import com.enesakkal.foodToGo.util.Constants.Companion.RECIPES_TABLE

/*
Constants class'ın altında ROOM database oluşturduk.Bunu yaparken const val olan iki tane variable oluşturduk.
 */

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity (
    var foodRecipe :FoodRecipe)

 {
    @PrimaryKey(autoGenerate = false)  //whenever we get a new data from our API,we're going to replace all the data from our database Table with new Data
    // so we are not going to have multiple food recipe, we have only one and we will replace the data from our API if we fetch data.
    var id: Int= 0   //id as Integer and default value 0
}