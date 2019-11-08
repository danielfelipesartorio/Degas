package com.sartorio.degas.ui.clients.newclient


import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.sartorio.degas.R
import com.sartorio.degas.common.SimpleTextWatcher
import com.sartorio.degas.common.statefragment.BaseState
import com.sartorio.degas.common.statefragment.OnStepConcludedListener
import com.sartorio.degas.common.statefragment.StateFragment
import com.sartorio.degas.databinding.FragmentClientNameBinding
import com.sartorio.degas.model.Client
import com.sartorio.degas.model.ClientName

class ClientNameFragment : StateFragment(), BaseState<ClientName> {
    private lateinit var client: Client
    var first = true

    override fun getData(): ClientName {
        return ClientName(companyName.get() ?: "", fantasyName.get() ?: "")
    }

    val companyName = ObservableField<String>()
    val fantasyName = ObservableField<String>()
    val dataValid = ObservableField<Boolean>()
    private lateinit var fragmentClientNameBinding: FragmentClientNameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentClientNameBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_client_name,
            container,
            false
        )
        return fragmentClientNameBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setInitialValues()
        fragmentClientNameBinding.apply {
            clientNameFragment = this@ClientNameFragment
            buttonNext.setOnClickListener {
                notifyStepConcluded()
            }
            editTextCompanyName.addTextChangedListener(object :
                SimpleTextWatcher() {
                override fun afterTextChanged(p0: Editable?) {
                    validateData()
                }
            })
            editTextFantasyName.addTextChangedListener(object :
                SimpleTextWatcher() {
                override fun afterTextChanged(p0: Editable?) {
                    validateData()
                }
            })
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setInitialValues() {
        if (first) {
            companyName.set(client.name.companyName)
            fantasyName.set(client.name.fantasyName)
            first = false
        }
        if (client.name.companyName.isNotBlank()) {
            fragmentClientNameBinding.companyNameLayout.isEnabled = false
        }
    }

    private fun validateData() {
        dataValid.set(
            (companyName.get()?.isNotBlank() ?: false) and
                    (fantasyName.get()?.isNotBlank() ?: false)
        )
    }

    companion object {
        fun newInstance(
            onStepConcludedListener: OnStepConcludedListener,
            client: Client
        ): ClientNameFragment {
            return ClientNameFragment().apply {
                this.onStepConcludedListener = onStepConcludedListener
                this.client = client
            }
        }
    }
}
