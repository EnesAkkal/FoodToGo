package com.enesakkal.foodToGo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.enesakkal.foodToGo.data.database.entities.FavoritesEntity
import com.enesakkal.foodToGo.data.database.entities.RecipesEntity

@Database(
    entities = [RecipesEntity::class,FavoritesEntity::class],  //we have only one entity or table so we need to specify the name of the entity class.
    version = 1,   // whenever you change your database schema, you need to increase version number
    exportSchema = false  //
)
@TypeConverters(RecipesTypeConverter::class) // we need to select our type converter class.In this case, RecipesTypeConverter class
abstract class RecipesDataBase: RoomDatabase() {

    abstract fun recipesDao() : RecipesDataAccessObject

}