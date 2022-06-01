package com.dwara.socialmedia.ui.favourite

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.dwara.socialmedia.R
import com.dwara.socialmedia.databinding.PostItemBinding
import com.dwara.socialmedia.room.Post
import com.dwara.socialmedia.room.SocialDB
import com.google.android.material.snackbar.Snackbar

class FavoriteAdapter(var list: List<Post>?,val clickListner:CommentClickListener) :
        RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostItemBinding.inflate(
            inflater,
           parent, false
        )
        context = parent.context
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list!!.size

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(list?.get(position)) {
                binding.titleTv.text = this!!.title
                binding.contentTv.text = this!!.body
                if (this.isFav==true) {
                    binding.favouriteImg.setImageDrawable(context.getDrawable(R.drawable.heart_selected))
                } else {
                    binding.favouriteImg.setImageDrawable(context.getDrawable(R.drawable.heart_unselected))
                }
                binding.favouriteImg.setOnClickListener {
                    val status=this.isFav==null || this.isFav==false
                    SocialDB.responseDao().updatePost(status, list?.get(position)!!.postId!!)
                    Snackbar.make(it, "You marked this post as "+ if(status==true) "favourite" else "unfavourite", Snackbar.LENGTH_LONG).show()
                    resetList()
                }

                binding.commentTv.setOnClickListener{
                    clickListner.onClick(this)
                }
            }
        }
    }
    fun resetList() {
        list = SocialDB.responseDao().getAllFavorite(1)
        notifyDataSetChanged()
    }
    interface CommentClickListener {
        fun onClick(data:Post)
    }
    class ViewHolder(val binding: PostItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}



