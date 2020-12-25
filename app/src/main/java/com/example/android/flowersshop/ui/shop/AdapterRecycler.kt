package com.example.android.flowersshop.ui.shop

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.flowersshop.R
import com.example.android.flowersshop.model.Flower
import com.squareup.picasso.Picasso
import java.util.*

class AdapterRecycler(private val listener: FlowerListener) : RecyclerView.Adapter<AdapterRecycler.FlowerViewHolder>() {

    private var all_flowers: ArrayList<Flower> = ArrayList()

    interface FlowerListener {
        fun onFlowerUpd(position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.card_flower, parent, false)
        //вирівнювання cardView по центру.
        //застосовується тільки з gridLayoutManager
        if (view.layoutParams is GridLayoutManager.LayoutParams) {
            val params = view.layoutParams as GridLayoutManager.LayoutParams
            params.marginStart = (parent.measuredWidth / 2 - view.layoutParams.width) / 2
        }
        return FlowerViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: FlowerViewHolder, position: Int) {
        holder.bind(all_flowers[position])
    }
    fun setFlowersArrayList(flowersArrayList: ArrayList<Flower>) {
        this.all_flowers = flowersArrayList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return all_flowers.size
    }

    class FlowerViewHolder(itemView: View, listener:FlowerListener) : RecyclerView.ViewHolder(itemView) {
        var name_tv: TextView?
        var price_tv: TextView?
        var photo_flower: ImageView?
        var favorite: ImageView
        var buy: ImageView
        var flower: Flower? = null

        @SuppressLint("SetTextI18n")
        fun bind(flower: Flower) {
            this.flower = flower
            name_tv?.text = flower.name
            price_tv?.text = flower.price.toString() + " ₴"
            if (flower.isFavorite) {
                favorite.setImageResource(R.drawable.ic_favorite)
            } else {
                favorite.setImageResource(R.drawable.favorite)
            }
            if (!flower.isBuy!!) {
                buy.setImageResource(R.drawable.add)
            }
            else {
                buy.setImageResource(R.drawable.add_to_cart)
            }
            Picasso.get()
                    .load(flower.photoUrl)
                    .into(photo_flower)

        }

        init {
            name_tv = itemView.findViewById(R.id.name_flowers)
            price_tv = itemView.findViewById(R.id.price)
            photo_flower = itemView.findViewById(R.id.photo_flowers)
            buy = itemView.findViewById(R.id.add)
            favorite = itemView.findViewById(R.id.favorite)
            buy.setOnClickListener {
                flower?.isBuy = !flower?.isBuy!!
                listener.onFlowerUpd(adapterPosition)
            }
            favorite.setOnClickListener {
                flower?.isFavorite = !flower?.isFavorite!!
                listener.onFlowerUpd(adapterPosition)
            }
        }
    }
}