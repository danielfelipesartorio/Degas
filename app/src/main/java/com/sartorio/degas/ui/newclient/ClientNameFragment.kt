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
import com.sartorio.degas.databinding.FragmentClientNameBinding
import com.sartorio.degas.model.ClientName
import kotlinx.android.synthetic.main.fragment_client_name.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class ClientNameFragment : StateFragment(), BaseState<ClientName> {
    override fun getData(): ClientName {
        return ClientName(companyName.get() ?: "", fantasyName.get() ?: "")
    }

    val companyName = ObservableField<String>("aaaa")
    val fantasyName = ObservableField<String>("aaaa")
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
        super.onViewCreated(view, savedInstanceState)
        fragmentClientNameBinding.clientNameFragment = this
        buttonNext.setOnClickListener {
            notifyStepConcluded()
        }
    }

    companion object {
        fun newInstance(onStepConcludedListener: OnStepConcludedListener): ClientNameFragment {
            return ClientNameFragment().apply {
                this.onStepConcludedListener = onStepConcludedListener
            }
        }
    }


}
