package learn.ghar.androidapps.repository

import androidx.lifecycle.map
import learn.ghar.androidapps.db.SubscriberDAO
import learn.ghar.androidapps.domain.SubscriberUI
import learn.ghar.androidapps.domain.toSubscriber
import learn.ghar.androidapps.domain.toSubscriberUI

//TODO: Interface this..
class SubscriberRepository(private val dao: SubscriberDAO) {

    val subscribers = dao.getAllSubscribers().map { data ->
        data.map { item ->
            item.toSubscriberUI()
        }
    }

    suspend fun insert(subscriber: SubscriberUI) = dao.insertSubscriber(subscriber.toSubscriber())

    suspend fun update(subscriber: SubscriberUI) = dao.updateSubscribers(subscriber.toSubscriber())

    suspend fun delete(subscriber: SubscriberUI) = dao.deleteSubscribers(subscriber.toSubscriber())
    suspend fun deleteAll() = dao.deleteAll()

}

