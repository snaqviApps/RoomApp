package learn.ghar.androidapps.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import learn.ghar.androidapps.repository.SubscriberRepository
import learn.ghar.androidapps.domain.SubscriberUI
import learn.ghar.androidapps.utils.safeLet


class SubscriberViewModel(private val repo: SubscriberRepository) : ViewModel() {

    private var isUpdateOrDelete: Boolean = false

    private lateinit var subscriberToUpdateOrDelete: SubscriberUI

    val subscribers = repo.subscribers

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearOrDeleteAllButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "save"
        clearOrDeleteAllButtonText.value = "Clear all"
    }

    fun saveOrUpdate() {

        safeLet(inputName.value, inputEmail.value) { name, email ->
            if (isUpdateOrDelete) {
                subscriberToUpdateOrDelete.name = name
                subscriberToUpdateOrDelete.email = email
                update(subscriberToUpdateOrDelete)
            } else {
                insert(SubscriberUI(0, name, email))
                inputName.value = ""
                inputEmail.value = ""
            }
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(subscriberToUpdateOrDelete)
        } else clearAll()
    }

    private fun insert(subscriber: SubscriberUI) = viewModelScope.launch(Dispatchers.IO) {
        repo.insert(subscriber)
    }

    private fun update(subscriber: SubscriberUI) = viewModelScope.launch(Dispatchers.IO) {
        repo.update(subscriber)
        inputName.value = ""
        inputEmail.value = ""
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteAllButtonText.value = "Clear All"
    }

    private fun delete(subscriber: SubscriberUI) = viewModelScope.launch(Dispatchers.IO) {
        repo.delete(subscriber)
        inputName.value = ""
        inputEmail.value = ""
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteAllButtonText.value = "Clear All"

    }

    private fun clearAll() = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteAll()
    }

    fun initUpdateAndDelete(subscriber: SubscriberUI) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        subscriberToUpdateOrDelete = subscriber
        isUpdateOrDelete = true
        saveOrUpdateButtonText.value = "Update"
        clearOrDeleteAllButtonText.value = "delete"
    }

}