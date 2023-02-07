package com.bronyst.room.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bronyst.room.R
import com.bronyst.room.data.User
import com.bronyst.room.data.UserViewModel
import com.bronyst.room.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {
    private lateinit var mUserViewModel: UserViewModel
    private  val args by navArgs<UpdateFragmentArgs>()
    private var _binding: FragmentUpdateBinding? = null
     private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.updateButton.setOnClickListener{
            updateUser()
        }

        binding.edtFirstName.setText(args.currentUser.firstName)
        binding.edtLastname.setText(args.currentUser.lastName)
        binding.edtAge.setText(args.currentUser.age.toString())

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_menu){
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("YES"){_, _ ->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(), "${args.currentUser.firstName} deleted successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment2_to_listFragment)
        }
        builder.setNegativeButton("NO"){_, _, ->}
        builder.setTitle("Delete ${args.currentUser.firstName}?")
        builder.setMessage("Are you sure you want to delete ${args.currentUser.firstName}?")
        builder.create().show()
    }

    private fun updateUser() {
        val firstName = binding.edtFirstName.text.toString()
        val lastName = binding.edtLastname.text.toString()
        val age = Integer.parseInt(binding.edtAge.text.toString())

        if(inputCheck(firstName, lastName, binding.edtAge.text)){
//            create user object
            val user = User(args.currentUser.id, firstName, lastName, age.toString())
            mUserViewModel.updateUser(user)
            Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()
//            navigate to list
            findNavController().navigate(R.id.action_updateFragment2_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
        }
    }

    fun inputCheck(firstName: String, lastName:String, age: Editable) : Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }




}