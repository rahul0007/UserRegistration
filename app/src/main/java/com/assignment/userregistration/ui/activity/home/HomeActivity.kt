package com.assignment.userregistration.ui.activity.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.assignment.userregistration.R
import com.assignment.userregistration.database.user.UserMaster
import com.assignment.userregistration.ui.activity.userInfo.RegisterActivity
import com.assignment.userregistration.ui.adapter.UsersAdapter
import com.assignment.userregistration.ui.base.BaseActivity
import com.assignment.userregistration.ui.dialog.UsersInfoDialog
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class HomeActivity : BaseActivity(),
    View.OnClickListener {
    lateinit var homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun findFragmentPlaceHolder() = 0

    override fun findContentView() = R.layout.activity_home

    override fun observeViewModel() {
        setOnClickListener()
        textViewTitle.text = resources.getString(R.string.users)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        homeViewModel.getUsers()
        homeViewModel.getUserListDelegate.observe(this) {
            val adapter = UsersAdapter(
                it as ArrayList<UserMaster>,
                this,
                object : UsersAdapter.OnItemClickStatusListener {
                    override fun itemsClicked(userMaster: UserMaster) {
                        val dilog = UsersInfoDialog()
                        dilog.setUserInfo(userMaster)
                        dilog.setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialog)
                        dilog.show(supportFragmentManager, "userMaster")
                    }

                })
            recyclerViewUser.adapter = adapter

            if (adapter.itemCount > 0) {
                textViewPlaceHolder.visibility = View.GONE
                recyclerViewUser.visibility = View.VISIBLE
            } else {
                textViewPlaceHolder.visibility = View.VISIBLE
                recyclerViewUser.visibility = View.GONE
            }
        }

        homeViewModel.delegatesMessage.observe(this) {
            Toast.makeText(this, "" + it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setOnClickListener() {
        imageViewBack.setOnClickListener(this)
        buttonRegister.setOnClickListener(this)
        imageViewBack.visibility = View.GONE
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.buttonRegister -> {
                RegisterActivity.start(this)
            }
        }
    }

    companion object {
        fun start(context: BaseActivity) {
            val intent = Intent(context, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}