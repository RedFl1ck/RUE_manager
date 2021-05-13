package com.example.reumanager.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.reumanager.ui.accounting.PersonalAccounting
import com.example.reumanager.ui.qualification.QualificationOfEmployees

@Suppress("DEPRECATION")
internal class RecoveryTabsAdapter(
    fm: FragmentManager,
    var totalTabs: Int
) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                PersonalAccounting()
            }
            1 -> {
                QualificationOfEmployees()
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}