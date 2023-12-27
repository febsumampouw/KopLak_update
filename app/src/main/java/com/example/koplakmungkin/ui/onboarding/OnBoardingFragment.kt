package com.example.koplakmungkin.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.koplakmungkin.databinding.FragmentOnBoardingBinding


class OnBoardingFragment(private val page: Page) : Fragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.titleOB.text = getString(page.title)
        binding.descOB.text = getString(page.desc)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
