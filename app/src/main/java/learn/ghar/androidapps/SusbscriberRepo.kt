package learn.ghar.androidapps

import learn.ghar.androidapps.db.Subscriber
import learn.ghar.androidapps.db.SubscriberDAO

class SusbscriberRepo(private val dao: SubscriberDAO) {

    val subscribers = dao.getAllSubscribers()           // due to liveData (or Flow), it is already run on background thread

    suspend fun insert(subscriber: Subscriber){
        dao.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber){
        dao.updateSubscribers(subscriber)
    }

    suspend fun delete(subscriber: Subscriber){
        dao.deleteSubscribers(subscriber)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }
}