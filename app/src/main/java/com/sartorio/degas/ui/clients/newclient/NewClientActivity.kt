package com.sartorio.degas.ui.clients.newclient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sartorio.degas.R
import com.sartorio.degas.common.statefragment.*
import com.sartorio.degas.model.*
import org.koin.android.viewmodel.ext.android.viewModel

class NewClientActivity : AppCompatActivity(), OnStepConcludedListener {

    private val newClientViewModel: NewClientViewModel by viewModel()
    lateinit var clientName: String
    lateinit var client: Client

    override fun onStepConcluded() {
        if (fragmentFlow.hasNext()) {
            goToNextState(fragmentFlow.getNext())
        } else {
            onFlowFinished()
        }
    }

    private lateinit var fragmentFlow: StateFlow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_client)
        clientName = intent.extras?.getString(CLIENT) ?: ""
        newClientViewModel.initViewModel(clientName)
        newClientViewModel.client.observe(this, Observer {
            client = it
            startFlow()
        })
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Cadastro de novo cliente"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (fragmentFlow.hasPrevious()) {
            goToPreviousState(fragmentFlow.getPrevious())
        } else {
            finish()
        }
    }

    private fun startFlow() {
        val nameFragment = ClientNameFragment.newInstance(this, client)
        val documentsFragment = ClientDocumentsFragment.newInstance(this, client)
        val addressFragment = ClientAddressFragment.newInstance(this, client)
        val contactFragment = ClientContactFragment.newInstance(this, client)

        fragmentFlow = StateFlow.Builder()
            .withInitialRoute(
                state = nameFragment,
                nextState = documentsFragment
            )
            .withRoute(
                state = documentsFragment,
                nextState = addressFragment,
                previousState = nameFragment
            )
            .withRoute(
                state = addressFragment,
                nextState = contactFragment,
                previousState = documentsFragment
            )
            .withFinalRoute(
                state = contactFragment,
                previousState = addressFragment
            )
            .build()
        goToNextState(fragmentFlow.getCurrent())
    }

    private fun goToNextState(state: BaseState<*>) {
        showFragmentEnterFromRightAnimation(state as Fragment, state::class.java.simpleName)
    }

    private fun goToPreviousState(state: BaseState<*>) {
        showFragmentEnterFromLeftAnimation(state as Fragment, state::class.java.simpleName)
    }

    private fun onFlowFinished() {
        newClientViewModel.saveNewClient(mapNewClientData(fragmentFlow))
        finish()
    }

    private fun mapNewClientData(flow: StateFlow): Client {
        val data = flow.getDataMap()
        return Client(
            uName = (data[ClientNameFragment::class.java.name] as ClientName).companyName,
            name = data[ClientNameFragment::class.java.name] as ClientName,
            clientAddress = data[ClientAddressFragment::class.java.name] as ClientAddress,
            contact = data[ClientContactFragment::class.java.name] as ClientContact,
            documents = data[ClientDocumentsFragment::class.java.name] as ClientDocuments
        )
    }

    companion object {
        const val CLIENT = "CLIENT"

        @JvmStatic
        fun createIntent(context: Context, clientName: String = ""): Intent {
            val intent = Intent(context, NewClientActivity::class.java)
            intent.putExtra(CLIENT, clientName)
            return intent
        }
    }
}
