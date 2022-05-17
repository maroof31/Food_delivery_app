package net.simplifiedcoding.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nevermindapp.newproject1.data.repository.UserRepository
import com.nevermindapp.newproject1.ui.ProductDetailViewModel
import com.nevermindapp.newproject1.ui.auth.AuthViewModel
import com.nevermindapp.newproject1.ui.auth.session.UserSessionUtils
import database.AppDatabase


@Suppress("UNCHECKED_CAST")
class ProductDetailViewModelFactory(
    private val repository: UserRepository,
    private val db: AppDatabase
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductDetailViewModel(repository, db) as T
    }

}