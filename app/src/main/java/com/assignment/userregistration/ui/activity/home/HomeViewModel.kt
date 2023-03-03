package com.assignment.userregistration.ui.activity.home
import android.content.Context
import androidx.lifecycle.ViewModel
import com.assignment.userregistration.database.RoomRepository
import com.assignment.userregistration.database.user.UserMaster
import com.assignment.userregistration.lifecycle.ErrorModelView
import com.assignment.userregistration.lifecycle.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    @ApplicationContext val context: Context,
) : ViewModel() {
    var getUserListDelegate = SingleLiveData<List<UserMaster>>()
    var delegatesMessage = SingleLiveData<String>()
    val errorLiveData: SingleLiveData<ErrorModelView> = SingleLiveData()
    var exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        errorLiveData.postValue(
            ErrorModelView(
                messageTitle = throwable.message,
                tagException = throwable.printStackTrace().toString()
            )
        )
    }
    fun getUsers() {
        getUserListDelegate.postValue(roomRepository.getUserList())
    }
}