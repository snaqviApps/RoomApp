package learn.ghar.androidapps.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import learn.ghar.androidapps.R
import learn.ghar.androidapps.databinding.SubscriberListItemBinding
import learn.ghar.androidapps.db.Subscriber

class SubscribersAdapter(private val subscribers: List<Subscriber>) : RecyclerView.Adapter<SubscribersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscribersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)    // here we create list_item_view layout
//        val rViewBinding = SubscriberListItemBinding.inflate(layoutInflater, R.layout.subscriber_list_item, false)
        val rViewBinding = SubscriberListItemBinding.inflate(layoutInflater)
        return SubscribersViewHolder(rViewBinding)
    }

    override fun getItemCount(): Int {
       return subscribers.size
    }

    override fun onBindViewHolder(holder: SubscribersViewHolder, position: Int) {
//        holder.bind(subscribers.get(position))      ---> not very optimized
        holder.bind(subscribers[position])            // indexing operator
    }

}

class SubscribersViewHolder(val listItemBinding: SubscriberListItemBinding) : RecyclerView.ViewHolder(listItemBinding.root){

    fun bind(subscriber: Subscriber){
        listItemBinding.tvItemName.text = subscriber.name
        listItemBinding.tvItemEmail.text = subscriber.email
    }
}