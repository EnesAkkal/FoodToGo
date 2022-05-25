package com.enesakkal.foodToGo.data.network

import com.enesakkal.foodToGo.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

/*
************ DEPENDENCY INJECTION *******************
Dependency injection is a technique in which an object receives other objects that it depends on.These other objects called dependencies.


Exaxmple: -->Classes often require references to other classes. For example, a Car class might need a reference to an Engine class.
These required classes are called dependencies, and in this example the Car class is dependent on having an instance of the Engine class to run.

How dependency injection look like ?
********************************************************************
class Car(private val engine: Engine) {
    fun start() {
        engine.start()
    }
}

fun main(args: Array) {
    val engine = Engine()
    val car = Car(engine)
    car.start()
}
*********************************************************************************

There are libraries that solve this problem by automating the process of creating and providing dependencies.
They fit into two categories:

1-)Reflection-based solutions that connect dependencies at runtime.
2-)Static solutions that generate the code to connect dependencies at compile time.

Dagger is a popular dependency injection library for Java, Kotlin, and Android that is maintained by Google.
Dagger facilitates using DI in your app by creating and managing the graph of dependencies for you.
It provides fully static and compile-time dependencies addressing many of the development and performance issues of reflection-based solutions such as Guice.

 */

class RemoteDataSource @Inject constructor (
    private val foodRecipesAPI: FoodRecipesAPI  //this  remote data source is injected with the foodRecipesAPI
    //and this foodRecipesAPI contains  get request function
        ) {

    suspend fun getRecipes (queries: Map<String, String>):Response<FoodRecipe> {

       return foodRecipesAPI.getRecipe(queries)
    }

    suspend fun searchRecipes(searchQuery : Map<String,String>) : Response<FoodRecipe> {
        return foodRecipesAPI.searchRecipes(searchQuery)
    }

}