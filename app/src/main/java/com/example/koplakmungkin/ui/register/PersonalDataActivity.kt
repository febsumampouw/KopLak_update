package com.example.koplakmungkin.ui.register

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koplakmungkin.BottomSheetAdapter
import com.example.koplakmungkin.R
import com.example.koplakmungkin.data.Result
import com.example.koplakmungkin.data.di.Injection
import com.example.koplakmungkin.data.response.ProfileResponse
import com.example.koplakmungkin.databinding.ActivityPersonalDataBinding
import com.example.koplakmungkin.ui.ViewModelFactory
import com.example.koplakmungkin.ui.main.profile.ProfileFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDragHandleView
import java.util.Calendar

class PersonalDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalDataBinding
    private lateinit var viewModel: PersonalDataViewModel
    private var selectedDate: String? = null
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, ViewModelFactory(Injection.provideRepository(this))).get(PersonalDataViewModel::class.java)

        val birthEditText = binding.personalDataLayout.birthEditText
        val birthEditTextLayout = binding.personalDataLayout.birthEditTextLayout

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                val formattedDate = String.format("%04d/%02d/%02d", year, monthOfYear + 1, dayOfMonth)
                birthEditText.setText(formattedDate)
                selectedDate = formattedDate
                birthEditTextLayout.hint = null
            },
            year,
            month,
            day
        )

        birthEditText.setOnClickListener {
            datePickerDialog.show()
        }


        binding.personalDataLayout.genderEditText.setOnClickListener {
            showBottomSheetGenderDialog()
        }

        binding.personalDataLayout.cityDomicileEditText.setOnClickListener {
            showBottomSheetCityDialog()
        }

        submitPersonalData()

        viewModel.profileResult.observe(this) {result ->
            when(result){
                is Result.Loading -> {
                    showLoading(true)
                    Log.d("tag", "submit profile loading")
                }
                is Result.Success ->{
                    showLoading(false)
                    val response: ProfileResponse = result.data
                    AlertDialog.Builder(this).apply {
                        setTitle("Mantep Profile!")
                        setMessage(response.status)
                        setPositiveButton("Lanjut"){ _, _ ->}
                        create()
                        show()
                        Log.d("tag", "submit profile success")
                    }
                    navigateToLogin()
                }
                is Result.Error ->{
                    showLoading(false)
                    val errorMessage: String = result.error
                    AlertDialog.Builder(this).apply {
                        setTitle("Oops")
                        setMessage(errorMessage)
                        setPositiveButton("OKE") { _, _ -> }
                        create()
                        show()
                        Log.d("tag", "submit profile error")
                    }
                }
            }
        }
    }

    private fun submitPersonalData(){
        binding.personalDataLayout.dataBtn.setOnClickListener {
            val imageProfile = "binding.personalDataLayout.chooseJobEditText.text.toString()"
            val fullname = binding.personalDataLayout.usernameEditText.text.toString()
            val address = binding.personalDataLayout.cityDomicileEditText.text.toString()
            val birth = binding.personalDataLayout.birthEditText.text.toString()
            val gender = binding.personalDataLayout.genderEditText.text.toString()

            viewModel.getSession()

            viewModel.sessionResult.observe(this) { sessionData ->
                Log.d("PersonalDataActivity", "Session data: $sessionData")
                val token = sessionData.token
                if (token.isNotEmpty()) {
                    viewModel.regisProfile(token, imageProfile, fullname, address, birth, gender)
                } else {
                    Log.e("PersonalDataActivity", "Invalid token")
                }
                }
        }
    }


    private fun navigateToLogin() {
        Log.d("tag", "submit profile navigateToLogin")
        val intent = Intent(this@PersonalDataActivity, ProfileFragment::class.java)
        startActivity(intent)
    }

    private fun showBottomSheetCityDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_choice_city, null)
        bottomSheetDialog.setContentView(bottomSheetView)

        val recyclerView = bottomSheetView.findViewById<RecyclerView>(R.id.rvItem)
        val searchView = bottomSheetView.findViewById<SearchView>(R.id.searchView)
        val dragHandle = bottomSheetView.findViewById<BottomSheetDragHandleView>(R.id.drag_handle)

        val adapter = BottomSheetAdapter { selectedCity ->
            binding.personalDataLayout.cityDomicileEditText.setText(selectedCity)
            bottomSheetDialog.dismiss()
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val cityList = listOf("Bontang", "Balikpapan", "Samarinda", "Sanggata", "Penajam")
        adapter.setCityList(cityList)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText.orEmpty())
                return true
            }
        })

        dragHandle.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    private fun showBottomSheetGenderDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_choice_gender, null)
        bottomSheetDialog.setContentView(bottomSheetView)

        val recyclerView = bottomSheetView.findViewById<RecyclerView>(R.id.rvItem)

        val adapter = BottomSheetAdapter { selectedJob ->
            binding.personalDataLayout.genderEditText.setText(selectedJob)
            bottomSheetDialog.dismiss()
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val genderList = listOf("male", "female")
        adapter.submitList(genderList)


        bottomSheetDialog.show()
    }
    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                personalDataLayout.progressBar.visibility = View.VISIBLE
            } else {
                personalDataLayout.progressBar.visibility = View.GONE
            }
        }
    }
}
