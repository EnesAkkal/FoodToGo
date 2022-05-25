package com.enesakkal.foodToGo.userInterface

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.enesakkal.foodToGo.R
import com.enesakkal.foodToGo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


/*
Menuyu olusturuken  bottom_nav_menu xml dosyasında  olustulucak secenklerdeki ıd'ler ile
my_nav xml dosyasında  olusturdugumuz ıd ler ıle aynı olmak zorunda

    <item android:id="@+id/recipiesFragment"
         android:title="@string/recipies_fragment"
        android:icon="@drawable/ic_menu_book"/>
        <fragment
        android:id="@+id/recipiesFragment"
        android:name="com.example.foodapplication.UserInterface.Fragments.RecipiesFragment"
        android:label="Recipies"
        tools:layout="layout/fragment_recipies" />

        yukarıda gozuktugu gıbı yukardakı bottom nav xmlindeki id ile aşağıda my_nav xml de olusan id aynı
        olması gerekıyor.

        En son olarak mainActivity xml dosyasında bottom navigation menu eklememız ıcın
        app:menu="@menu/bottom_nav_menu"/>
        yazmamız gerekıyor boylelıkle aşağıda cıkan menuyu maınActivity xml'de gorebılırız.


        ********************************** BÖLÜM - 4 START WITH RETROFIT *************************************
        https://api.spoonacular.com/recipes/complexSearch?number=50&apiKey=3a00991f427e4c5b81a69ab80fb22392&type=snack&diet=vegan&addRecipeInformation=true&fillIngredients=true

        Bu linkten ham datamıza ulaştık fakat goruntuden bır sey anlasılmıyor o yuzden plugin indirmemiz gerekıyor guzel gozukmesı ıcn

        File > Settings > Plugins kısmından JSON to Kotlin Class indirdik.Bu sekılde datalar daha guzel gozukecek.

        Ardından new > "Kotlin data class from json" tıkladıktan sonra açılan pencerede ham datayı yapıstıracagız ve format dedik
        Böylelikle datalar daha guzel gozukuyor ardından aşağıda bulunan  "Advanced" kısmından ozellıklerı degıstırebılıyoruz.

        Verilerin val mı var mı olacağı null mı yoksa non nullable olacagı gıbı vs.

        Burada anettotation non-nullable olarak sectık

 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       //setTheme(R.style.Apptheme)  //--->Bunu sonradan ekleyeceksin buraya ardından dıger kodlar gelecek obur turlu hata gelır

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = findNavController(R.id.navHostFragment)
        val navGraph = navController.navInflater.inflate(R.navigation.my_nav)
        navGraph.setStartDestination(R.id.loginActivity)
        navController.graph = navGraph
        binding.bottomNavigationView.setupWithNavController(navController)




        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.recipiesFragment,
                R.id.favoriteRecipiesFragment,
                R.id.foodJokeFragment
            )
        )
        binding.bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController,appBarConfiguration)
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}