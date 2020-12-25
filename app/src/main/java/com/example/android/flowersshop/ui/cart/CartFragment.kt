package com.example.android.flowersshop.ui.cart

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android.flowersshop.R
import com.example.android.flowersshop.model.Flower
import com.example.android.flowersshop.ui.shop.AdapterRecycler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.card_buy_flower.view.*
import kotlinx.android.synthetic.main.fragment_basket.*


class CartFragment : Fragment() {

    private val sharedPrefFile = "stringFlower"

    private var all_flowers: ArrayList<Flower> = ArrayList()

    lateinit var buyAdapterRecycler: BuyRecyclerAdapter
    var all_flowers_price = 0
    var all_quantity_flowers = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gson = Gson()
        val sharedPreferences: SharedPreferences? = this.activity?.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  sharedPreferences!!.edit()

        buyAdapterRecycler = BuyRecyclerAdapter(object:AdapterRecycler.FlowerListener{
            override fun onFlowerUpd(position: Int) {
                editor.putString("All_flowers", gson.toJson(all_flowers))
                editor.apply()
                buyAdapterRecycler.notifyItemChanged(position)
            }
        })


        buy_flowers_recycle.adapter = buyAdapterRecycler



        val sharedNameValue = sharedPreferences?.getString("All_flowers","defaultname").toString()
        val myType = object : TypeToken<List<Flower>>() {}.type
        all_flowers = gson.fromJson(sharedNameValue, myType)
        buyAdapterRecycler.setAll_buy_flowers(all_flowers.filter { it.isBuy } as java.util.ArrayList<Flower>)
        var all_flowers_buy = all_flowers.filter {
            it.isBuy
        }
        all_flowers_buy.forEach {
            all_flowers_price += it.price
            all_quantity_flowers += it.quantity
        }
    }

}