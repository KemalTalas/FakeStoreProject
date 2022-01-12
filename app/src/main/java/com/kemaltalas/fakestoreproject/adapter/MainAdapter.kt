package com.kemaltalas.fakestoreproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.kemaltalas.fakestoreproject.R
import com.kemaltalas.fakestoreproject.model.FakeStoreModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_main.view.*
import java.util.*
import kotlin.collections.ArrayList

class MainAdapter(var fakeStoreList : ArrayList<FakeStoreModel>, listener : Listener) : RecyclerView.Adapter<MainAdapter.RowHolder>() , Filterable {

    val filterList = ArrayList<FakeStoreModel>(fakeStoreList)

    interface Listener {
        fun onItemClick(position: Int,fakeStoreModel: FakeStoreModel)
    }

    class RowHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(fakeStoreModel: FakeStoreModel,position: Int){


                itemView.item_main_itemName.text = fakeStoreModel.title
                itemView.item_main_itemPrice.text = "${fakeStoreModel.price}$"

            Picasso.get().load(fakeStoreModel.image).into(itemView.item_main_imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main,parent,false)
        return RowHolder(view)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(fakeStoreList[position],position)
    }

    override fun getItemCount(): Int {
        return fakeStoreList.count()
    }

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<FakeStoreModel>()
            if (constraint == null || constraint.isEmpty()){
                    filteredList.addAll(filterList)
            }else{
                val filteredPattern = constraint.toString().lowercase(Locale.getDefault()).trim()

                for(model : FakeStoreModel in filterList){
                    val itemName = model.title.toString()
                    val categoryName = model.category.toString()

                    if (itemName.lowercase(Locale.getDefault()).contains(filteredPattern) || categoryName.lowercase(Locale.getDefault()).contains(filteredPattern)){
                        filteredList.add(model)
                    }
                }
            }
            return FilterResults().apply { values = filteredList }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            fakeStoreList.clear()
            fakeStoreList.addAll(results?.values as ArrayList<FakeStoreModel>)
            notifyDataSetChanged()
        }

    }


}