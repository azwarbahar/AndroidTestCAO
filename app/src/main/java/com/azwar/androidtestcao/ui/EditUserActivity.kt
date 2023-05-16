package com.azwar.androidtestcao.ui

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.DatePicker
import android.widget.Toast
import com.azwar.androidtestcao.R
import com.azwar.androidtestcao.databinding.ActivityEditUserBinding
import com.azwar.androidtestcao.models.User
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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

        binding.etDob.setOnClickListener {
            showDatePickerDialog()
        }

        binding.tvSave.setOnClickListener {

            val updatedUser = User(
                user?.id ?: "",
                binding.etFirstName.text.toString(),
                binding.etLastName.text.toString(),
                binding.etEmail.text.toString(),
                binding.etDob.text.toString()
            )
            // Memperbarui data JSON
            updateUserJson(updatedUser)
        }

    }

    private fun updateUserJson(updatedUser: User) {
        try {
            val inputStream = assets.open("data.json")
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val jsonStringBuilder = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                jsonStringBuilder.append(line)
            }
            bufferedReader.close()

            val userListType = object : TypeToken<List<User>>() {}.type
            val userList: MutableList<User> =
                Gson().fromJson(jsonStringBuilder.toString(), userListType)

            val userIndex = userList.indexOfFirst { it.id == updatedUser.id }
            if (userIndex != -1) {
                userList[userIndex] = updatedUser
            }

            val json = GsonBuilder().setPrettyPrinting().create().toJson(userList)

            val outputStreamWriter =
                OutputStreamWriter(openFileOutput("data.json", Context.MODE_PRIVATE))
            outputStreamWriter.write(json)
            outputStreamWriter.close()
            Toast.makeText(this, "Berhasil Memperbaharui Data!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showDatePickerDialog() {
        val c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                var dayfix = "" + dayOfMonth;
                var monthfix = "" + monthOfYear;

                if (monthOfYear < 10) {
                    monthfix = "0" + monthfix;
                }
                if (dayOfMonth < 10) {
                    dayfix = "0" + dayfix;
                }
                binding.etDob.setText("$dayfix/$monthfix/$year")

            }, year, month, day
        )

        dpd.show()
    }

    private fun initData(user: User) {
        binding.etFirstName.setText(user.firstName ?: "")
        binding.etLastName.setText(user.lastName ?: "")
        binding.etEmail.setText(user.email ?: "")
        binding.etDob.setText(user.dob ?: "")
    }
}