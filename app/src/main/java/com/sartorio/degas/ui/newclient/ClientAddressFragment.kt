package com.sartorio.degas.ui.newclient


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField

import com.sartorio.degas.R
import com.sartorio.degas.common.statefragment.BaseState
import com.sartorio.degas.common.statefragment.OnStepConcludedListener
import com.sartorio.degas.common.statefragment.StateFragment
import com.sartorio.degas.databinding.FragmentClientAddressBinding
import com.sartorio.degas.model.ClientAddress
import kotlinx.android.synthetic.main.fragment_client_address.*

/**
 * A simple [Fragment] subclass.
 */
class ClientAddressFragment : StateFragment(), BaseState<ClientAddress> {
    override fun getData(): ClientAddress {
        return ClientAddress("","","","","")
    }

    private lateinit var fragmentClientAddressBinding: FragmentClientAddressBinding
    val streetAddress = ObservableField<String>("aaaa")
    val postCode = ObservableField<String>("aaaa")
    val district = ObservableField<String>("aaaa")
    val city = ObservableField<String>("aaaa")
    val state = ObservableField<String>("aaaa")


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
        fragmentClientAddressBinding.clientAddressFragment = this
        buttonNext.setOnClickListener {
            notifyStepConcluded()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    companion object{
        fun newInstance(onStepConcludedListener: OnStepConcludedListener):ClientAddressFragment{
            return ClientAddressFragment().apply {
                this.onStepConcludedListener = onStepConcludedListener
            }
        }
    }
}
