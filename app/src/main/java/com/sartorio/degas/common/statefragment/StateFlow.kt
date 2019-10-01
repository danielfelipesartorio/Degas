package com.sartorio.degas.common.statefragment

class StateFlow {

    private lateinit var initialState: StateRoute
    private lateinit var finalState: StateRoute
    private lateinit var states: HashMap<String, StateRoute>

    private lateinit var currentState: StateRoute

    fun getCurrent(): BaseState<*> {
        return currentState.state
    }

    fun hasNext(): Boolean {
        return currentState.hasNext()
    }

    fun getNext(): BaseState<*> {
        if (hasNext()) {
            currentState = states[currentState.nextState!!::class.java.name]!!
            return currentState.state
        }
        throw StateNotFoundException()
    }

    fun hasPrevious(): Boolean {
        return currentState.hasPrevious()
    }

    fun getPrevious(): BaseState<*> {
        if (hasPrevious()) {
            currentState = states[currentState.previousState!!::class.java.name]!!
            return currentState.state
        }
        throw StateNotFoundException()
    }

    fun getDataList(): List<*> {
        return states.values.map {stateRoute ->
            stateRoute.state.getData()
        }
    }

    fun getDataMap(): Map<String, *> {
        return states.mapValues {entry ->
            entry.value.state.getData()
        }
    }

    class Builder {
        private lateinit var initialState: StateRoute
        private lateinit var finalState: StateRoute
        private val states: HashMap<String, StateRoute> = hashMapOf()

        fun withInitialRoute(
            state: BaseState<*>,
            nextState: BaseState<*>? = null
        ): Builder {
            initialState = StateRoute(state = state, nextState = nextState)
            states[state::class.java.name] = initialState
            return this
        }

        fun withFinalRoute(
            state: BaseState<*>,
            previousState: BaseState<*>? = null
        ): Builder {
            finalState = StateRoute(state = state, previousState = previousState)
            states[state::class.java.name] = finalState
            return this
        }

        fun withSingleRoute(state: BaseState<*>): Builder {
            return withInitialRoute(state)
                .withFinalRoute(state)
        }

        fun withRoute(
            state: BaseState<*>,
            previousState: BaseState<*>,
            nextState: BaseState<*>
        ): Builder {

            val stateRoute = StateRoute(
                state = state,
                previousState = previousState,
                nextState = nextState
            )
            states[state::class.java.name] = stateRoute
            return this
        }

        fun build(): StateFlow {
            val baseFlow = StateFlow()
            baseFlow.initialState = initialState
            baseFlow.finalState = finalState
            baseFlow.states = states

            baseFlow.currentState = initialState

            return baseFlow
        }
    }

}