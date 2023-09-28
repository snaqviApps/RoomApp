package learn.ghar.androidapps.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import learn.ghar.androidapps.repository.SubscriberRepository
import learn.ghar.androidapps.databinding.ActivityMainBinding
import learn.ghar.androidapps.db.SubscriberDatabase
import learn.ghar.androidapps.domain.SubscriberUI
import learn.ghar.androidapps.view.SubscriberViewModel
import learn.ghar.androidapps.view.SubscriberViewModelFactory
import learn.ghar.androidapps.view.SubscribersAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainViewModel: SubscriberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        //TODO: This could be improved by DI
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val mainRepository = SubscriberRepository(dao)
        val viewModelFactory = SubscriberViewModelFactory(mainRepository)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[SubscriberViewModel::class.java]
        mainBinding.mainViewModelXML = mainViewModel
        mainBinding.lifecycleOwner = this
        initRV()
    }

    private fun initRV() {
        mainBinding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        displaySubscribers()
    }

    private fun displaySubscribers() {

        mainViewModel.subscribers.observe(this) {
            mainBinding.subscriberRecyclerView.adapter =
                SubscribersAdapter(it) { subscriberClicked: SubscriberUI ->
                    listItemClickListener(subscriberClicked)
                }
        }
    }

    private fun listItemClickListener(subscriber: SubscriberUI) {
        Toast.makeText(this, "name :${subscriber.name}", Toast.LENGTH_SHORT).show()
        mainViewModel.initUpdateAndDelete(subscriber)
    }
}