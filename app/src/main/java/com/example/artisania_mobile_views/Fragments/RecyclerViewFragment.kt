package com.example.artisania_mobile_views.Fragments
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artisania_mobile_views.Adapter.HorizontalRecyclerView
import com.example.artisania_mobile_views.R
class RecyclerViewFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HorizontalRecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_view, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        adapter = HorizontalRecyclerView()
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        return view
    }

    companion object {
        fun newInstance(position: Int): RecyclerViewFragment {
            val fragment = RecyclerViewFragment()
            val args = Bundle()
            args.putInt("position", position)
            fragment.arguments = args
            return fragment
        }
    }
}