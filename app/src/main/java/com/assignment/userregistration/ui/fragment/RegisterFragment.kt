package com.assignment.userregistration.ui.fragment
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.assignment.userregistration.R
import com.assignment.userregistration.databinding.FragmentRegisterBinding
import com.assignment.userregistration.exception.ApplicationException
import com.assignment.userregistration.utils.*
import com.assignment.userregistration.viewModel.Const
import com.assignment.userregistration.viewModel.UserInfoViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import java.io.File
import javax.inject.Inject


@AndroidEntryPoint
class RegisterFragment : Fragment() {

    @Inject
    lateinit var validator: Validator
    private lateinit var imageUri: Uri
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserInfoViewModel by activityViewModels()
    var permissionsStr = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

//    profile from camera
    private val contract =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {/*take from camera*/
            with(binding) {
                imageViewUserProfile.setImageURI(null)
                viewModel.imageUri.set(imageUri.toString())
                Glide.with(requireActivity()).load(imageUri)
                    .apply(RequestOptions().circleCrop()).into(imageViewUserProfile)
            }

        }

    //    profile from gallery
    private val galleryContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) { /*pick from gallery*/
            with(binding) {
                val uri = requireContext().getRealPathFromUri(it!!)
                viewModel.imageUri.set(uri)
                viewModel.imageUri.set(imageUri.toString())
                Glide.with(requireActivity()).load(imageUri)
                    .apply(RequestOptions().circleCrop()).into(imageViewUserProfile)
            }

        }


    private fun setupObservers() {
        viewModel.errorChannel.observe(viewLifecycleOwner) {
            requireContext().showToast(it.asString(requireContext()))
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            val list: ArrayList<Boolean> = ArrayList(result!!.values)
            val permissionsList = ArrayList<String>()
            var permissionsCount = 0
            for (i in 0 until list.size) {
                if (shouldShowRequestPermissionRationale(permissionsStr.get(i))) {
                    permissionsList.add(permissionsStr[i])
                } else if (!hasPermission(requireContext(), permissionsStr.get(i))) {
                    permissionsCount++
                }
            }
            if (permissionsList.size > 0) {
                //Some permissions are denied and can be asked again.
                showSnackBarMsg()
            } else if (permissionsCount > 0) {
                //Show alert dialog
                showSnackBarMsg()
            } else {
                showCameraGalleryDialog()
                //All permissions granted. Do your stuff 🤞
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.userInfoVM = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createImageUri()
        setupObservers()
        setupViews()
        clickEvents()

    }

    private fun createImageUri() { //creating image uri
        val image = File(requireContext().applicationContext.filesDir, "my_photos.png")
        imageUri = FileProvider.getUriForFile(
            requireContext().applicationContext,
            "${requireContext().packageName}.utils.provider",
            image
        )
    }

    private fun setupViews() {
        binding.include.textViewTitle.text = getString(R.string.register)
    }

    private fun clickEvents() {
        with(binding) {
            binding.include.imageViewBack.setOnClickListener {
                findNavController().popBackStack()
            }

            layoutProfile.setOnClickListener {
                val permissionsList = ArrayList<String>()
                var permissionsCount = 0
                for (i in permissionsStr.indices) {
                    if (shouldShowRequestPermissionRationale(permissionsStr[i])) {
                        permissionsList.add(permissionsStr[i])
                    } else if (!hasPermission(requireContext(), permissionsStr[i])) {
                        permissionsCount++
                    }
                }
                when {
                    permissionsCount == permissionsList.size -> {
                        // You can use the API that requires the permission.
                        showCameraGalleryDialog()
                    }

                    permissionsList.size > 0 -> {
                        showSnackBarMsg()
                    }
                    else -> {
                        // The registered ActivityResultCallback gets the result of this request
                        requestPermissionLauncher.launch(permissionsStr)
                    }


                }
            }

        }
    }


    private fun showSnackBarMsg() {
        requireContext().showToast("Some Permission Denied.")
    }


    private fun showCameraGalleryDialog() {
        requireContext().showCameraGalleryDialog(object : DialogCallbackCameraGallery {
            override fun onYes(from: String) {
                if (from == Const.GALLERY) {
                    galleryContract.launch("image/*")
                } else {
                    contract.launch(imageUri)
                }
            }
        })
    }

    private fun hasPermission(context: Context, permissionStr: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permissionStr
        ) == PackageManager.PERMISSION_GRANTED
    }
}


