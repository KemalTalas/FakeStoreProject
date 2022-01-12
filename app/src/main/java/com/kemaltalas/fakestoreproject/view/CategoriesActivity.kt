package com.kemaltalas.fakestoreproject.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kemaltalas.fakestoreproject.R
import com.kemaltalas.fakestoreproject.adapter.CategoriesAdapter
import com.kemaltalas.fakestoreproject.adapter.MainAdapter
import com.kemaltalas.fakestoreproject.model.FakeStoreModel
import com.kemaltalas.fakestoreproject.service.FakeStoreAPI
import com.kemaltalas.fakestoreproject.util.gone
import com.kemaltalas.fakestoreproject.util.visible
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CategoriesActivity : AppCompatActivity(), CategoriesAdapter.Listener {

    private val BASE_URL = "https://fakestoreapi.com/"

    private var fakeStoreModels: ArrayList<FakeStoreModel>? = null

    private var categoriesAdapter: CategoriesAdapter? = null

    companion object{
        const val EXTRA_INTENT = "extra_intent_item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)


        cancelButton.setOnClickListener{
            onBackPressed()
        }

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        recyclerViewCategories.layoutManager = layoutManager
        recyclerViewCategories?.setHasFixedSize(true)

        progressBarCategories.visible()
        recyclerViewCategories.gone()
        loadCategories()

        filterButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun loadCategories() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(FakeStoreAPI::class.java)
        val call = service.getProducts()

        call.enqueue(object : Callback<ArrayList<FakeStoreModel>> {
            override fun onResponse(
                call: Call<ArrayList<FakeStoreModel>>,
                response: Response<ArrayList<FakeStoreModel>>
            ) {
                if (response.isSuccessful){
                    response.body().let {
                        fakeStoreModels = ArrayList(it)
                        fakeStoreModels.let {
                            categoriesAdapter = it?.let { it1 -> CategoriesAdapter(it,this@CategoriesActivity) }
                            recyclerViewCategories.adapter = categoriesAdapter
                            progressBarCategories.gone()
                            recyclerViewCategories.visible()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<FakeStoreModel>>, t: Throwable) {
            Toast.makeText(this@CategoriesActivity,"Could not load",Toast.LENGTH_SHORT).show()

            }

        })

    }

    override fun onItemClick(position: Int, fakeStoreModel: FakeStoreModel) {
        var categoryString = "${fakeStoreModel.category}"
        val intent = Intent(this,FilteredCategoriesActivity::class.java)
        intent.putExtra(EXTRA_INTENT,categoryString)
        startActivity(intent)

    }

}