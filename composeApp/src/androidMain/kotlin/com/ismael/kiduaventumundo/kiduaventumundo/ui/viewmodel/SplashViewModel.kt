import com.ismael.kiduaventumundo.kiduaventumundo.domain.repository.userRepository

class SplashViewModel(
    private val repository: userRepository
) {

    fun hasSession(): Boolean {
        return repository.getSessionUserId() != null
    }
}