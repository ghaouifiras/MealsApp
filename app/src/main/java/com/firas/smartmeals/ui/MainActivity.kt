package com.firas.smartmeals.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.firas.smartmeals.data.model.Category
import com.firas.smartmeals.databinding.ActivityMainBinding
import com.firas.smartmeals.others.Resource
import com.firas.smartmeals.others.Utils
import com.firas.smartmeals.view_models.MealViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MealViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) //initializing the binding class
        setContentView(binding.root)
        setAdapter()
        getListMeals()
        search()
    }


    private fun getListMeals() {


        viewModel.mealsStateFlow.onEach {
            when (it) {
                is Resource.Error -> {
                    it.message?.let { msg ->
                        showError(msg)
                    }
                    hideLoading()


                }
                is Resource.Loading -> {

                    showLoading()


                }
                is Resource.Success -> {


                    it.data?.let { list ->
                        showData(list)
                    }
                    hideLoading()


                }
            }

        }.launchIn(lifecycleScope)
        viewModel.fetchMeals("")


    }


    private fun showError(e: String) {
        if (Utils.isInternetConnected(context = this))
            Snackbar.make(binding.root, e, Snackbar.LENGTH_SHORT).show()
    }

    private fun showData(list: List<Category>) {
        mainAdapter.setData(list)

    }

    private fun showLoading() {
        binding.progressBar.isVisible = true


    }

    private fun hideLoading() {
        binding.progressBar.isVisible = false

    }

    private fun setAdapter() {
        mainAdapter = MainAdapter()
        binding.rec.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mainAdapter
            setHasFixedSize(true)
            adapter?.notifyDataSetChanged()


        }
    }

    private fun search() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // viewModel.searchQuery.value = char.toString()
                viewModel.fetchMeals(char.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }
}