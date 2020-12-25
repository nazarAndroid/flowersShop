package com.example.android.flowersshop.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.android.flowersshop.R
import com.example.android.flowersshop.model.Flower
import com.example.android.flowersshop.ui.shop.AdapterRecycler
import com.squareup.picasso.Picasso
import java.util.*

class BuyRecyclerAdapter(private val listener: AdapterRecycler.FlowerListener) : RecyclerView.Adapter<BuyRecyclerAdapter.BuyFlowerViewHolder>() {

    private var all_buy_flowers: ArrayList<Flower> = ArrayList<Flower>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyFlowerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.card_buy_flower, parent, false)
        return BuyFlowerViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: BuyFlowerViewHolder, position: Int) {
        holder.bind(all_buy_flowers[position])
    }

    override fun getItemCount(): Int {
        return all_buy_flowers.size
    }

    class BuyFlowerViewHolder(itemView: View, listener: AdapterRecycler.FlowerListener) : RecyclerView.ViewHolder(itemView) {
        var name_tv: TextView
        var price_tv: TextView
        var photo_flower: ImageView
        var flower: Flower? = null
        var number_tv: TextView
        var plus: ImageView
        var minus: ImageView
        var delete_flower: ImageView
        var number = 0
        var all_price: TextView
        var all_quantity: TextView
        @SuppressLint("SetTextI18n")
        fun bind(flower: Flower) {
            this.flower = flower
            number_tv.text = flower.quantity.toString() + ""
            name_tv.text = flower.name
            price_tv.text = (flower.price * flower.quantity).toString() + " ₴"
            all_price.text = (flower.price * flower.quantity).toString()
            all_quantity.text = flower.quantity.toString()
            Picasso.get()
                    .load(flower.photoUrl)
                    .into(photo_flower)
        }

        init {
            name_tv = itemView.findViewById(R.id.name_flowers)
            price_tv = itemView.findViewById(R.id.price)
            photo_flower = itemView.findViewById(R.id.photo_flowers)
            number_tv = itemView.findViewById(R.id.number)
            plus = itemView.findViewById(R.id.plus)
            minus = itemView.findViewById(R.id.minus)
            all_price = itemView.findViewById(R.id.all_price)
            all_quantity = itemView.findViewById(R.id.all_quantity)
            delete_flower = itemView.findViewById(R.id.delete_flower)
            delete_flower.setOnClickListener {
                flower?.isBuy = false
                flower?.quantity = 1
                listener.onFlowerUpd(adapterPosition)
            }
            plus.setOnClickListener { view ->
                number = number_tv.text.toString().toInt()
                if (number <= 100) {
                    number++
                    flower?.quantity = number
                    listener.onFlowerUpd(adapterPosition)
                } else {
                    Toast.makeText(view.context, "Максимально 100 одиниць", Toast.LENGTH_LONG)
                }
            }
            minus.setOnClickListener {
                number = number_tv.text.toString().toInt()
                if (number >= 1) {
                    number--
                    flower?.quantity = number
                    listener.onFlowerUpd(adapterPosition)
                }
            }


        }
    }

    interface FlowerListener {
        fun onFlowerUpd(flower: Flower?)
    }

    fun setAll_buy_flowers(all_flowers: ArrayList<Flower>) {
        all_buy_flowers = all_flowers
        notifyDataSetChanged()
    }
}