package com.assignment.userregistration.utils
import android.app.Dialog
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.assignment.userregistration.R
import com.assignment.userregistration.viewModel.Const
import java.util.regex.Pattern

fun isValidPassword(password: String): Boolean {
    val pattern = Pattern.compile(Const.PASSWORD_PATTERN)
    val matcher = pattern.matcher(password)
    return matcher.matches()
}


fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

interface DialogCallbackCameraGallery {
    fun onYes(from: String)
}

fun Context.showCameraGalleryDialog(callback: DialogCallbackCameraGallery) {
    val dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
    dialog.setCancelable(true)
    dialog.setCanceledOnTouchOutside(true)
    dialog.window!!.setLayout(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    dialog.window?.setGravity(Gravity.BOTTOM)
    val lp: WindowManager.LayoutParams = dialog.window!!.attributes
    lp.dimAmount = 0.75f
    dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window
    dialog.window!!.attributes = lp
    dialog.setContentView(R.layout.raw_profile_chooser)

    val tvCamera = dialog.findViewById(R.id.tvCamera) as TextView
    val tvGallery = dialog.findViewById(R.id.tvGallery) as TextView
    tvCamera.setOnClickListener {
        dialog.dismiss()
        callback.onYes(Const.CAMERA)
    }
    tvGallery.setOnClickListener {
        dialog.dismiss()
        callback.onYes(Const.GALLERY)
    }

    dialog.show()
}


fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
fun Context.getRealPathFromUri(contentUri: Uri): String {
    var cursor: Cursor? = null
    return try {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val databaseUri: Uri
        val selection: String?
        val selectionArgs: Array<String>?
        if (contentUri.path!!.contains("/document/image:")) {
            databaseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            selection = "_id=?"
            selectionArgs = arrayOf(DocumentsContract.getDocumentId(contentUri).split(":")[1])
        } else {
            databaseUri = contentUri
            selection = null
            selectionArgs = null
        }
        cursor = this.contentResolver.query(databaseUri, proj, selection, selectionArgs, null)
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()

        cursor.getString(column_index)
    } catch (ex: Exception) {
        ex.printStackTrace()
        ""
    } finally {
        cursor?.close()
    }
}

