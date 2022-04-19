package com.example.edvora.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.edvora.databinding.ChildRouteAdapterBinding
import com.example.edvora.pojo.rideDetail.RideDetailResponseItem

class RouteAdapter (
    private val context: Context?,
    private val list: ArrayList<RideDetailResponseItem>

) :
    RecyclerView.Adapter<RouteAdapter.ViewHolder>() {

    class ViewHolder(binding: ChildRouteAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val cityName = binding.cityName
        val stateName = binding.StateName
        val stationPath = binding.stationPath
        val routeId = binding.routId
        val routeImage = binding.routeImage
        val distance = binding.distance
        val date = binding.date
        val origin = binding.originStationId

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ChildRouteAdapterBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item:RideDetailResponseItem= list[position]
        Glide.with(context!!).load(item.map_url).centerCrop().into(holder.routeImage)
        holder.distance.text= item.distance.toString()
        holder.origin.text= item.origin_station_code.toString()
        holder.stationPath.text= item.station_path.toString()
        holder.routeId.text= item.id.toString()
        holder.date.text= item.date
        holder.cityName.text= item.city
        holder.stateName.text= item.state
    }
}