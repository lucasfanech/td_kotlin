package fr.unilasalle.fanech.td_android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(private val database: AppDatabase) : ViewModel() {
    private var _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> = _userList

    init {
        loadUsers()
    }

    fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            database.userDao().insert(user)
            loadUsers()
        }
    }

    fun loadUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _userList.postValue(database.userDao().getAllUsers())
        }
    }


}
