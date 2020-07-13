package com.sartorio.degas.ui.clients

import com.sartorio.degas.model.Client

interface ClientsListOnClickListener {
    fun onClick(client: Client)
    fun onLongClick(client: Client)
}
