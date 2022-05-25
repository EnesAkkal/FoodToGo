package com.enesakkal.foodToGo.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.enesakkal.foodToGo.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.enesakkal.foodToGo.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.enesakkal.foodToGo.util.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.enesakkal.foodToGo.util.Constants.Companion.PREFERENCES_DIET_TYPE
import com.enesakkal.foodToGo.util.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.enesakkal.foodToGo.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.enesakkal.foodToGo.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.enesakkal.foodToGo.util.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


import javax.inject.Inject

/*
The main difference between sharedpreferences and datastore is that datastore running on back thread,
we need to define our preferencesKey with datastore.

We need to inject application context so we have to use @Inejct and constructor.

Inside this DataStoreRepository class, we need to create one function for actually saving those values. --> "suspend fun saveMealAndDietType"
 */

private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)



@ViewModelScoped //we need to use this tag to use our hilt library to annotate our datastore repository
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {
//We are going to need this context for our data store.

    private object PreferenceKeys {  //We need to define our preferences keys which we are going to use for our data store preferences
        // so what we are doing here is that saving the name of the chip and ID in the filter section.

        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE) // we  need to  define the type and actual name of that key
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID) // here we are saving the ID of the chip
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)  //We created those variables in constants class. E.g. --> PREFERENCES_DIET_TYPE.
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)


    }

    private val datastore: DataStore<Preferences> = context.dataStore // used "Androidx Preferences"
        //We've created our Datastore here.

    suspend fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int) {

        datastore.edit { preferences ->  //we've used datastore to actually save those values.This is basically mutable preferences object
            preferences[PreferenceKeys.selectedMealType] = mealType //mealType is the value here and value will be passed through this function.
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId
        }
    }

    suspend fun saveBackOnline(backOnline : Boolean) {
        datastore.edit { preferences->
            preferences[PreferenceKeys.backOnline] = backOnline
        }
    }

    val readMealAndDietType : Flow<MealAndDietType> = datastore.data  //type of flow is MealAndDietType.When we are going to read our values from bottomsheet.
        .catch {exception ->                                                        //We are going to use flow to pass mealAndDietType below.
            if (exception is IOException) {
                emit(emptyPreferences())
            }else {
                throw exception
            }
        }
        //Inside of this block below, we are going to create 4 different variables for 4 values.Datastore.edit içindeki değerler.Then we will create MealAndDietType object
        .map { preferences ->
            val selectedMealType = preferences[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
            val selectedDietType = preferences[PreferenceKeys.selectedDietType] ?: DEFAULT_DIET_TYPE //and if there is no value we will pass this value here.
            val selectedDietTypeId = preferences[PreferenceKeys.selectedDietTypeId] ?: 0  // 0 represents the ID of the chip
            MealAndDietType(   //since the type of flow is MealAndDietType, we need to return the same object here.
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }

    val readBackOnline: Flow<Boolean> = datastore.data
        .catch {exception ->                                                        //We are going to use flow to pass mealAndDietType below.
            if (exception is IOException) {
                emit(emptyPreferences())
            }else {
                throw exception
            }

        }
        .map { prefereces ->
            val backOnline = prefereces[PreferenceKeys.backOnline]?: false
            backOnline
        }
}

data class MealAndDietType (  //This class will have 4 different fields.We are going to use this class to pass those four values.
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int

        )