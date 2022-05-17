package net.simplifiedcoding.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nevermindapp.newproject1.data.repository.UserRepository
import com.nevermindapp.newproject1.ui.auth.session.UserSessionUtils
import com.nevermindapp.newproject1.ui.rest.MainViewModel


@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val repository: UserRepository,
    private val utils: UserSessionUtils
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository, utils) as T
    }

}