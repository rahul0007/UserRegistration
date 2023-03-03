package com.assignment.userregistration.ui.dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.assignment.userregistration.R
import com.assignment.userregistration.database.user.UserMaster
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.user_info_dialog.*
import java.io.File
class UsersInfoDialog : DialogFragment(), View.OnClickListener {
    var userMaster: UserMaster? = null
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.imageViewClose -> {
                dialog?.dismiss()
            }
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.user_info_dialog, container, false)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(resources.getColor(android.R.color.transparent)))
        dialog!!.setCancelable(true)
        return view
    }

    fun setUserInfo(userMaster: UserMaster) {
        this.userMaster = userMaster
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {
            imageViewClose.setOnClickListener(this)
            textViewEmail.text = userMaster?.Email
            textViewPhone.text = userMaster?.PhoneNumber
            textViewUserName.text = userMaster?.FirstName+" "+userMaster?.LastName
            textViewDesignation.text = userMaster?.Designation
            textViewAddress.text = userMaster?.Address
            textViewLandmark.text = userMaster?.Landmark
            textViewCity.text = userMaster?.City +","+userMaster?.State
            textViewPincode.text = userMaster?.PinCode
            textViewQualification.text = userMaster?.Qualification
            textViewPassingYear.text = "Passing Year - "+userMaster?.PassingYear
            textViewGrade.text =userMaster?.Grade
            textViewExperience.text =userMaster?.Experience +" Years of Experience"
            textViewDomain.text =userMaster?.Domain
            Glide.with(this).load(File(userMaster?.Profile)).apply(RequestOptions().circleCrop()).placeholder(R.drawable.ic_user)
                .into(imageViewUserProfile)

        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }
}
