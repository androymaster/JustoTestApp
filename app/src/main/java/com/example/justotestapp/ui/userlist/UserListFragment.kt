package com.example.justotestapp.ui.userlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justotestapp.R
import com.example.justotestapp.core.Resource
import com.example.justotestapp.data.model.DataUser
import com.example.justotestapp.data.remote.UserDataSources
import com.example.justotestapp.databinding.FragmentUserListBinding
import com.example.justotestapp.presentation.UserViewModel
import com.example.justotestapp.presentation.UserViewModelFactory
import com.example.justotestapp.repository.RetrofitClient
import com.example.justotestapp.repository.UserRepositoryImpl
import com.example.justotestapp.ui.userlist.adapters.UserAdapter

class UserListFragment : Fragment(R.layout.fragment_user_list), UserAdapter.CellClickListener {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var myAdapter: UserAdapter

    private val viewModel by viewModels<UserViewModel> {
        UserViewModelFactory(
            UserRepositoryImpl(
                UserDataSources(RetrofitClient.webservice)
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserListBinding.bind(view)

        viewModel.getUsersFemale().observe(viewLifecycleOwner,{ result ->
            when(result){
               is Resource.Loading -> {
                   binding.progressBar.visibility = View.VISIBLE
               }
               is Resource.Success -> {
                   binding.progressBar.visibility = View.GONE
                   if (result.data.results.isEmpty()){
                       return@observe
                   }else{
                       binding.progressBar.visibility = View.VISIBLE
                   }
                   val data = listOf(result.data)
                   myAdapter = UserAdapter(data, this)
                   binding.rvUser.adapter = myAdapter
               }
               is Resource.Failure -> {
                   binding.progressBar.visibility = View.GONE
               }
            }
        })
    }

    override fun onCellClickListener(data: DataUser) {
        val action = UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(
            data.results[0].picture.thumbnail!!,
            data.results[0].name.first!!,
            data.results[0].gender!!,
            data.results[0].email!!,
            data.results[0].phone!!,
            data.results[0].cell!!,
            data.results[0].id.name!!,
            data.results[0].nat!!,
            data.results[0].location.street.number!!,
            data.results[0].location.street.name!!,
            data.results[0].location.city!!,
            data.results[0].location.state!!,
            data.results[0].location.coordinates.latitude!!,
            data.results[0].location.coordinates.longitude!!
            )
        findNavController().navigate(action)
    }

}