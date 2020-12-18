package com.example.android.flowersshop.ui.profile

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.flowersshop.R
import com.example.android.flowersshop.common.Status
import com.example.android.flowersshop.model.UserData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ViewModelUser

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edit_profile.setOnClickListener {
            showDialog()
        }

        viewModel = ViewModelProviders.of(this).get(ViewModelUser::class.java)

        viewModel.loadData()

        viewModel.loadData().observe(viewLifecycleOwner, Observer { event ->
            when (event.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    showUser(event.data)
                }
                Status.ERROR -> {
                }
            }
        })
    }
    private fun showUser(user: UserData?) {
        if (user != null) {
            set_email.text = user.email
            set_name.text = user.name
            set_number.text = user.number
        }
    }


    private fun showDialog() {
        var base: DatabaseReference?
        val dialog = activity?.let { Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.layout_dialog)
        dialog?.show()
        val save = dialog?.findViewById(R.id.save_profile) as FloatingActionButton
        val name = dialog?.findViewById(R.id.name_edit) as EditText
        val number = dialog?.findViewById(R.id.number_edit) as EditText
        save.setOnClickListener {
            val mAuth = FirebaseAuth.getInstance()
            val currentUser = mAuth.currentUser
            base = FirebaseDatabase.getInstance().reference
            base!!.child("users").child(currentUser!!.uid).child("number").setValue(number.text.toString())
            base!!.child("users").child(currentUser.uid).child("name").setValue(name.text.toString())
            dialog?.dismiss()
        }
    }
}