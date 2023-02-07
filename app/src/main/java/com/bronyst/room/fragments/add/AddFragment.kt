package com.bronyst.room.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bronyst.room.R
import com.bronyst.room.data.User
import com.bronyst.room.data.UserViewModel


class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.findViewById<Button>(R.id.button).setOnClickListener{
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
          val firstName = view?.findViewById<EditText>(R.id.edt_first_name)?.text.toString()
        val lastName = view?.findViewById<EditText>(R.id.edt_lastname)?.text.toString()
        val age = view?.findViewById<EditText>(R.id.edt_age)?.text.toString()

        if(inputCheck(firstName, lastName, age)){
//            create user object
            val user = User(0, firstName, lastName, age)
            mUserViewModel.insertUser(user)
            Toast.makeText(requireContext(), "Successfully created", Toast.LENGTH_SHORT).show()
//            navigate to list
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
        }
    }

    fun inputCheck(firstName: String, lastName:String, age: String) : Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(age))
    }

}