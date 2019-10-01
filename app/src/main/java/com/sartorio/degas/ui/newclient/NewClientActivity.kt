package com.sartorio.degas.ui.newclient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sartorio.degas.R
import com.sartorio.degas.common.statefragment.*

class NewClientActivity : AppCompatActivity(), OnStepConcludedListener {
    override fun onStepConcluded() {
        if (fragmentFlow.hasNext()) {
            goToNextState(fragmentFlow.getNext())
        } else {
            onFlowFinished()
        }
    }

    private lateinit var fragmentFlow: StateFlow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_client)

        startFlow()

    }

    override fun onBackPressed() {
        if (fragmentFlow.hasPrevious()) {
            goToPreviousState(fragmentFlow.getPrevious())
        } else {
            finish()
        }
    }

    private fun startFlow() {
        val nameFragment = ClientNameFragment.newInstance(this)
        val documentsFragment = ClientDocumentsFragment.newInstance(this)
        val addressFragment = ClientAddressFragment.newInstance(this)
        val contactFragment = ClientContactFragment.newInstance(this)

        fragmentFlow = StateFlow.Builder()
            .withInitialRoute(
                state = nameFragment,
                nextState = documentsFragment
            )
            .withRoute(
                state = documentsFragment,
                nextState = addressFragment,
                previousState = nameFragment
            )
            .withRoute(
                state = addressFragment,
                nextState = contactFragment,
                previousState = documentsFragment
            )
            .withFinalRoute(
                state = contactFragment,
                previousState = addressFragment
            )
            .build()
        goToNextState(fragmentFlow.getCurrent())
    }

    private fun goToNextState(state: BaseState<*>) {
        showFragmentEnterFromRightAnimation(state as Fragment, state::class.java.simpleName)
    }

    private fun goToPreviousState(state: BaseState<*>) {
        showFragmentEnterFromLeftAnimation(state as Fragment, state::class.java.simpleName)
    }

    private fun onFlowFinished() {
        finish()
    }

    companion object {
        @JvmStatic
        fun createIntent(context: Context): Intent {
            return Intent(context, NewClientActivity::class.java)
        }
    }
}