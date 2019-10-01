package com.sartorio.degas.common.statefragment

import com.sartorio.degas.common.statefragment.BaseState

data class StateRoute(val state: BaseState<*>,
                      val previousState: BaseState<*>? = null,
                      val nextState: BaseState<*>? = null) {
    fun hasNext(): Boolean {
        return nextState != null
    }

    fun hasPrevious(): Boolean {
        return previousState != null
    }
}