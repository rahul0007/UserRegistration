package com.assignment.userregistration.extentions
import android.accounts.NetworkErrorException
import android.content.Context
import com.assignment.userregistration.R
import com.assignment.userregistration.utils.NetworkUtils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope


fun CoroutineScope.manageNetwork(
    context: Context,
    coroutineExceptionHandler: CoroutineExceptionHandler
): CoroutineScope? {
    if (!NetworkUtils.isOnline(context)) {
        coroutineExceptionHandler.handleException(
            this.coroutineContext, NetworkErrorException(
                context.getString(
                    R.string.no_connection_available
                )
            )
        )
        return null
    }
    return this
}