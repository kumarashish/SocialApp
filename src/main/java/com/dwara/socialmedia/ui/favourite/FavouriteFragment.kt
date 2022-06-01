package com.dwara.socialmedia.ui.favourite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dwara.socialmedia.R
import com.dwara.socialmedia.databinding.FragmentFavouriteBinding
import com.dwara.socialmedia.room.Post
import com.dwara.socialmedia.room.SocialDB
import com.dwara.socialmedia.ui.comments.Comment
import com.dwara.socialmedia.ui.posts.PostAdapter
import com.google.gson.Gson

class FavouriteFragment : Fragment(),FavoriteAdapter.CommentClickListener {

    private lateinit var favouriteViewModel: FavouriteViewModel
    private var _binding: FragmentFavouriteBinding? = null
    private var listner: FavoriteAdapter.CommentClickListener?=null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listner=this
        favouriteViewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager= layoutManager
        getFavourite()
        return root
    }

    fun getFavourite() {
        val favList = SocialDB.responseDao().getAllFavorite(1)
        if (favList == null || favList.size==0) {
            binding.textNoData.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.textNoData.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            binding.recyclerView.adapter = FavoriteAdapter(favList, listner!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(data: Post) {
        val intent =Intent(activity, Comment::class.java)
        intent.putExtra("data", Gson().toJson(data).toString())
        startActivity(intent)
    }
}