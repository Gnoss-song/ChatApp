package com.example.chatapp.view.hire

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentHireBinding

class HireFragment : Fragment() {
    lateinit var binding : FragmentHireBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHireBinding.inflate(inflater,container,false)
        return binding.root
    }
}