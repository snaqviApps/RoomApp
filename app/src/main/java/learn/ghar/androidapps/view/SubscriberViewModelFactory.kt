package learn.ghar.androidapps.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import learn.ghar.androidapps.repository.SubscriberRepository

@Suppress("UNCHECKED_CAST")
class SubscriberViewModelFactory(private val repository: SubscriberRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubscriberViewModel::class.java)) {
            return SubscriberViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }

}