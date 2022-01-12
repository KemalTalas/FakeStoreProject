package com.kemaltalas.fakestoreproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kemaltalas.fakestoreproject.R
import com.kemaltalas.fakestoreproject.adapter.MainAdapter
import com.kemaltalas.fakestoreproject.model.FakeStoreModel
import com.kemaltalas.fakestoreproject.service.FakeStoreAPI
import com.kemaltalas.fakestoreproject.util.gone
import com.kemaltalas.fakestoreproject.util.filterCategory
import com.kemaltalas.fakestoreproject.util.navigation
import com.kemaltalas.fakestoreproject.util.visible
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.activity_user_login.*
import retrofit2.*


class MainActivity : AppCompatActivity(), MainAdapter.Listener, PopupMenu.OnMenuItemClickListener {


    private val BASE_URL = "https://fakestoreapi.com/"


    private  var fakeStoreModels: ArrayList<FakeStoreModel>? = null

    private var mainAdapter: MainAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)

        recyclerViewMain.layoutManager = layoutManager
        recyclerViewMain?.setHasFixedSize(true)

        progressBarMain.visible()
        recyclerViewMain.gone()


        loadProducts()




        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    Log.d("OnQuerySubmit",query)
                }
                mainAdapter?.getFilter()?.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    Log.d("OnQuerySubmit",newText)
                }
                mainAdapter?.getFilter()?.filter(newText)
                return false
            }

        })


        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_dashboard -> {
                    startActivity(navigation(applicationContext,MainActivity::class.java))
                    true
                }
                R.id.nav_categories -> {
                    startActivity(navigation(applicationContext,CategoriesActivity::class.java))
                    true
                }
                R.id.nav_empty -> {
                    startActivity(navigation(applicationContext,MainActivity::class.java))
                    true
                }
                R.id.nav_user -> {
                    startActivity(navigation(applicationContext,UserLoginActivity::class.java))
                    true
                }
                else -> false
            }
        }

    }



    fun loadProducts() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(FakeStoreAPI::class.java)
        val call = service.getProducts()

        call.enqueue(object : Callback<ArrayList<FakeStoreModel>>{
            override fun onResponse(
                call: Call<ArrayList<FakeStoreModel>>,
                response: Response<ArrayList<FakeStoreModel>>
            ) {
                if (response.isSuccessful){
                    response.body().let {
                        fakeStoreModels = ArrayList(it)
                        fakeStoreModels.let {
                            mainAdapter = it?.let { it1 -> MainAdapter(it1,this@MainActivity) }
                            recyclerViewMain.adapter = mainAdapter
                            progressBarMain.gone()
                            recyclerViewMain.visible()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<FakeStoreModel>>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Could not load",Toast.LENGTH_SHORT).show()
            }

        })
    }


    fun showFilter(view : View){
        val popup = PopupMenu(this,view)
        val inflater : MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.filter_menu,popup.menu)
        popup.setOnMenuItemClickListener(this)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.action_navElectronics -> {
                startActivity(filterCategory(this,"electronics"))
                true
            }R.id.action_navJewelery -> {
                startActivity(filterCategory(this,"jewelery"))
                true
            }R.id.action_navMensClothing -> {
                startActivity(filterCategory(this,"men's clothing"))
                true
            }R.id.action_navWomensClothing -> {
                startActivity(filterCategory(this,"women's clothing"))
                true
            }else -> false

        }
    }


    override fun onItemClick(position: Int, fakeStoreModel: FakeStoreModel) {
        Toast.makeText(this,"",Toast.LENGTH_SHORT)
    }

}








