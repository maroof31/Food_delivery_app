package net.simplifiedcoding.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nevermindapp.newproject1.data.repository.UserRepository
import com.nevermindapp.newproject1.ui.auth.AuthViewModel
import com.nevermindapp.newproject1.ui.auth.session.UserSessionUtils


@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(
    private val repository: UserRepository,
    private val utils: UserSessionUtils
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repository,utils) as T
    }

}