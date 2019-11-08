package com.sartorio.degas.model

class Client(
    var name: ClientName,
    var documents: ClientDocuments,
    var clientAddress: ClientAddress,
    var contact: ClientContact
){

    companion object{
        fun getNewClient():Client{
            return Client(
                ClientName("",""),
                ClientDocuments("",""),
                ClientAddress("","","","",""),
                ClientContact("","","","")
            )
        }
    }
}
