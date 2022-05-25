package com.enesakkal.foodToGo.userInterface.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesakkal.foodToGo.R
import com.enesakkal.foodToGo.viewmodels.MainViewModel
import com.enesakkal.foodToGo.adapters.RecipesAdapter
import com.enesakkal.foodToGo.databinding.FragmentRecipiesBinding
import com.enesakkal.foodToGo.util.Constants.Companion.API_KEY
import com.enesakkal.foodToGo.util.NetworkListener
import com.enesakkal.foodToGo.util.NetworkResult
import com.enesakkal.foodToGo.util.observeOnce
import com.enesakkal.foodToGo.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipesFragment : Fragment(), SearchView.OnQueryTextListener {

    private val args by navArgs<RecipesFragmentArgs>()


    private val mAdapter by lazy { RecipesAdapter() }
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel :RecipesViewModel
    private lateinit var networkListener: NetworkListener

    private var _binding: FragmentRecipiesBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentRecipiesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setHasOptionsMenu(true)

        networkListener = NetworkListener()
        networkListener.checkNetworkAvailability(requireContext())

        setUpRecyclerView()
       // requestApiData() //whenever we open our app, we request new data but we are gonna change that so we wıll remove this

        recipesViewModel.readBackOnline.observe(viewLifecycleOwner
        ) {
            recipesViewModel.backOnline = it
        }


        lifecycleScope.launchWhenStarted {
           networkListener = NetworkListener()
           networkListener.checkNetworkAvailability(requireContext())
               .collect { status ->
                   Log.d("NetworkListener",status.toString())
                   recipesViewModel.networkStatus = status
                   recipesViewModel.showNetworkStatus()
                   readDatabase() //this function will be run as soon as our app starts since we are calling it under onCreate
               }
       }

        binding.recipesFab.setOnClickListener {
            if (recipesViewModel.networkStatus) {
                findNavController().navigate(R.id.action_recipiesFragment_to_recipesBottomSheet)
            }else{
                recipesViewModel.showNetworkStatus()
            }
        }
        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.RecyclerView.adapter = mAdapter
        binding.RecyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu,menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        if (query != null) {
            searchApiData(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        return true
    }

    private fun readDatabase() {  //we created this fun, so if our database is not empty then basically we want to read from our database
     lifecycleScope.launch {
         mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
             if (database.isNotEmpty() && !args.backFromBottomSheet) {   //if our database is not empty, we want to call our adapter.
                 Log.d("RecipesFragment", "readDatabase Called!")
                 mAdapter.setData(database[0].foodRecipe) //we will get the first row of our data so we will type 0 here
                 hideShimmerEffect()
             } else {
                 requestApiData()  //request API DATA
             }
         }
     }
    }

    private fun requestApiData() {

        Log.d("RecipesFragment","requestApiData Called!")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())  //called this getRecipes function from our MainViewModel
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(requireContext(),response.message.toString(),Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }

        }
    }

    private fun searchApiData(searchQuery : String) {
        showShimmerEffect()
        mainViewModel.searchRecipes(recipesViewModel.applySearchQuery(searchQuery))
        mainViewModel.searchedRecipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    val foodRecipe = response.data
                    foodRecipe?.let {
                        mAdapter.setData(it)

                    }

                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun applyQueries(): HashMap<String,String> {
        val queries: HashMap<String,String> = HashMap()

        queries["number"] = "50"
        queries["apiKey"] = API_KEY
        queries["type"] = "Snack"
        queries["diet"] = "Vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"

        return queries
    }


    private fun loadDataFromCache() { // we are going to call this data inside MetWorkResult.error

        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner
            ) { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].foodRecipe)

                }
            }
        }
    }




    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.RecyclerView.visibility = View.GONE


    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.RecyclerView.visibility = View.VISIBLE
        binding.shimmerFrameLayout.visibility = View.GONE


    }

    override fun onDestroy() {  //with this, we are going to avoid the memory leaks.
        super.onDestroy()
        _binding = null
    }


}

