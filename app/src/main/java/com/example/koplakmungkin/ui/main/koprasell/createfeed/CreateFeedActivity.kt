package com.example.koplakmungkin.ui.main.koprasell.createfeed

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.koplakmungkin.R
import com.example.koplakmungkin.data.model.FeedData
import com.example.koplakmungkin.databinding.ActivityCreateFeedBinding
import com.example.koplakmungkin.ui.main.MainActivity
import com.google.firebase.database.FirebaseDatabase
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


class CreateFeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateFeedBinding
    private var selectedImagePath: String? = null
    private var username: String? = null
    private val createFeedViewModel by viewModels<CreateFeedViewModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createFeedLayout.cameraBtn.setOnClickListener {
            requestCameraPermission()
        }
        observeViewModel()
       binding.createFeedLayout.submitStoryButton.setOnClickListener {
           buttonSubmit()
       }
    }
    private val cameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            openCamera()
        }
    }
    private fun requestCameraPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) -> {
                openCamera()
            }
            else -> {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
    @SuppressLint("QueryPermissionsNeeded")
    @Suppress("DEPRECATION")
    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
            Log.d("tag", "kamera intent")
        }
    }
    private fun navigateToHome(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun saveDataToFirebase(description: String, price: String, contact: String, username: String, imageUrl: String, grade: String) {
        val feedData = FeedData(description, price, contact, username, imageUrl, grade)
        val databaseRef = FirebaseDatabase.getInstance().getReference("feeds")

        val newFeedRef = databaseRef.push()
        newFeedRef.setValue(feedData)
            .addOnSuccessListener {
                Toast.makeText(this@CreateFeedActivity, "Create Feed Success", Toast.LENGTH_SHORT).show()
                navigateToHome()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this@CreateFeedActivity, "Create Feed Failed", Toast.LENGTH_SHORT).show()
            }
    }
    private fun buttonSubmit(){
        val description = binding.createFeedLayout.descriptionEditText.text.toString()
        val price = binding.createFeedLayout.priceEditText.text.toString()
        val contact = binding.createFeedLayout.ContactEditText.text.toString()
        val grade = binding.createFeedLayout.gradeTv.text.toString()
        val username = username.toString()
//        val username = username?.toString() ?: ""

        selectedImagePath?.let {
            val imageFile = File(it)
            createFeedViewModel.uploadImage(imageFile){ imageUrl ->
                saveDataToFirebase(description, price, contact, username, imageUrl , grade)
            }
        }
    }




    private fun observeViewModel(){
        createFeedViewModel.grade.observe(this){
            binding.createFeedLayout.gradeTv.text =getString(R.string.grade, it)
        }
        createFeedViewModel.error.observe(this){
            binding.createFeedLayout.gradeTv.text = getString(R.string.grade, it)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (
            requestCode == CAMERA_REQUEST_CODE
            && resultCode == RESULT_OK
            ){
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap?
                    selectedImagePath = saveImageToInternalStorage(imageBitmap)
                    updateImageView(selectedImagePath)
                    generateImage()
                }
                GALLERY_REQUEST_CODE -> {
                    val imageUri = data?.data
                    imageUri?.let {
                        selectedImagePath = getImagePathFromUri(it)
                        updateImageView(selectedImagePath)
                    }
                    generateImage()
                }
            }
        }
    }
    private fun generateImage(){
        val description = ""
        selectedImagePath?.let {
            val imageFile = File(it)
            createFeedViewModel.getGrade(imageFile, description)
        }
    }
    private fun saveImageToInternalStorage(bitmap: Bitmap?): String {
        val file = File(cacheDir, "tempImage.jpg")
        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file.absolutePath
    }
    private fun updateImageView(image: String?){
        if (image != null){
            val bitmap = BitmapFactory.decodeFile(image)
            binding.createFeedLayout.imagePickerView.setImageBitmap(bitmap)
        }
    }
    private fun getImagePathFromUri(uri: Uri): String?{
        var path: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null,null,null)
        cursor?.use {
            if (it.moveToFirst()){
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                path = it.getString(columnIndex)
            }
        }
        return path
    }


    companion object {
        private const val CAMERA_REQUEST_CODE = 1001
        private const val GALLERY_REQUEST_CODE = 1002
    }
}