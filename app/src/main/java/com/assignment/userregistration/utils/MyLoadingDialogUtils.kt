package com.assignment.userregistration.utils
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.assignment.userregistration.dialogs.MyDialogLoading

object MyLoadingDialogUtils {

    private var myDialogLoading: MyDialogLoading? = null
    fun showLoadingDialog(
        supportFragmentManager: FragmentManager
    ): MyDialogLoading? {
        myDialogLoading = MyDialogLoading.newInstance()
        myDialogLoading?.show(supportFragmentManager, MyDialogLoading.TAG)
        return myDialogLoading
    }


    fun dismissLoadingDialog(activity: FragmentActivity) {
        MyDialogLoading.dismiss(activity)
    }

}