package com.kemaltalas.fakestoreproject.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FakeStoreModel(

    @SerializedName("title")
    var title : String,

    @SerializedName("price")
    var price : String,

    @SerializedName("image")
    var image : String,

    @SerializedName("category")
    var category: String,

    @SerializedName("description")
    var description : String,


) : Serializable