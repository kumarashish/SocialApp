package com.dwara.socialmedia.ui.posts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dwara.socialmedia.R
import com.dwara.socialmedia.common.Common
import com.dwara.socialmedia.databinding.FragmentPostBinding
import com.dwara.socialmedia.network.APIResult
import com.dwara.socialmedia.room.Database
import com.dwara.socialmedia.room.Post
import com.dwara.socialmedia.room.SocialDB
import com.dwara.socialmedia.ui.comments.Comment
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class PostsFragment : Fragment(), PostAdapter.CommentClickListener {

    private lateinit var postsViewModel: PostsViewModel
    private var _binding: FragmentPostBinding? = null
    private var listner: PostAdapter.CommentClickListener? = null
    private var isApiCalled=false

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listner = this
        postsViewModel =
            ViewModelProvider(this).get(PostsViewModel::class.java)

        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.progressView.progressLayout.visibility = View.GONE
        dataObserver()
        getList()
        return root
    }

    fun getList() {
        val list=SocialDB.responseDao().getAll()
        if (list == null||list.size==0) {
            if (Common.isInternetConnected(requireContext())) {
                isApiCalled=true
                postsViewModel.getPostDetails()
            }
        } else {
            binding.progressView.progressLayout.visibility = View.GONE
            binding.recyclerView.adapter = PostAdapter(list, listner!!)
        }
    }


    private fun dataObserver() {
        if (isApiCalled) {
            binding!!.progressView.progressLayout.visibility = View.VISIBLE
        }
        postsViewModel.postDetails.observe(viewLifecycleOwner, Observer {
            when (it) {
                is APIResult.Success -> {
                    if (isApiCalled) {
                        isApiCalled = false
                        binding.progressView.progressLayout.visibility = View.GONE
                        if (it.data.body()!!.size > 0) {

                            SocialDB.responseDao().insertAll(it.data.body()!!)
                            binding.recyclerView.adapter = PostAdapter(it.data.body(), listner!!)
                        }
                    }
                }
                is APIResult.Error -> {

                }


            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onClick(data: Post) {
       val intent =Intent(activity, Comment::class.java)
        intent.putExtra("data", Gson().toJson(data).toString())
        startActivity(intent)
    }
}