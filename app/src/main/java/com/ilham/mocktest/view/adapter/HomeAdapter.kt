package com.ilham.mocktest.view.adapter

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ilham.mocktest.R
import com.ilham.mocktest.databinding.ItemListBinding
import com.ilham.mocktest.local.DatabaseToko
import com.ilham.mocktest.model.DataToko
import com.ilham.mocktest.viewmodel.HomeViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class HomeAdapter(var context: Context, var toko: List<DataToko>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var Note: DatabaseToko? = null
    class ViewHolder(var binding : ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : DataToko){
            binding.dbtoko = item
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(toko[position])
        // delete note
        holder.binding.btnDelete.setOnClickListener {
            Note = DatabaseToko.getInstance(it.context)
            GlobalScope.async {
                HomeViewModel(Application()).delete(toko[position])
                Note?.tokoDao()?.deleteToko(toko[position])
                kotlin.run {
                    Navigation.findNavController(it).navigate(R.id.homeFragment)
                }


            }
        }
        holder.binding.btnEdit.setOnClickListener {
            var edit = Bundle()
            edit.putSerializable("edit",toko[position])
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_editFragment,edit)

        }

    }

    override fun getItemCount(): Int {
        return toko.size
    }

    fun setNotes(itemToko: ArrayList<DataToko>) {
        this.toko = itemToko
    }


}