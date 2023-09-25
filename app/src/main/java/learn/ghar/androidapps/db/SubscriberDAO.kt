package learn.ghar.androidapps.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SubscriberDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)            // can have different strategies when an Id has refreshed data
    suspend fun insertSubscriber(subscriber: Subscriber)

    @Update
    suspend fun updateSubscribers(subscriber: Subscriber)

    @Delete
    suspend fun deleteSubscribers(subscriber: Subscriber) : Int

    @Query("DELETE FROM subscriber_data_table")
    suspend fun deleteAll() : Int

    /**
     * note, there is no suspend key word, as Room-library runs this function on background thread
     * as it returns 'live-data'
     */
    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscribers() : LiveData<List<Subscriber>>
}