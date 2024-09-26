package com.example.artisania_mobile_views.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.artisania_mobile_views.Fragments.RecyclerViewFragment

class ViewPageCatalogAdapter (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return RecyclerViewFragment.newInstance(position)
    }
}