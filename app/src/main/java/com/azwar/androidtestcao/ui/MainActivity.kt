package com.azwar.androidtestcao.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.azwar.androidtestcao.R
import com.azwar.androidtestcao.adapters.UserAdapter
import com.azwar.androidtestcao.databinding.ActivityMainBinding
import com.azwar.androidtestcao.models.User
import com.azwar.androidtestcao.viewmodels.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: ActivityMainBinding

    private val userViewModel: UserViewModel by viewModel()

    private lateinit var userAdapter: UserAdapter

    private lateinit var swipe_refresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        swipe_refresh = binding.swipeRefresh
        swipe_refresh.setOnRefreshListener(this)
        swipe_refresh.setColorSchemeResources(
            R.color.primaryColor,
            android.R.color.holo_blue_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_green_dark
        )
        swipe_refresh.post(Runnable {
            loadData()
        })

        binding.imgAdd.setOnClickListener {
            Toast.makeText(this, "Action Click Add", Toast.LENGTH_SHORT).show()
        }

        binding.imgSearch.setOnClickListener {
            Toast.makeText(this, "Action Click Search", Toast.LENGTH_SHORT).show()
        }

    }

    private fun loadData() {

        var rv_user = binding.rvUser
        userViewModel.users.observe(this, Observer { users ->
            rv_user.layoutManager = GridLayoutManager(this, 2)
            userAdapter = UserAdapter(users)
            rv_user.adapter = userAdapter
            userAdapter.notifyDataSetChanged()
        })

        swipe_refresh.isRefreshing = false
        userViewModel.loadUsers(this)

    }

    override fun onResume() {
        super.onResume()
        userViewModel.loadUsers(this)
    }

    override fun onRefresh() {
        loadData()
    }
}