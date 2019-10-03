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
import com.sartorio.degas.databinding.FragmentClientDocumentsBinding
import com.sartorio.degas.model.ClientDocuments
import kotlinx.android.synthetic.main.fragment_client_documents.*

/**
 * A simple [Fragment] subclass.
 */
class ClientDocumentsFragment : StateFragment() , BaseState<ClientDocuments> {
    override fun getData(): ClientDocuments {
        return ClientDocuments("cpf","inscrição estadual")
    }

    private lateinit var fragmentClientDocumentsBinding: FragmentClientDocumentsBinding
    val cnpj = ObservableField<String>("aaaa")
    val stateRegistration = ObservableField<String>("aaaa")

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
        buttonNext.setOnClickListener {
            notifyStepConcluded()
        }
        fragmentClientDocumentsBinding.clientDocumentsFragment = this
        super.onViewCreated(view, savedInstanceState)
    }

    companion object{
        fun newInstance(onStepConcludedListener: OnStepConcludedListener):ClientDocumentsFragment{
            return ClientDocumentsFragment().apply {
                this.onStepConcludedListener = onStepConcludedListener
            }
        }
    }
}
