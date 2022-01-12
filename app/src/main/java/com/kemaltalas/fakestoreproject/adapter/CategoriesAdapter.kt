package com.kemaltalas.fakestoreproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kemaltalas.fakestoreproject.R
import com.kemaltalas.fakestoreproject.model.FakeStoreModel
import kotlinx.android.synthetic.main.item_categories.view.*
import kotlin.collections.ArrayList

class CategoriesAdapter( fakeStoreList : ArrayList<FakeStoreModel>, private val listener : Listener) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>(){


     var filterCategoryList = ArrayList<FakeStoreModel>(fakeStoreList).distinctBy {
        it.category
    }.sortedBy {
        it.category
    }


    interface Listener{
        fun onItemClick(position: Int, fakeStoreModel: FakeStoreModel)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        fun bind(fakeStoreModel: FakeStoreModel,position: Int, listener : Listener){
            itemView.setOnClickListener {
                listener.onItemClick(position,fakeStoreModel)
            }
            itemView.item_categoryName.text = fakeStoreModel.category
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_categories,parent,false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return filterCategoryList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(filterCategoryList[position],position,listener)
    }






}