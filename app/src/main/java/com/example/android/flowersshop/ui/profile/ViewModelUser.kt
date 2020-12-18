package com.example.android.flowersshop.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.android.flowersshop.model.UserData
import com.example.android.flowersshop.common.Event

class ViewModelUser : ViewModel(){
    private val repository: UserRepository = UserRepository()
    fun loadData(): LiveData<Event<UserData?>> {
        return repository.loadUser()
    }
}