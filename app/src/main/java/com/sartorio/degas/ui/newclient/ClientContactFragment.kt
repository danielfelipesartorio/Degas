package com.sartorio.degas.ui.newclient


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
import com.sartorio.degas.common.SimpleTextWatcher
import com.sartorio.degas.common.statefragment.BaseState
import com.sartorio.degas.common.statefragment.OnStepConcludedListener
import com.sartorio.degas.common.statefragment.StateFragment
import com.sartorio.degas.databinding.FragmentClientContactBinding
import com.sartorio.degas.model.ClientContact

/**
 * A simple [Fragment] subclass.
 */
class ClientContactFragment : StateFragment(), BaseState<ClientContact> {
    override fun getData(): ClientContact {
        return ClientContact(
            contactName.get() ?: "",
            email.get() ?: "",
            telephone.get() ?: "",
            cellphone.get() ?: ""
        )
    }

    private lateinit var fragmentClientContactBinding: FragmentClientContactBinding
    val contactName = ObservableField<String>()
    val email = ObservableField<String>()
    val telephone = ObservableField<String>()
    val cellphone = ObservableField<String>()
    val dataValid = ObservableField<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentClientContactBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_client_contact,
            container,
            false
        )
        return fragmentClientContactBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentClientContactBinding.apply {
            clientContactFragment = this@ClientContactFragment
            buttonNext.setOnClickListener {
                notifyStepConcluded()
            }
            editTextContactName.addTextChangedListener(object :
                SimpleTextWatcher() {
                override fun afterTextChanged(p0: Editable?) {
                    validateData()
                }
            })
            editTextEmail.addTextChangedListener(object :
                SimpleTextWatcher() {
                override fun afterTextChanged(p0: Editable?) {
                    validateData()
                }
            })
            editTextTelephone.addTextChangedListener(object :
                SimpleTextWatcher() {
                override fun afterTextChanged(p0: Editable?) {
                    validateData()
                }
            })
            editTextTelephone.addTextChangedListener(
                MaskTextChangedListener(
                    "(##) ####-####",
                    editTextTelephone
                )
            )
            editTextCellphone.addTextChangedListener(object :
                SimpleTextWatcher() {
                override fun afterTextChanged(p0: Editable?) {
                    validateData()
                }
            })
            editTextCellphone.addTextChangedListener(
                MaskTextChangedListener(
                    "(##) #####-####",
                    editTextCellphone
                )
            )
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun validateData() {
        dataValid.set(
            (contactName.get()?.isNotBlank() ?: false) and
                    (email.get()?.isNotBlank() ?: false) and
                    (telephone.get()?.isNotBlank() ?: false) and
                    (cellphone.get()?.isNotBlank() ?: false)
        )
    }

    companion object {
        fun newInstance(onStepConcludedListener: OnStepConcludedListener): ClientContactFragment {
            return ClientContactFragment().apply {
                this.onStepConcludedListener = onStepConcludedListener
            }
        }
    }
}
