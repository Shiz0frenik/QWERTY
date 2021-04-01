package com.example.qwerty

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.qwerty.MapsActivity.MySingleton.myVariable
import com.example.qwerty.data.Point
import com.example.qwerty.data.fragments.list.ListFragmentDirections
import kotlinx.android.synthetic.main.custom_row.view.*


class ListAdapter:RecyclerView.Adapter<ListAdapter.MyViewHolder>(){
    private var pointList = emptyList<Point>()
    class MyViewHolder(itemView : View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row,parent,false))
    }

    override fun getItemCount(): Int {
        return pointList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = pointList[position]
        holder.itemView.txt.text = currentItem.id.toString()
        holder.itemView.longitude.text = currentItem.longitide.toString()
        holder.itemView.lattitude.text = currentItem.latitude.toString()
        holder.itemView.adress.text = currentItem.adress
        holder.itemView.RowLayout.setOnClickListener {
             myVariable=currentItem }
    }


    fun setData(point:List<Point>){
        this.pointList = point
        notifyDataSetChanged()
    }





}
