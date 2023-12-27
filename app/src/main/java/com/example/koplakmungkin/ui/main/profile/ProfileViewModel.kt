import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koplakmungkin.data.Result
import com.example.koplakmungkin.data.model.UserData
import com.example.koplakmungkin.data.repository.KoplakRepository
import com.example.koplakmungkin.data.response.UserProfileResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(private val koplakRepository: KoplakRepository) : ViewModel() {

    private val _userProfileResult = MutableLiveData<Result<UserProfileResponse>>()
    val userProfileResult: LiveData<Result<UserProfileResponse>> get() = _userProfileResult

    private val _sessionResult = MutableLiveData<UserData>()
    val sessionResult: LiveData<UserData> get() = _sessionResult

    fun getSession() {
        viewModelScope.launch {
            val sessionData = koplakRepository.getSession().first()
            _sessionResult.value = sessionData
        }
    }

    fun getUserProfile(token: String, userId: String) {
        viewModelScope.launch {
            _userProfileResult.value = Result.Loading

            try {
                val response = koplakRepository.getUserProfile(token, userId)
                if (response.isSuccessful) {
                    _userProfileResult.value = Result.Success(response.body()!!)
                } else {
                    _userProfileResult.value = Result.Error("Failed to fetch user profile")
                }
            } catch (e: Exception) {
                _userProfileResult.value = Result.Error("Failed to fetch user profile")
            }
        }
    }
}
