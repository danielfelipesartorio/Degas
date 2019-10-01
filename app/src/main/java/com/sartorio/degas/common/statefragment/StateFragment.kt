package com.sartorio.degas.common.statefragment

import androidx.fragment.app.Fragment


abstract class StateFragment : Fragment() {

    var onStepConcludedListener: OnStepConcludedListener? = null

    protected fun notifyStepConcluded() {
        onStepConcludedListener?.onStepConcluded()
    }

}