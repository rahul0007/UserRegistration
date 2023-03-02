package com.assignment.userregistration.utils
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import com.assignment.userregistration.R
import com.yalantis.ucrop.UCrop
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
class ImagePicker : DialogFragment() {

    internal var cropMode = 0
    internal var mCurrentPhotoPath: String? = null
    internal lateinit var imagePickerResult: ImagePickerResult
    private lateinit var imageViewCamera: AppCompatTextView
    private lateinit var imageViewGallery: AppCompatTextView
    private lateinit var linearLayoutCamera: LinearLayout
    private lateinit var linearLayoutImage: LinearLayout
    private var file: File? = null
    private var isAccessImageSeleter: Boolean = false
    fun setImagePickerResult(imagePickerResult: ImagePickerResult) {
        this.imagePickerResult = imagePickerResult

    }

    fun setCropMode(cropMode: Int) {
        this.cropMode = cropMode
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.raw_profile_chooser, container)
        imageViewCamera = view.findViewById(R.id.tvCamera)
        imageViewGallery = view.findViewById(R.id.tvGallery)
        dialog?.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        imageViewCamera.setOnClickListener {

            if (Build.VERSION.SDK_INT >= 23) {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(Constant.PERMISSIONS_CAMERA, Constant.REQUEST_CAMERA_PERMISSION)
                } else {
                    dispatchTakePictureIntent()
                }

            } else dispatchTakePictureIntent()
        }


        imageViewGallery.setOnClickListener {

            if (Build.VERSION.SDK_INT >= 23) {

                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(Constant.PERMISSIONS_GALLERY, Constant.REQUEST_GALLERY_PERMISSION)
                } else {
                    openGallory()
                }

            } else openGallory()
        }
        return view
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }




    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Log.e("dfdf","fdfdfdf"+ex.message)
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                            requireContext(),
                            "com.assignment.userregistration.android.fileprovider",
                            it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, Constant.REQUEST_TAKE_PHOTO)
                }
        }
    }
    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        }
    }



    private fun openGallory() {
        try {
            val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(i, Constant.RESULT_LOAD_IMAGE)
        } catch (ignored: Exception) {
        }

    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == Constant.REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            if (mCurrentPhotoPath != null) {
                cropImage(mCurrentPhotoPath)
            }

        } else if (requestCode == Constant.RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK) {

            if (data != null) {
                val selectedImage = data.data
                if (selectedImage != null) {
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor = requireActivity().contentResolver.query(selectedImage, filePathColumn, null, null, null)
                    if (cursor != null) {
                        cursor.moveToFirst()
                        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                        mCurrentPhotoPath = cursor.getString(columnIndex)
                        cursor.close()
                        cropImage(mCurrentPhotoPath)
                    }
                }
            }

        } else if (requestCode == UCrop.REQUEST_CROP) {

            if (data != null) {

                try {
                    val resultUri = UCrop.getOutput(data)

                    if (resultUri != null) {
                        file = File(resultUri.path)
                        imagePickerResult.onResult(resultUri.path!!)
                        dismiss()
                    }

                } catch (e: Exception) {

                    e.printStackTrace()

                }

            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == Constant.REQUEST_CAMERA_PERMISSION) {
            if (PermissionUtil.verifyPermissions(grantResults)) {
                dispatchTakePictureIntent()

            } else {

                ////////////////////

            }

        } else if (requestCode == Constant.REQUEST_GALLERY_PERMISSION) {

            if (PermissionUtil.verifyPermissions(grantResults)) {

                openGallory()

            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }


    interface ImagePickerResult {
        fun onResult(path: String)
    }

    private fun cropImage(mCurrentPhotoPath: String?) {
        if (mCurrentPhotoPath != null && !mCurrentPhotoPath.isEmpty()) {
            val option = UCrop.Options()
            option.setFreeStyleCropEnabled(true)
            option.setToolbarColor(ContextCompat.getColor(Objects.requireNonNull<Context>(requireContext()), R.color.darkOrange))
            option.setToolbarTitle(getString(R.string.app_name))
            option.setToolbarWidgetColor(ContextCompat.getColor(requireContext(), R.color.white))
            try {
                UCrop.of(Uri.fromFile(File(mCurrentPhotoPath)),
                    Uri.fromFile(File.createTempFile("image_", ".jpg", requireContext().cacheDir)))
                    .withOptions(option).start(requireActivity(),this, 69)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun setAccessImageSeleter(isAccessImageSeleter: Boolean) {
        this.isAccessImageSeleter = isAccessImageSeleter
    }

}

