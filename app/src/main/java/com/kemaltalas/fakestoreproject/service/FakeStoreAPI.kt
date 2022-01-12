package com.kemaltalas.fakestoreproject.service

import com.kemaltalas.fakestoreproject.model.FakeStoreModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FakeStoreAPI {



    @GET("products")
    fun getProducts() : Call<ArrayList<FakeStoreModel>>

    @GET("products/category/{id}")
    fun getCategories(@Path("id") category : String ) : Call<ArrayList<FakeStoreModel>>


}