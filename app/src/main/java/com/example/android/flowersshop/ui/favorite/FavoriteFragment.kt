package com.example.android.flowersshop.ui.favorite

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.flowersshop.R
import com.example.android.flowersshop.model.Flower
import com.example.android.flowersshop.ui.shop.AdapterRecycler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {

    lateinit var favoriteAdapterRecycler: AdapterRecycler
    private val sharedPrefFile = "stringFlower"

    private var all_flowers: ArrayList<Flower> = ArrayList()
    private var all_flowers_sort: ArrayList<Flower> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences: SharedPreferences? = this.activity?.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        val gson = Gson()
        favoriteAdapterRecycler = AdapterRecycler(object:AdapterRecycler.FlowerListener{
            override fun onFlowerUpd(position: Int) {
                favoriteAdapterRecycler.notifyItemChanged(position)
            }
        })

        favorite_flowers_recycle.adapter = favoriteAdapterRecycler

        val sharedNameValue = sharedPreferences?.getString("All_flowers","defaultname").toString()
        val myType = object : TypeToken<List<Flower>>() {}.type
        all_flowers = gson.fromJson<ArrayList<Flower>>(sharedNameValue, myType)
        all_flowers_sort = all_flowers.filter { it.isFavorite }as java.util.ArrayList<Flower>
        favoriteAdapterRecycler.setFlowersArrayList(all_flowers_sort)

    }


}