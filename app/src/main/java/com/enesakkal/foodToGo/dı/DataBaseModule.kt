package com.enesakkal.foodToGo.dı

import android.content.Context
import androidx.room.Room
import com.enesakkal.foodToGo.data.database.RecipesDataBase
import com.enesakkal.foodToGo.util.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
Inside this module, we are going to tell our hilt library how to provide our room database builder and recipesDao
We are going to create two functions to do that.

 */

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context  //we use this annotation from our hilt library so we can inject this app context immediately
    ) = Room.databaseBuilder( //this function should return dataBaseBuilder
        context, //from our provideDataBase function.
        RecipesDataBase::class.java,  //the actual Database,
        DATABASE_NAME  //actual name of the database, we created it under constants class.
    ).build()  //this function will provide our room database builder  and we need to annotate this fun with @singleton and @provides
    //because room library is not our class, it's a third party class.

    @Singleton
    @Provides
    fun provideDao(database: RecipesDataBase) = database.recipesDao()


}