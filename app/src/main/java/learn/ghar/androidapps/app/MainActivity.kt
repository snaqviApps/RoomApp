package learn.ghar.androidapps.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import learn.ghar.androidapps.R
import learn.ghar.androidapps.SubscriberRepository
import learn.ghar.androidapps.databinding.ActivityMainBinding
import learn.ghar.androidapps.db.Subscriber
import learn.ghar.androidapps.db.SubscriberDatabase
import learn.ghar.androidapps.view.SubscriberViewModel
import learn.ghar.androidapps.view.SubscriberViewModelFactory
import learn.ghar.androidapps.view.SubscribersAdapter
import learn.ghar.androidapps.view.SubscribersViewHolder

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding : ActivityMainBinding
    private lateinit var mainViewModel : SubscriberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)                                    // no data-binding-implementation
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val mainRepository = SubscriberRepository(dao)                              // repository instance that takes 'DAO' instance as constructor parameter
        val viewModelFactory = SubscriberViewModelFactory(mainRepository)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[SubscriberViewModel::class.java]
        mainBinding.mainViewModelXML = mainViewModel                                // assigning to binding-object
        mainBinding.lifecycleOwner = this                                           // assigning lifeCycle owner for liveData-interaction with data-binding

        // fetch Subscriber's data from db, and display
        initRV()
    }

    private fun initRV() {
        mainBinding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        displaySubscribers()
    }

    private fun displaySubscribers() {
        /**
         * fetch updated-data using liveData parameter 'subscribers' from DAO, using repository
         * via viewModel
         *
         */
        mainViewModel.subscribers.observe(this, Observer { subs: List<Subscriber> ->
            Log.i("TAG", subs.map {
                it.name.plus(" : ${it.email}")
            }.toString())
            mainBinding.subscriberRecyclerView.adapter = SubscribersAdapter(subs)
        })
    }
}