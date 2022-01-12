package com.kemaltalas.fakestoreproject.util

import android.content.Context
import android.content.Intent
import android.view.View
import com.kemaltalas.fakestoreproject.view.FilteredCategoriesActivity
import com.kemaltalas.fakestoreproject.view.CategoriesActivity.Companion.EXTRA_INTENT
import java.net.URI

fun View.gone(){
    this.visibility = View.GONE
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

 fun filterCategory(context : Context, name : String) : Intent{

    val intent = Intent(context,FilteredCategoriesActivity::class.java)
    intent.putExtra(EXTRA_INTENT,name)

    return intent
}

fun navigation(context: Context,cls : Class<*>) : Intent{
    val intent = Intent(context,cls)
    return intent
}