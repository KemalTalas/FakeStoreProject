package com.kemaltalas.fakestoreproject.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Filter
import android.widget.Filterable
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kemaltalas.fakestoreproject.R
import com.kemaltalas.fakestoreproject.adapter.MainAdapter
import com.kemaltalas.fakestoreproject.model.FakeStoreModel
import com.kemaltalas.fakestoreproject.service.FakeStoreAPI
import com.kemaltalas.fakestoreproject.util.gone
import com.kemaltalas.fakestoreproject.util.visible
import com.kemaltalas.fakestoreproject.view.CategoriesActivity.Companion.EXTRA_INTENT
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.android.synthetic.main.activity_filtered_categories.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FilteredCategoriesActivity : AppCompatActivity(), MainAdapter.Listener {

    private val BASE_URL = "https://fakestoreapi.com/"

    private  var fakeStoreModels: ArrayList<FakeStoreModel>? = null

    private var filterAdapter: MainAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtered_categories)

        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false)
        recyclerViewFiltered.layoutManager = layoutManager
        recyclerViewFiltered?.setHasFixedSize(true)

        var catName : String

        catName = intent.getStringExtra(EXTRA_INTENT).toString()

        progressBarFilter.visible()
        recyclerViewFiltered.gone()

        loadFiltered(catName)

        cancelButtonFiltered.setOnClickListener {
            onBackPressed()
        }

    }

    fun loadFiltered(category : String) {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(FakeStoreAPI::class.java)
        val call = service.getCategories(category)

        call.enqueue(object : Callback<ArrayList<FakeStoreModel>> {
            override fun onResponse(
                call: Call<ArrayList<FakeStoreModel>>,
                response: Response<ArrayList<FakeStoreModel>>
            ) {
                if (response.isSuccessful){
                    response.body().let {
                        fakeStoreModels = ArrayList(it)
                        fakeStoreModels.let {
                            filterAdapter = it?.let { it1 -> MainAdapter(it1,this@FilteredCategoriesActivity) }
                            recyclerViewFiltered.adapter = filterAdapter
                            progressBarFilter.gone()
                            recyclerViewFiltered.visible()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<FakeStoreModel>>, t: Throwable) {
                Toast.makeText(this@FilteredCategoriesActivity,"Could not load",Toast.LENGTH_SHORT).show()
            }

        })
    }


    override fun onItemClick(position: Int, fakeStoreModel: FakeStoreModel) {
        Toast.makeText(this,"",Toast.LENGTH_SHORT)
    }

}