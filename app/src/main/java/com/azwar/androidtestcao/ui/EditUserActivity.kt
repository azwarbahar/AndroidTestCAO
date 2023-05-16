package com.azwar.androidtestcao.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import com.azwar.androidtestcao.R
import com.azwar.androidtestcao.databinding.ActivityEditUserBinding
import com.azwar.androidtestcao.models.User

class EditUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditUserBinding

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getParcelableExtra("user")!!
        initData(user)
        binding.tvCancel.setOnClickListener { finish() }

    }

    private fun initData(user: User) {
        binding.etFirstName.setText(user.firstName ?: "-")
        binding.etLastName.setText(user.lastName ?: "-")
        binding.etEmail.setText(user.email ?: "-")
        binding.etDob.setText(user.dob ?: "-")
    }
}