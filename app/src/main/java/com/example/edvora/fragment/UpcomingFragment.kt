package com.example.edvora.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.edvora.adapter.RouteAdapter
import com.example.edvora.databinding.FragmentRidesBinding
import com.example.edvora.pojo.rideDetail.RideDetailResponse
import com.example.edvora.pojo.rideDetail.RideDetailResponseItem
import com.example.edvora.utils.ModelPreferencesManager
import java.util.*
import kotlin.collections.ArrayList


class UpcomingFragment : Fragment() {
    private lateinit var binding: FragmentRidesBinding
    private lateinit var ridesData:  ArrayList<RideDetailResponseItem>
    private var upComingArray = arrayListOf<RideDetailResponseItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRidesBinding.inflate(layoutInflater)
        ridesData = ModelPreferencesManager.getUpcomingDetail()!!
//        sorting(ridesData)
        adapter(ridesData)
        return binding.root

    }

    private fun adapter(list: ArrayList<RideDetailResponseItem>) {
        binding.rvRout.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRout.adapter = RouteAdapter(requireContext(), list)
    }

}