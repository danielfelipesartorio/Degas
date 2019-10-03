package com.sartorio.degas.ui.newclient


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import com.sartorio.degas.R
import com.sartorio.degas.common.statefragment.BaseState
import com.sartorio.degas.common.statefragment.OnStepConcludedListener
import com.sartorio.degas.common.statefragment.StateFragment
import com.sartorio.degas.databinding.FragmentClientContactBinding
import com.sartorio.degas.model.ClientContact
import kotlinx.android.synthetic.main.fragment_client_contact.*

/**
 * A simple [Fragment] subclass.
 */
class ClientContactFragment : StateFragment(), BaseState<ClientContact> {
    override fun getData(): ClientContact {
        return ClientContact("", "", "", "")
    }

    private lateinit var fragmentClientContactBinding: FragmentClientContactBinding
    val contactName = ObservableField<String>("aaaa")
    val email = ObservableField<String>("aaaa")
    val telephone = ObservableField<String>("aaaa")
    val cellphone = ObservableField<String>("aaaa")

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
        fragmentClientContactBinding.clientContactFragment = this
        buttonNext.setOnClickListener {
            notifyStepConcluded()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(onStepConcludedListener: OnStepConcludedListener): ClientContactFragment {
            return ClientContactFragment().apply {
                this.onStepConcludedListener = onStepConcludedListener
            }
        }
    }
}
