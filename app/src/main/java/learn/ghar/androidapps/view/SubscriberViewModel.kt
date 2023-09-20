package learn.ghar.androidapps.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import learn.ghar.androidapps.SubscriberRepository
import learn.ghar.androidapps.db.Subscriber


class SubscriberViewModel(private val repo: SubscriberRepository) : ViewModel() {

    /** updated list from database using DAO-access in repository */
    val subscribers = repo.subscribers

    val _inputName = MutableLiveData<String>()
    val _inputEmail = MutableLiveData<String>()
//    val inputEmail : LiveData<String> = _inputEmail     // not using, for two-way binding

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearOrDeleteAllButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "save"
        clearOrDeleteAllButtonText.value = "Clear all"
    }

    fun saveOrUpdate(){
        val name = _inputName.value
        val email = _inputEmail.value
        safeLet(name, email)  {name1, email1 ->
            insert(Subscriber(0, name1, email1))
        }
        _inputName.value = ""
        _inputEmail.value = ""
    }

    fun clearAllOrDelete(){
        clearAll()
    }

    private fun insert(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
            repo.insert(subscriber)
    }
    private fun update(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
            repo.update(subscriber)
    }
    private fun delete(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
            repo.delete(subscriber)
    }

    private fun clearAll() = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteAll()
    }

    private fun <T1: Any, T2: Any, R: Any> safeLet(p1:T1?, p2: T2?, func: (T1, T2) -> R?): R? {
        return if(p1 != null && p2 != null) func(p1, p2) else null
    }
}