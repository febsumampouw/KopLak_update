package com.example.koplakmungkin.ui.main.koprasell

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koplakmungkin.data.model.FeedData
import com.example.koplakmungkin.databinding.FragmentJualKopraBinding
import com.example.koplakmungkin.ui.main.koprasell.createfeed.CreateFeedActivity
import com.example.koplakmungkin.ui.main.koprasell.createnews.CreateNewsActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class JualKopraFragment : Fragment() {
    private var _binding: FragmentJualKopraBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: JualKopraAdapter

    private var isAllFabsVisible: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentJualKopraBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.rvFeeds
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = JualKopraAdapter(emptyList())
        recyclerView.adapter = adapter

        setUpFabs()
        loadDataFromFirebase()
        navigateToCreateFeed()
        navigateToCreateNews()
        return root

    }

    private fun setUpFabs(){
        binding.createFeedButton.visibility = GONE
        binding.createNews.visibility = GONE
        binding.addJualActionText.visibility = GONE
        binding.addNewsActionText.visibility = GONE

        isAllFabsVisible = false
        binding.addFab.setOnClickListener {
            if (!isAllFabsVisible!!) {
                showFabs()
                isAllFabsVisible = true
            } else {
                hideFabs()
                isAllFabsVisible = false
            }
        }
    }
    private fun showFabs(){
        with(binding) {
            createNews.show()
            createFeedButton.show()
            addJualActionText.visibility = VISIBLE
            addNewsActionText.visibility = VISIBLE
        }
    }
    private fun hideFabs(){
        with(binding){
            createNews.hide()
            createFeedButton.hide()
            addJualActionText.visibility = GONE
            addNewsActionText.visibility = GONE
        }
    }
    private fun navigateToCreateFeed(){
        binding.createFeedButton.setOnClickListener {
            val intent = Intent(binding.root.context, CreateFeedActivity::class.java)
            startActivity(intent)
        }
    }
    private fun navigateToCreateNews(){
        binding.createNews.setOnClickListener {
            val intent = Intent(binding.root.context, CreateNewsActivity::class.java)
            startActivity(intent)
        }
    }
    private fun loadDataFromFirebase(){
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("feeds")

        reference.addValueEventListener(object: ValueEventListener{
           override fun onDataChange(snapshot: DataSnapshot){
               val feeds = mutableListOf<FeedData>()
               for (childSnapshot in snapshot.children){
                   val feed = childSnapshot.getValue(FeedData::class.java)
                   feed?.let {
                       feeds.add(it)
                   }
               }
               adapter.updateData(feeds)
           }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private companion object{
        private const val VISIBLE = View.VISIBLE
        private const val GONE = View.GONE
    }
}