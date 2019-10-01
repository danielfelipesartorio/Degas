package com.sartorio.degas.ui.newclient


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sartorio.degas.R
import com.sartorio.degas.common.statefragment.BaseState
import com.sartorio.degas.common.statefragment.OnStepConcludedListener
import com.sartorio.degas.common.statefragment.StateFragment
import kotlinx.android.synthetic.main.fragment_client_address.*

/**
 * A simple [Fragment] subclass.
 */
class ClientAddressFragment : StateFragment(), BaseState<Any> {
    override fun getData(): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
