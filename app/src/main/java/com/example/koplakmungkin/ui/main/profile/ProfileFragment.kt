package com.example.koplakmungkin.ui.main.profile

import ProfileViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.koplakmungkin.data.Result
import com.example.koplakmungkin.data.di.Injection
import com.example.koplakmungkin.data.response.UserProfileResponse
import com.example.koplakmungkin.databinding.FragmentProfileBinding
import com.example.koplakmungkin.ui.ViewModelFactory

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(Injection.provideRepository(requireContext()))
        ).get(ProfileViewModel::class.java)


        viewModel.userProfileResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    val userProfile = result.data
                    updateUI(userProfile)
                }

                is Result.Error -> {
                    showLoading(false)
                    val errorMessage = result.error
                    showError(errorMessage)
                }
            }
        }
        viewModel.getSession()
        setProfile()

        return root
    }

    private fun setProfile() {
        viewModel.sessionResult.observe(viewLifecycleOwner) { sessionData ->
            val token = sessionData.token
            val userId = sessionData.user_id
            if (userId != null) {
                viewModel.getUserProfile(token, userId)
            }
        }
    }

    private fun updateUI(userProfile: UserProfileResponse) {
        binding.textFullName.text = userProfile.data[0].fullname
        binding.textRole.text = userProfile.data[0].role
        binding.textAddress.text = userProfile.data[0].address
        binding.textBirth.text = userProfile.data[0].birth
        binding.textGender.text = userProfile.data[0].gender
    }

    private fun showError(errorMessage: String) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    companion object {
        // ...
    }
}
