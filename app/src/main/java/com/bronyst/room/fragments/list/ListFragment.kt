package com.bronyst.room.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bronyst.room.R
import com.bronyst.room.R.id.action_listFragment_to_addFragment
import com.bronyst.room.data.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var mUserViewModel: UserViewModel
    val adapter = ListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_list, container, false)

       view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
           findNavController().navigate(action_listFragment_to_addFragment)
       }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.getAllUsers.observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user)
        })

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        inflater.inflate(R.menu.search_menu, menu)

        val search = menu?.findItem(R.id.search_menu)
        val searchView = search?.actionView as SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_menu){
            deleteAllUsers()
        }
        if (item.itemId == R.id.search_menu){

        }
        return super.onOptionsItemSelected(item)
    }



    private fun deleteAllUsers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("YES"){_,_ ->
            mUserViewModel.deleteAll()
            Toast.makeText(requireContext(), "All users deleted", Toast.LENGTH_SHORT).show()
//            findNavController().navigate()
        }
        builder.setNegativeButton("NO"){_, _ ->}
        builder.setTitle("Delete all users")
        builder.setMessage("Are you sure you want to delete all users?")
        builder.create().show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }

        return  true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }

        return true
    }

    private fun searchDatabase(searchQuery: String){
        val searchQuery = "%$searchQuery%"

        mUserViewModel.searchDatabase(searchQuery).observe(this) { list ->
            list.let {
                adapter.setData(it)
            }
        }
    }

}