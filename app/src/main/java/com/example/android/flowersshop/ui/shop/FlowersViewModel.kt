package com.example.android.flowersshop.ui.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.flowersshop.common.Event
import com.example.android.flowersshop.model.Flower
import com.example.android.flowersshop.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class FlowersViewModel : ViewModel() {

    fun loadFlowers(): LiveData<Event<ArrayList<Flower>>> {
        val flowersLiveData = MutableLiveData<Event<ArrayList<Flower>>>()
        flowersLiveData.postValue(Event.loading())
        var flowerList: ArrayList<Flower> = ArrayList()

        val database = FirebaseDatabase.getInstance()
        database.getReference("flowers/flowersApiece").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val flower: Flower = postSnapshot.getValue(Flower::class.java)!!
                    flowerList.add(flower)
                    flowersLiveData.postValue(Event.success(flowerList))
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        return flowersLiveData
    }
    fun loadBouquetFlowers(): LiveData<Event<ArrayList<Flower>>> {
        val flowersLiveData = MutableLiveData<Event<ArrayList<Flower>>>()
        flowersLiveData.postValue(Event.loading())
        var flowerList: ArrayList<Flower> = ArrayList()

        val database = FirebaseDatabase.getInstance()
        database.getReference("flowers/bouquet").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val flower: Flower = postSnapshot.getValue(Flower::class.java)!!
                    flowerList.add(flower)
                    flowersLiveData.postValue(Event.success(flowerList))
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        return flowersLiveData
    }
}