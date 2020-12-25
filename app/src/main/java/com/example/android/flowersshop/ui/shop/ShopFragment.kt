package com.example.android.flowersshop.ui.shop

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.flowersshop.R
import com.example.android.flowersshop.common.Status
import com.example.android.flowersshop.model.Flower
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_shop.*


class ShopFragment : Fragment() {

    private lateinit var viewModel: FlowersViewModel

    var bouquetAdapterRecycler: AdapterRecycler? = null
    var singleAdapterRecycler: AdapterRecycler? = null


    private val sharedPrefFile = "stringFlower"

    private var all_flowers: ArrayList<Flower>? = ArrayList()
    var gson: Gson = Gson()
    var sharedPreferences: SharedPreferences? = null
    lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FlowersViewModel::class.java)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        gson = Gson()
        sharedPreferences = this.activity?.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()!!


        horizontal_recycle.layoutManager = layoutManager

        bouquetAdapterRecycler = AdapterRecycler(object : AdapterRecycler.FlowerListener {
            override fun onFlowerUpd(position: Int) {
                editor.putString("All_flowers", gson.toJson(all_flowers))
                editor.apply()
                bouquetAdapterRecycler?.notifyItemChanged(position)
            }
        })
        singleAdapterRecycler = AdapterRecycler(object : AdapterRecycler.FlowerListener {
            override fun onFlowerUpd(position: Int) {
                editor.putString("All_flowers", gson.toJson(all_flowers))
                editor.apply()
                singleAdapterRecycler?.notifyItemChanged(position)
            }
        })

        vertical_recycle.adapter = bouquetAdapterRecycler
        horizontal_recycle.adapter = singleAdapterRecycler


        viewModel.loadFlowers().observe(viewLifecycleOwner, Observer { event ->
            when (event.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    event.data?.let {

                        all_flowers = it
                        singleAdapterRecycler!!.setFlowersArrayList(ArrayList(it.filter {
                            !it.isBouquet
                        }))
                        bouquetAdapterRecycler!!.setFlowersArrayList(ArrayList(it.filter {
                            it.isBouquet
                        }))
                        editor.putString("All_flowers", gson.toJson(all_flowers))
                        editor.apply()
                    }
                }
                Status.ERROR -> {
                }
            }
        })
    }
}