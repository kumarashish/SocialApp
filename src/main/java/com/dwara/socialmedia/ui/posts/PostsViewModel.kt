package com.dwara.socialmedia.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dwara.socialmedia.network.APIResult
import com.dwara.socialmedia.network.RetrofitClient
import com.dwara.socialmedia.room.Post
import kotlinx.coroutines.*
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class PostsViewModel : ViewModel() {

    private val _postDetails: MutableLiveData<APIResult<Response<ArrayList<Post>>>> =
        MutableLiveData()
    val postDetails: LiveData<APIResult<Response<ArrayList<Post>>>> =
        _postDetails
    //region ViewModelJobs
    /**
     * This is the job for all coroutines started by this ViewModel.
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     * Since we pass viewModelJob, you can cancel all coroutines
     * launched by uiScope by calling viewModelJob.cancel()
     */
    private val uiScope = CoroutineScope(Dispatchers.Default + viewModelJob)


    fun getPostDetails() {
        uiScope.launch(coroutineExceptionHandler) {
            val signInResponse =
                RetrofitClient.getService().getPost(ApiConstants.getPost)
            _postDetails.postValue(APIResult.Success(signInResponse))
        }


    }

    private var coroutineExceptionHandler =
        CoroutineExceptionHandler { _: CoroutineContext, throwable: Throwable ->
            _postDetails.postValue(APIResult.Error(IOException(throwable)))

        }


}