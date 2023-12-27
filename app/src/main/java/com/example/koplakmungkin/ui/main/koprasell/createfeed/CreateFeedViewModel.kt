package com.example.koplakmungkin.ui.main.koprasell.createfeed

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.koplakmungkin.data.repository.GradeRepository
import com.example.koplakmungkin.data.response.GradeResponse
import com.google.firebase.storage.FirebaseStorage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class CreateFeedViewModel(private val gradeRepository: GradeRepository = GradeRepository()) : ViewModel(){
    private val _grade = MutableLiveData<String>()
    val grade: LiveData<String>
        get() = _grade
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun getGrade(imageFile: File, description: String){
        val descriptionPart: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), description)
        val imageRequestBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
        val imagePart: MultipartBody.Part = MultipartBody.Part.createFormData("image", imageFile.name, imageRequestBody)

        gradeRepository.getGrad(imagePart, descriptionPart, object : Callback<GradeResponse>{
            override fun onResponse(call: Call<GradeResponse>, response: Response<GradeResponse>) {
                if (response.isSuccessful){
                    val grade = response.body()?.status?.data?.jsonMemberClass?: "Unknown"
                    _grade.value = grade
                } else{
                    val errorMessage = response.errorBody()?.string()?: "Unknown Error"
                    _error.value = errorMessage
                }
            }

            override fun onFailure(call: Call<GradeResponse>, t: Throwable) {
                Log.e("CreateFeedViewModel", "Network request failed", t)
                _error.value = "An unexpected error occurred. Please try again later."

            }
        })
    }

    fun uploadImage(imageFile: File, onImageUploaded: (String) -> Unit) {
        val storageRef = FirebaseStorage.getInstance().reference.child("images/${imageFile.name}")
        storageRef.putFile(Uri.fromFile(imageFile))
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener {
                    onImageUploaded(it.toString())
                }
            }
            .addOnFailureListener {

            }
    }
}