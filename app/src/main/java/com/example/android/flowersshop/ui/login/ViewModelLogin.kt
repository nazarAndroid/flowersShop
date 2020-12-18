package com.example.android.flowersshop.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.flowersshop.common.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ViewModelLogin : ViewModel() {
    private var auth: FirebaseAuth? = null
    var user: MutableLiveData<Event<FirebaseUser?>> = MutableLiveData()

    fun loginAccount(email: String, password: String){
        auth = FirebaseAuth.getInstance()
        auth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                user.postValue(Event.success(auth?.currentUser))

            } else {
                user.postValue(Event.error(task.result.toString()))
            }
        }
    }
    fun isUserLogin(): FirebaseUser? {
        var authUser = FirebaseAuth.getInstance().currentUser
        return authUser
    }

}