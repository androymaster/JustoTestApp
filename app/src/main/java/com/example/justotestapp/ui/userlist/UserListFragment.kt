package com.example.justotestapp.ui.userlist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justotestapp.R
import com.example.justotestapp.core.Resource
import com.example.justotestapp.data.local.AppDataBase
import com.example.justotestapp.data.local.LocalUserDataSources
import com.example.justotestapp.data.model.*
import com.example.justotestapp.data.remote.RemoteUserDataSources
import com.example.justotestapp.databinding.FragmentUserListBinding
import com.example.justotestapp.presentation.UserViewModel
import com.example.justotestapp.presentation.UserViewModelFactory
import com.example.justotestapp.repository.RetrofitClient
import com.example.justotestapp.repository.UserRepositoryImpl
import com.example.justotestapp.ui.userlist.adapters.UserAdapter
import com.google.gson.Gson
import kotlinx.coroutines.launch

class UserListFragment : Fragment(R.layout.fragment_user_list), UserAdapter.OnUserClickListener {

    private lateinit var binding: FragmentUserListBinding
    private val myAdapter = UserAdapter(this)

    private val viewModel by viewModels<UserViewModel> {
        UserViewModelFactory(
            UserRepositoryImpl(
                RemoteUserDataSources(RetrofitClient.webservice),
                LocalUserDataSources(AppDataBase.getDatabase(requireContext()).usersDao())
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserListBinding.bind(view)

        setupRecyclerView()
        setupChangeList()
    }

    private fun getNewUserRemote(){
        viewModel.getUsersRemote().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("MyUserRemote", result.data.toUser().toString())
                    if (result.data.results.isNotEmpty()){
                        saveLastUser(listOf(result.data.toUser()!!))
                    }
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error al traer los datos ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun setupChangeList() {
        lifecycleScope.launchWhenResumed {
            viewModel.getUserLocal().observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.addUser.setOnClickListener {
                            getNewUserRemote()
                        }
                        if (result.data.isNotEmpty()){
                            myAdapter.apply {
                                submitList(result.data)
                                binding.message.visibility = View.GONE
                            }
                        }else{
                            binding.message.visibility = View.VISIBLE
                        }
                    }
                    is Resource.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "Ocurrio un error al traer los datos ${result.exception}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }
    }

    private fun saveLastUser(userList: List<UserEntity>) {
        if (userList.isNotEmpty()){
            myAdapter.submitList(userList)
            val user = userList.last()
            saveNewUser(
                user = UserEntity(
                    first = user.first,
                    last = user.last,
                    location = user.location,
                    state = user.state,
                    thumbnail = user.thumbnail,
                    latitude = user.latitude,
                    longitude =user.longitude
                )
            )
        }else{
            binding.message.visibility = View.VISIBLE
        }
    }

    private fun saveNewUser(user: UserEntity){
            viewModel.addUser(user)
            setupChangeList()
            Toast.makeText(context,"Se agrego un nuevo usuario", Toast.LENGTH_SHORT).show()
    }

    private fun setupRecyclerView(){
       binding.rvUser.adapter = myAdapter
       binding.rvUser.layoutManager = LinearLayoutManager(requireContext().applicationContext)
    }

    override fun onUserClick(user: UserEntity) {
        val bundle = Bundle()
        bundle.putParcelable("user", user.toUser())
        findNavController().navigate(R.id.action_userListFragment_to_userDetailFragment, bundle)
    }
}