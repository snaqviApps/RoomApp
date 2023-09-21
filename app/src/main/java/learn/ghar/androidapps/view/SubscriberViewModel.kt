package learn.ghar.androidapps.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import learn.ghar.androidapps.SubscriberRepository
import learn.ghar.androidapps.db.Subscriber


class SubscriberViewModel(private val repo: SubscriberRepository) : ViewModel() {

    private var isUpdateOrDelete: Boolean = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

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

        safeLet(_inputName.value, _inputEmail.value) { name, email ->
                if(isUpdateOrDelete) {
                subscriberToUpdateOrDelete.name = name
                subscriberToUpdateOrDelete.email = email
                update(subscriberToUpdateOrDelete)
//            }

            } else {
                insert(Subscriber(0, name, email))
                _inputName.value = ""
                _inputEmail.value = ""
            }
        }
    }

    fun clearAllOrDelete(){
        if (isUpdateOrDelete) {
            delete(subscriberToUpdateOrDelete)
        }
        else clearAll()
    }

    private fun insert(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
            repo.insert(subscriber)
    }
    private fun update(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
            repo.update(subscriber)
        withContext(Dispatchers.Main) {
            _inputName.value = ""
            _inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearOrDeleteAllButtonText.value = "Clear All"
        }
    }
    private fun delete(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
            repo.delete(subscriber)
        withContext(Dispatchers.Main, {
            _inputName.value = ""
            _inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearOrDeleteAllButtonText.value = "Clear All"
        })

    }

    private fun clearAll() = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteAll()
    }

    fun initUpdateAndDelete(subscriber: Subscriber){
        _inputName.value = subscriber.name
        _inputEmail.value = subscriber.email
        subscriberToUpdateOrDelete = subscriber
        isUpdateOrDelete = true
        saveOrUpdateButtonText.value = "Update"
        clearOrDeleteAllButtonText.value = "delete"
    }

    private fun <T1: Any, T2: Any, R: Any> safeLet(p1:T1?, p2: T2?, func: (T1, T2) -> R?): R? {
        return if(p1 != null && p2 != null) func(p1, p2) else null
    }
}