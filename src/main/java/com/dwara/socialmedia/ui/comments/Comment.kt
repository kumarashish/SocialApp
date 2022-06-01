package com.dwara.socialmedia.ui.comments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dwara.socialmedia.databinding.FragmentCommentBinding
import android.os.Build
import com.dwara.socialmedia.room.Post
import com.dwara.socialmedia.room.SocialDB
import com.google.gson.Gson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.R
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.NonNull
import com.dwara.socialmedia.databinding.CommentsItemBinding
import com.google.android.material.snackbar.Snackbar


class Comment : AppCompatActivity() {

    private var _binding: FragmentCommentBinding? = null
    private lateinit var binding: FragmentCommentBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    var model: Post? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()!!.setTitle("Comments");
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);

        // add back arrow to toolbar


        binding = FragmentCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var data = intent.getStringExtra("data")
        model = Gson().fromJson(data, Post::class.java)
        binding.titleTv.text = model!!.title
        binding.submit.setOnClickListener {
            if (binding.comment.text!!.length == 0) {
                binding.commentTlout.error = "Please enter your comments"
            } else {
                binding.commentTlout.error = ""
                SocialDB.responseDao().insertComment(
                    com.dwara.socialmedia.room.Comment(
                        postId = model!!.postId!!,
                        comment = binding.comment.text.toString(),
                        commentby = "Test User",
                        commentOn = getCurrentdateTime(),
                        id = 0
                    )

                )
                binding.comment.setText("")
                Snackbar.make(it, "Comment posted successfully", Snackbar.LENGTH_LONG).show()

                handleCommentsUI()
            }
        }
        handleCommentsUI()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun getCurrentdateTime(): String {
        val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val formatted = current.format(formatter)
        return formatted.toString()
    }

    fun handleCommentsUI() {
        val commentsList = SocialDB.responseDao().getAllComments(model!!.postId)
        if (commentsList == null || commentsList.size == 0) {
            var child =
                CommentsItemBinding.inflate(LayoutInflater.from(this), binding.comments, false)
            child.commentTv.text = ""
            child.commentbyTv.text = "No comments"
            binding.comments.addView(child.root)
        } else {
            binding.comments.removeAllViews()
            for (comment in commentsList) {
                var child =
                    CommentsItemBinding.inflate(LayoutInflater.from(this), binding.comments, false)
                child.commentTv.text = comment.comment
                child.commentbyTv.text =
                    "Comment by "+comment.commentby + " on " + comment.commentOn
                binding.comments.addView(child.root)
            }
        }

    }


}