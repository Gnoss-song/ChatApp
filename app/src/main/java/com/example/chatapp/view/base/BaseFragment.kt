package com.example.chatapp.view.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentBaseBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BaseFragment : Fragment() {

    @Inject
    lateinit var userManager: com.example.chatapp.util.UserManager

    lateinit var binding: FragmentBaseBinding

    private lateinit var navHostFragment: NavHostFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navHostFragment = childFragmentManager.findFragmentById(R.id.navi_host) as NavHostFragment
        setBottomNavigation()
    }

    //setting bottom navigation : Home, Scrap, MyPage, Community
    private fun setBottomNavigation() {
        binding.bottomNavigation.apply {
            setupWithNavController(navHostFragment.findNavController())
            itemIconTintList = null
            setOnItemSelectedListener { item ->
                return@setOnItemSelectedListener if (binding.bottomNavigation.selectedItemId != item.itemId) {
                    if (checkLogin(item.itemId)) {
                        NavigationUI.onNavDestinationSelected(
                            item, navHostFragment.findNavController()
                        )
                        true
                    } else {
                        false
                    }
                } else {
                    true
                }
            }
        }
    }

    private fun checkLogin(itemId: Int): Boolean {
        return when (itemId) {
            R.id.chatFragment, R.id.myPageFragment -> {
                userManager.checkLogin(requireActivity())
            }
            else -> {
                true
            }
        }
    }
}