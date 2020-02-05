package com.sartorio.degas.ui.clients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sartorio.degas.R
import com.sartorio.degas.model.Client
import kotlinx.android.synthetic.main.client_item.view.*

class ClientsAdapter(var list: List<Client>, val listener: ClientsListOnClickListener) :
    RecyclerView.Adapter<ClientViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ClientViewHolder(
            inflater.inflate(
                R.layout.client_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        holder.format(list[position])
        holder.itemView.setOnClickListener {
            listener.onClick(list[position])
        }
    }

}

class ClientViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun format(client: Client) {
        view.textViewClientCompanyName.text = client.name.companyName
        view.textViewClientFantasyName.text = client.name.fantasyName
    }

}
