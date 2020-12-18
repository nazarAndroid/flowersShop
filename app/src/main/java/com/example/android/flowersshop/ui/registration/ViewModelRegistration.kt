package com.example.android.flowersshop.ui.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.flowersshop.common.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class ViewModelRegistration:ViewModel() {
    private var databaseAuth: FirebaseAuth? = null
    var userRegistration: MutableLiveData<Event<FirebaseUser?>> = MutableLiveData()
    fun registrationUser(email:String,password:String,number: String){

        val base = FirebaseDatabase.getInstance().reference

        databaseAuth = FirebaseAuth.getInstance()
        databaseAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener {
            task ->
            if (task.isSuccessful) {
                userRegistration.postValue(Event.success(databaseAuth?.currentUser))
                databaseAuth?.currentUser?.uid?.let { base.child("users").child(it).child("number").setValue(number) }
                databaseAuth?.currentUser?.uid?.let { base.child("users").child(it).child("email").setValue(email) }

            } else {
                userRegistration.postValue(Event.error(task.result.toString()))
            }
        }
    }
}