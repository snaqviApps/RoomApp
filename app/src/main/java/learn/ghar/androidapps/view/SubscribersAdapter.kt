package learn.ghar.androidapps.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import learn.ghar.androidapps.databinding.SubscriberListItemBinding
import learn.ghar.androidapps.db.Subscriber

class SubscribersAdapter(
    private val subscribers: List<Subscriber>,
    private val itemClickListener: (Subscriber) -> Unit
) : RecyclerView.Adapter<SubscribersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscribersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)    // here we create list_item_view layout
        val rViewBinding = SubscriberListItemBinding.inflate(layoutInflater)
        return SubscribersViewHolder(rViewBinding)
    }

    override fun getItemCount(): Int {
       return subscribers.size
    }

    override fun onBindViewHolder(holder: SubscribersViewHolder, position: Int) {
        holder.bind(subscribers[position], itemClickListener)
    }

}

class SubscribersViewHolder(private val listItemBinding: SubscriberListItemBinding) : RecyclerView.ViewHolder(listItemBinding.root){

    fun bind(subscriber: Subscriber, itemClickListener: (Subscriber) -> Unit){
        listItemBinding.tvItemName.text = subscriber.name
        listItemBinding.tvItemEmail.text = subscriber.email
        listItemBinding.listItemLayout.setOnClickListener {
            itemClickListener(subscriber)
        }
    }
}