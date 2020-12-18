package com.example.android.flowersshop.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.flowersshop.model.UserData
import com.example.android.flowersshop.common.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserRepository {

    private val mAuth = FirebaseAuth.getInstance()

    fun loadUser(): LiveData<Event<UserData?>>{
        val userLiveData = MutableLiveData<Event<UserData?>>()
        userLiveData.postValue(Event.loading())

        val currentUser = mAuth.currentUser
        var base = FirebaseDatabase.getInstance().getReference("/users/" + currentUser?.uid)
        base.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user: UserData? = snapshot.getValue(UserData::class.java)
                userLiveData.postValue(Event.success(user))
            }

            override fun onCancelled(error: DatabaseError) {
                userLiveData.postValue(Event.error(error.toString()))
            }

        })
        return userLiveData
    }
}