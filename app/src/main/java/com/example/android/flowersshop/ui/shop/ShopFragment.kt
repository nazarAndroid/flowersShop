package com.example.android.flowersshop.ui.shop

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
import kotlinx.android.synthetic.main.fragment_shop.*

class ShopFragment : Fragment() {

    private lateinit var viewModel: FlowersViewModel

    var bouquetAdapterRecycle: AdapterRecycle? = null
    var singleAdapterRecycle: AdapterRecycle? = null

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

        horizontal_recycle.layoutManager = layoutManager
        bouquetAdapterRecycle = AdapterRecycle()
        singleAdapterRecycle = AdapterRecycle()

        vertical_recycle.adapter = bouquetAdapterRecycle
        horizontal_recycle.adapter = singleAdapterRecycle


        viewModel.loadFlowers().observe(viewLifecycleOwner, Observer { event ->
            when (event.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    event.data?.let { singleAdapterRecycle!!.setFlowersArrayList(it) }
                }
                Status.ERROR -> {
                }
            }
        })

        viewModel.loadBouquetFlowers().observe(viewLifecycleOwner, Observer { event ->
            when (event.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    event.data?.let { bouquetAdapterRecycle!!.setFlowersArrayList(it) }
                }
                Status.ERROR -> {
                }
            }
        })

    }

}