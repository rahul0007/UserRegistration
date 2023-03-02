package com.assignment.userregistration.utils

import android.Manifest

class Constant {
    companion object {
        const val BaseURL="https://staging.grubbrr.com"
        val PERMISSIONS_CAMERA = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val PERMISSIONS_GALLERY = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        public val REQUEST_CAMERA_PERMISSION = 1
        public val REQUEST_GALLERY_PERMISSION = 2
        val REQUEST_TAKE_PHOTO = 1
        val RESULT_LOAD_IMAGE = 2
        val REQUEST = 10
        val REQUEST_CROP = 11
        val USER_DATA = "UserData"
    }

}