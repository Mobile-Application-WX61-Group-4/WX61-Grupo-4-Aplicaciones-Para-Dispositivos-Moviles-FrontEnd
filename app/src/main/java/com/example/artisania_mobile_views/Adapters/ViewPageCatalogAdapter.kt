package com.example.artisania_mobile_views.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.artisania_mobile_views.Fragments.ProductListFragment
import com.example.artisania_mobile_views.Fragments.RecyclerViewFragment
import com.example.artisania_mobile_views.models.Product

class ViewPageCatalogAdapter(
    fragmentActivity: FragmentActivity,
    private val products: List<Product>
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        val category = when (position) {
            0 -> "Accesorios"
            1 -> "Ropa"
            2 -> "Decoración"
            3 -> "Joyería"
            4 -> "Arte"
            else -> ""
        }
        val filteredProducts = products.filter { it.categoria == category }
        return RecyclerViewFragment.newInstance(filteredProducts)


    }
}