package com.sartorio.degas.ui.clients.newclient


import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.sartorio.degas.R
import com.sartorio.degas.common.MaskTextChangedListener
import com.sartorio.degas.common.SimpleTextWatcher
import com.sartorio.degas.common.statefragment.BaseState
import com.sartorio.degas.common.statefragment.OnStepConcludedListener
import com.sartorio.degas.common.statefragment.StateFragment
import com.sartorio.degas.databinding.FragmentClientAddressBinding
import com.sartorio.degas.model.Client
import com.sartorio.degas.model.ClientAddress
import com.sartorio.degas.services.CepService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ClientAddressFragment : StateFragment(), BaseState<ClientAddress> {
    private var first = true
    private lateinit var client: Client

    override fun getData(): ClientAddress {
        return ClientAddress(
            streetAddress.get() ?: "",
            postCode.get() ?: "",
            district.get() ?: "",
            city.get() ?: "",
            state.get() ?: ""
        )
    }

    private lateinit var fragmentClientAddressBinding: FragmentClientAddressBinding
    val streetAddress = ObservableField<String>()
    val postCode = ObservableField<String>()
    val district = ObservableField<String>()
    val city = ObservableField<String>()
    val state = ObservableField<String>()
    val dataValid = ObservableField<Boolean>()

    val cepService: CepService by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentClientAddressBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_client_address,
            container,
            false
        )
        return fragmentClientAddressBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setInitialValues()
        fragmentClientAddressBinding.apply {
            clientAddressFragment = this@ClientAddressFragment
            buttonNext.setOnClickListener {
                notifyStepConcluded()
            }
            editTextStreetAddress.addTextChangedListener(object :
                SimpleTextWatcher() {
                override fun afterTextChanged(p0: Editable?) {
                    validateData()
                }
            })
            editTextCity.addTextChangedListener(object :
                SimpleTextWatcher() {
                override fun afterTextChanged(p0: Editable?) {
                    validateData()
                }
            })
            editTextDistrict.addTextChangedListener(object :
                SimpleTextWatcher() {
                override fun afterTextChanged(p0: Editable?) {
                    validateData()
                }
            })
            editTextPostCode.addTextChangedListener(object :
                SimpleTextWatcher() {
                override fun afterTextChanged(p0: Editable?) {
                    if (p0?.length == 9) {
                        getAddressFromCep(p0.replace(Regex("-"), ""))
                    } else {
                        validateData()
                    }
                }
            })
            editTextPostCode.addTextChangedListener(
                MaskTextChangedListener(
                    "#####-###",
                    editTextPostCode
                )
            )
            editTextState.addTextChangedListener(object :
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

        streetAddress.set(client.clientAddress.streetAddress)
        postCode.set(client.clientAddress.postCode)
        district.set(client.clientAddress.district)
        city.set(client.clientAddress.city)
        state.set(client.clientAddress.state)
            first = false
        }
    }

    fun getAddressFromCep(cep: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val body = cepService.getAddressDetails(cep).body()
            streetAddress.set(body?.logradouro)
            district.set(body?.bairro)
            city.set(body?.localidade)
            state.set(body?.uf)
        }
    }

    private fun validateData() {
        dataValid.set(
            (streetAddress.get()?.isNotBlank() ?: false) and
                    (postCode.get()?.isNotBlank() ?: false) and
                    (district.get()?.isNotBlank() ?: false) and
                    (city.get()?.isNotBlank() ?: false) and
                    (state.get()?.isNotBlank() ?: false)
        )
    }

    companion object {
        fun newInstance(
            onStepConcludedListener: OnStepConcludedListener,
            client: Client
        ): ClientAddressFragment {
            return ClientAddressFragment().apply {
                this.onStepConcludedListener = onStepConcludedListener
                this.client = client
            }
        }
    }
}
