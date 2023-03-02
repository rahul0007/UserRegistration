package com.assignment.userregistration.viewModel
import android.content.Context
import androidx.lifecycle.ViewModel
import com.assignment.userregistration.database.RoomRepository
import com.assignment.userregistration.database.user.UserMaster
import com.assignment.userregistration.lifecycle.ErrorModelView
import com.assignment.userregistration.lifecycle.SingleLiveData
import com.assignment.userregistration.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val repository: Repository,
    private val roomRepository: RoomRepository,
    @ApplicationContext val context: Context,
) : ViewModel() {
    var delegatesUserDataInsert = SingleLiveData<String>()
    val errorLiveData: SingleLiveData<ErrorModelView> = SingleLiveData()
    var exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        errorLiveData.postValue(
            ErrorModelView(
                messageTitle = throwable.message,
                tagException = throwable.printStackTrace().toString()
            )
        )
    }
    fun insertUserData(user: UserMaster) {
        roomRepository.insertUser(user)
        delegatesUserDataInsert.postValue("success")
    }
}