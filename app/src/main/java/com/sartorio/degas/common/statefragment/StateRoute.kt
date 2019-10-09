package com.sartorio.degas.common.statefragment

data class StateRoute(
    val state: BaseState<*>,
    val previousState: BaseState<*>? = null,
    val nextState: BaseState<*>? = null
) {
    fun hasNext(): Boolean {
        return nextState != null
    }

    fun hasPrevious(): Boolean {
        return previousState != null
    }
}