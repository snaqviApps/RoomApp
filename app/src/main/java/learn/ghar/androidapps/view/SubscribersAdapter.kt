package learn.ghar.androidapps.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import learn.ghar.androidapps.databinding.SubscriberListItemBinding
import learn.ghar.androidapps.domain.SubscriberUI

class SubscribersAdapter(
    private val subscribers: List<SubscriberUI>,
    private val itemClickListener: (SubscriberUI) -> Unit
) : RecyclerView.Adapter<SubscribersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscribersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rViewBinding = SubscriberListItemBinding.inflate(layoutInflater)
        return SubscribersViewHolder(rViewBinding)
    }

    override fun getItemCount() = subscribers.size

    override fun onBindViewHolder(holder: SubscribersViewHolder, position: Int) =
        holder.bind(subscribers[position], itemClickListener)

}

class SubscribersViewHolder(
    private val listItemBinding: SubscriberListItemBinding
) : RecyclerView.ViewHolder(listItemBinding.root) {

    fun bind(subscriber: SubscriberUI, itemClickListener: (SubscriberUI) -> Unit) {
        listItemBinding.tvItemName.text = subscriber.name
        listItemBinding.tvItemEmail.text = subscriber.email
        listItemBinding.listItemLayout.setOnClickListener {
            itemClickListener(subscriber)
        }
    }
}