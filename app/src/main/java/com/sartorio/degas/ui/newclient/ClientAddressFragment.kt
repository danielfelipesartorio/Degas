package com.sartorio.degas.ui.newclient


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
import com.sartorio.degas.databinding.FragmentClientAddressBinding
import com.sartorio.degas.model.ClientAddress

class ClientAddressFragment : StateFragment(), BaseState<ClientAddress> {
    override fun getData(): ClientAddress {
        return ClientAddress("", "", "", "", "")
    }

    private lateinit var fragmentClientAddressBinding: FragmentClientAddressBinding
    val streetAddress = ObservableField<String>()
    val postCode = ObservableField<String>()
    val district = ObservableField<String>()
    val city = ObservableField<String>()
    val state = ObservableField<String>()
    val dataValid = ObservableField<Boolean>()


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
        fragmentClientAddressBinding.apply {
            clientAddressFragment = this@ClientAddressFragment
            buttonNext.setOnClickListener {
                notifyStepConcluded()
            }
            editTextStreetAddress.addTextChangedListener(object :
                SimpleTextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    validateData()
                }
            })
            editTextCity.addTextChangedListener(object :
                SimpleTextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    validateData()
                }
            })
            editTextDistrict.addTextChangedListener(object :
                SimpleTextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    validateData()
                }
            })
            editTextPostCode.addTextChangedListener(object :
                SimpleTextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    validateData()
                }
            })
            editTextState.addTextChangedListener(object :
                SimpleTextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    validateData()
                }
            })
        }
        super.onViewCreated(view, savedInstanceState)
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
        fun newInstance(onStepConcludedListener: OnStepConcludedListener): ClientAddressFragment {
            return ClientAddressFragment().apply {
                this.onStepConcludedListener = onStepConcludedListener
            }
        }
    }
}
