package com.example.edvora.activity

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.edvora.R

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.edvora.databinding.ActivityHomeScreenBinding
import com.example.edvora.databinding.FilterDialogBoxBinding
import com.example.edvora.fragment.NearestFragment
import com.example.edvora.fragment.PastFragment
import com.example.edvora.fragment.UpcomingFragment
import com.example.edvora.listner.ApiInterface
import com.example.edvora.listner.ApiListener
import com.example.edvora.pojo.rideDetail.RideDetailResponse
import com.example.edvora.pojo.rideDetail.RideDetailResponseItem
import com.example.edvora.pojo.userDetail.UserDetailResponse
import com.example.edvora.utils.APIClient
import com.example.edvora.utils.ApiRepo
import com.example.edvora.utils.ModelPreferencesManager
import com.google.android.material.tabs.TabLayout
import retrofit2.Retrofit
import java.util.*
import kotlin.collections.ArrayList

class HomeScreen : AppCompatActivity(), ApiListener {
    private lateinit var binding: ActivityHomeScreenBinding
    private var userDetailUrl = ""
    private var ridesDetailUrl = ""
    private var upComingCount = 0
    private var pastCount = 0
    var dis = 1000
    private var stateList = arrayListOf<String>()
    private var cityList = arrayListOf<String>()
    private var upComingArray = arrayListOf<RideDetailResponseItem>()
    private var pastArray = arrayListOf<RideDetailResponseItem>()
    private var nearArray = arrayListOf<RideDetailResponseItem>()

    private var upComingArrayTemp = arrayListOf<RideDetailResponseItem>()
    private var pastArrayTemp = arrayListOf<RideDetailResponseItem>()
    private var nearArrayTemp = arrayListOf<RideDetailResponseItem>()
    private var selectedStateName = ""
    private var selectedCityName = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        ridesDetailApiCall()
        userDetailApiCall()

        setContentView(binding.root)
    }

    private fun initTab(upcoming: String, past: String) {
        val adapter = MyViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(NearestFragment(), "Nearest")
        adapter.addFragment(UpcomingFragment(), "Upcoming(${upcoming})")
        adapter.addFragment(PastFragment(), "Past(${past})")
        binding.viewpagerPickup.adapter = adapter
        binding.routeTab.tabMode = TabLayout.MODE_SCROLLABLE
        binding.routeTab.setupWithViewPager(binding.viewpagerPickup)
        for (i in 0..binding.routeTab.tabCount.minus(1)) {
            val tab = binding.routeTab.getTabAt(i)!!
            tab.customView = null
            //tab!!.customView = fragmentAdapter.getTabView(i)

            val tabTextView: TextView = TextView(this)
            tab.customView = tabTextView

            tabTextView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            tabTextView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

            tabTextView.text = tab.text

            if (i == 0) {
                // This set the font style of the first tab
                tabTextView.setTypeface(null, Typeface.BOLD)

            }
            if (i == 1) {
                // This set the font style of the second tab
                tabTextView.setTypeface(null, Typeface.NORMAL)
            }
        }
        binding.routeTab.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewpagerPickup.currentItem = tab!!.position
                val text: TextView = tab.customView as TextView
                text.setTypeface(null, Typeface.BOLD)
                text.setTextColor(resources.getColor(R.color.white))
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val text: TextView = tab.customView as TextView
                text.setTypeface(null, Typeface.NORMAL)
                text.setTextColor(resources.getColor(R.color.light_grey))

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun userDetailApiCall() {
        Log.d("userDetailResponse:", " 0")

        var retrofit: Retrofit = APIClient.getClient()!!
        var apiInterface: ApiInterface = retrofit.create(ApiInterface::class.java)
        Log.d("userDetailResponse:", " 1")

        val apiCall = apiInterface.userDetail()
        userDetailUrl = apiCall.request().toString()
        Log.d("userDetailResponse:", "$userDetailUrl")

        ApiRepo.callRetrofit(
            apiCall, this, userDetailUrl, this
        )
    }

    private fun ridesDetailApiCall() {
        Log.d("userDetailResponse:", " 0")

        var retrofit: Retrofit = APIClient.getClient()!!
        var apiInterface: ApiInterface = retrofit.create(ApiInterface::class.java)
        Log.d("userDetailResponse:", " 1")

        val apiCall = apiInterface.ridesDetail()
        ridesDetailUrl = apiCall.request().toString()
        Log.d("userDetailResponse:", "$ridesDetailUrl")

        ApiRepo.callRetrofit(
            apiCall, this, ridesDetailUrl, this
        )
    }

    override fun success(url: String, response: Any) {
        try {
            if (userDetailUrl != null && url == userDetailUrl) {
                val userModel: UserDetailResponse =
                    response as UserDetailResponse
                binding.userName.text = userModel.name
                Glide.with(this).load(userModel.url).into(binding.userImage)
            }
            if (ridesDetailUrl != null && url == ridesDetailUrl) {
                upComingArray.clear()
                pastArray.clear()
                stateList.add("State")
                cityList.add("City")

                val ridesModel: RideDetailResponse =
                    response as RideDetailResponse
                val currentDate = Date()

                Log.d("rideDetailResponse:", " Success: ${ridesModel.size}")
                for (i in 0..ridesModel.size.minus(1)) {
                    val temp = ridesModel[i].date.split(" ")
                    val temp2 = temp[0].split("/")
                    val month = temp2[0]
                    val date = temp2[1]
                    val year = temp2[2]
                    stateList.add(ridesModel[i].state)
                    cityList.add(ridesModel[i].city)
                    nearArray.add(ridesModel[i])


                    Log.d("currentDat3", "${month},${date}, ${year}")

                    if (date.toInt() > currentDate.date && month.toInt() > currentDate.month) {
                        upComingArray.add(ridesModel[i])
                        upComingCount += 1

                    }
                    if (date.toInt() < currentDate.date && month.toInt() < currentDate.month) {
                        pastCount += 1
                        pastArray.add(ridesModel[i])

                    }

                    dis = 1000
                    Log.d("currentDat3", "${ridesModel[i].station_path}")
                    for (j in 0..ridesModel[i].station_path.size.minus(1)) {
                        if (ridesModel[i].distance == null) {
                            val tempDist =
                                ridesModel[i].station_path[j] - ridesModel[i].origin_station_code
                            if (tempDist > 0) {
                                if (dis >= tempDist) {
                                    dis = tempDist
                                }
                            }
                        }
                    }
                    ridesModel[i].distance = dis
                }

                Log.d(
                    "rideDetailResponse:",
                    "${ridesModel.size}\n ${upComingArray.size} \n ${pastArray.size} "
                )
                initTab(upComingCount.toString(), pastCount.toString())

                binding.filter.setOnClickListener {
                Toast.makeText(this, "Functionality not Implemented", Toast.LENGTH_SHORT).show()
//                    val builder = AlertDialog.Builder(this).create()
//                    LayoutInflater.from(this)
//                    builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))  // for transparent background
//                    val binding: FilterDialogBoxBinding =
//                        FilterDialogBoxBinding.inflate(LayoutInflater.from(this))
//                    builder.setCancelable(false)
//
//                    var arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
//                        this,
//                        android.R.layout.simple_spinner_item,
//                        stateList
//                    )
//                    var arrayAdapter2: ArrayAdapter<String> = ArrayAdapter<String>(
//                        this,
//                        android.R.layout.simple_spinner_item,
//                        cityList
//                    )
//                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                    binding.stateSpinner.adapter = arrayAdapter
//                    binding.stateSpinner.setOnItemSelectedListener(object :
//                        AdapterView.OnItemSelectedListener {
//                        override fun onItemSelected(
//                            parent: AdapterView<*>,
//                            view: View,
//                            position: Int,
//                            id: Long
//                        ) {
//                            Log.d(
//                                "rideDetail12:",
//                                " ${stateList} "
//                            )
//                            selectedStateName= parent.selectedItem as String
//
//                        }
//
//                        override fun onNothingSelected(parent: AdapterView<*>?) {}
//                    })
//                    citySelected()
//
//                    if (selectedStateName!=""){
//                        binding.citySpinner.visibility= View.VISIBLE
//
//                        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                        binding.stateSpinner.adapter = arrayAdapter2
//                        binding.stateSpinner.setOnItemSelectedListener(object :
//                            AdapterView.OnItemSelectedListener {
//                            override fun onItemSelected(
//                                parent: AdapterView<*>,
//                                view: View,
//                                position: Int,
//                                id: Long
//                            ) {
//                                selectedCityName = parent.selectedItem as String
//                            }
//
//                            override fun onNothingSelected(parent: AdapterView<*>?) {}
//                        })
//                    }
//
//
//                    builder.setView(binding.root)
//                    builder.show()
//
//
                }
                ModelPreferencesManager.putNearDetail(nearArray)
                ModelPreferencesManager.putUpcomingDetail(upComingArray)
                ModelPreferencesManager.putPastDetail(pastArray)
            }


        } catch (e: Exception) {
            Log.d("userDetailResponse:", " Success")
        }
    }


    override fun error(error: String) {
        Log.d("userDetailResponse:", " error")
    }

    override fun failure(message: String) {
        Log.d("userDetailResponse:", " failure:$message")

    }

//    private fun citySelected(){
//        if (selectedStateName == "City") {
//            nearArrayTemp= nearArray
//            upComingArrayTemp= upComingArray
//            pastArrayTemp= pastArray
//        } else {
//
//            for (i in 0..nearArray.size.minus(1)) {
//                Log.d(
//                    "rideDetail12:",
//                    "${nearArray[i].city}---- ${cityList} "
//                )
////                        && nearArray[i].state ==selectedStateName
//                if (nearArray[i].state ==selectedCityName ){
//                    nearArrayTemp.add(nearArray[i])
//                }
//            }
////                    && upComingArray[i].state== selectedStateName
//            for (i in 0..upComingArray.size.minus(1)) {
//                if (upComingArray[i].state== selectedCityName ){
//                    upComingArrayTemp.add(upComingArray[i])
//                }
//            }
////                    && pastArray[i].state== selectedStateName
//            for (i in 0..pastArray.size.minus(1)) {
//                if (pastArray[i].state== selectedCityName ){
//                    pastArrayTemp.add(pastArray[i])
//                }
//            }
//        }
//    }

}


class MyViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
    private val fragmentList: MutableList<Fragment> = ArrayList()
    private val titleList: MutableList<String> = ArrayList()

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }


}