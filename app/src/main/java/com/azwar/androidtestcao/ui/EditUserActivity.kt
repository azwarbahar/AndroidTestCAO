package com.azwar.androidtestcao.ui

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.DatePicker
import com.azwar.androidtestcao.R
import com.azwar.androidtestcao.databinding.ActivityEditUserBinding
import com.azwar.androidtestcao.models.User
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