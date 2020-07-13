package com.sartorio.degas.ui.clients.newclient


import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import com.sartorio.degas.R
import com.sartorio.degas.common.MaskTextChangedListener
import com.sartorio.degas.common.MultiMaskTextChangedListener
import com.sartorio.degas.common.SimpleTextWatcher
import com.sartorio.degas.common.statefragment.BaseState
import com.sartorio.degas.common.statefragment.OnStepConcludedListener
import com.sartorio.degas.common.statefragment.StateFragment
import com.sartorio.degas.databinding.FragmentClientDocumentsBinding
import com.sartorio.degas.model.Client
import com.sartorio.degas.model.ClientDocuments

/**
 * A simple [Fragment] subclass.
 */
class ClientDocumentsFragment : StateFragment(), BaseState<ClientDocuments> {

    override fun getData(): ClientDocuments {
        return ClientDocuments(cnpj.get() ?: "", stateRegistration.get() ?: "")
    }


    private var first = true
    private lateinit var client: Client
    private lateinit var fragmentClientDocumentsBinding: FragmentClientDocumentsBinding
    val cnpj = ObservableField<String>()
    val stateRegistration =
        ObservableField<String>()
    val dataValid = ObservableField<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentClientDocumentsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_client_documents,
            container,
            false
        )
        return fragmentClientDocumentsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setInitialValues()
        fragmentClientDocumentsBinding.apply {
            clientDocumentsFragment = this@ClientDocumentsFragment
            buttonNext.setOnClickListener {
                notifyStepConcluded()
            }
            editTextCnpj.addTextChangedListener(object : SimpleTextWatcher() {
                override fun afterTextChanged(p0: Editable?) {
                    validateData()
                }
            })
            editTextCnpj.addTextChangedListener(
                MultiMaskTextChangedListener(
                    "##.###.###/####-##",
                    "###.###.###-##",
                    editTextCnpj
                )
            )
            editTextStateRegistration.addTextChangedListener(object : SimpleTextWatcher() {
                override fun afterTextChanged(p0: Editable?) {
                    validateData()
                }
            })
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setInitialValues() {
        if (first) {

            cnpj.set(client.documents.cnpj)
            stateRegistration.set(client.documents.stateRegistration)
            first = false
        }

    }

    private fun validateData() {
        dataValid.set(
            (cnpj.get()?.isNotBlank() ?: false) and
                    (stateRegistration.get()?.isNotBlank() ?: false)
        )
    }

    companion object {
        fun newInstance(
            onStepConcludedListener: OnStepConcludedListener,
            client: Client
        ): ClientDocumentsFragment {
            return ClientDocumentsFragment().apply {
                this.onStepConcludedListener = onStepConcludedListener
                this.client = client
            }
        }
    }
}
