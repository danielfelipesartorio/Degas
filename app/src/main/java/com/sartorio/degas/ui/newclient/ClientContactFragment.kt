package com.sartorio.degas.ui.newclient


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sartorio.degas.R
import com.sartorio.degas.common.statefragment.BaseState
import com.sartorio.degas.common.statefragment.OnStepConcludedListener
import com.sartorio.degas.common.statefragment.StateFragment
import com.sartorio.degas.model.ClientContact
import kotlinx.android.synthetic.main.fragment_client_contact.*

/**
 * A simple [Fragment] subclass.
 */
class ClientContactFragment : StateFragment(), BaseState<ClientContact> {
    override fun getData(): ClientContact {
        return ClientContact("","","","")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
